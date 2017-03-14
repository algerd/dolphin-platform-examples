
package ru.javafx.dolphin.security.core;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.view.AbstractViewBinder;
import com.canoo.dolphin.client.javafx.view.FxmlLoadException;
import com.canoo.dolphin.util.Assert;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    Пример аннотации контроллеров:

        @FXMLController("/fxml/somepath/someview.fxml")
        @FXMLController(
            value = "/fxml/somepath/main.fxml", // fxml-view path
            css = {"/styles/somepath/style1.css", "/fxml/somepath/style2.css"}, // array css pathes
            bundle = "...",
            title = "Some Controller"
        }
    Если не задавать пути вьюхи и css, то они будут искаться соответственно в папках /fxml/ и /styles/ по
    имени контроллера без суффикса controller: SomeController будет искать /fxml/Some.fxml и /styles/Some.css

*/
public abstract class BaseFXMLViewBinder<M> extends AbstractViewBinder<M> {
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String FXML_PATH_ROOT = "/fxml/";
    private static final String CSS_PATH_ROOT = "/styles/";
    private URL fxmlPath;
    private ResourceBundle resourceBundle;
    private StringProperty title = new SimpleStringProperty();       
    private Node rootNode;

    public BaseFXMLViewBinder(ClientContext clientContext, String controllerName, URL fxmlLocation) {
        super(clientContext, controllerName);
        initView(fxmlLocation);
    }
           
    public BaseFXMLViewBinder(ClientContext clientContext, String controllerName) {
        super(clientContext, controllerName);       
        initView(getClass().getResource(getStringFxmlPath()));        
    }
    
    private void initView(URL fxmlLocation) {
        Assert.requireNonNull(fxmlLocation, "fxmlLocation");
        fxmlPath = fxmlLocation;
        resourceBundle = getResourceBundle(getBundleName());
        title.set(getFxmlAnnotation().title());
        setRootNode();
        addCss();
    }
    
    private void setRootNode() {
        try {
            FXMLLoader loader = new FXMLLoader(fxmlPath, resourceBundle);
            loader.setController(this);
            rootNode = loader.load();                    
        } catch (IOException e) {
            throw new FxmlLoadException("Can not create view based on FXML location " + fxmlPath, e);
        }        
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }
    
    private String getStringFxmlPath() {
        FXMLController annotation = getFxmlAnnotation();
        return annotation == null || annotation.value().equals("") ?
            FXML_PATH_ROOT + getConventionalName(".fxml") : annotation.value();
    }
    
    private FXMLController getFxmlAnnotation() {
		return getClass().getAnnotation(FXMLController.class);
	}
    
    private String getConventionalName(String ending) {
		return getConventionalName() + ending;
	}

	private String getConventionalName() {
        String clazz = getClass().getSimpleName().toLowerCase();
        return !clazz.endsWith("controller") ? clazz : clazz.substring(0, clazz.lastIndexOf("controller"));
	}
    
    private void addCss() {
        if (rootNode != null && (rootNode instanceof Parent)) {
            Parent parent = (Parent) rootNode;                      
            FXMLController annotation = getFxmlAnnotation();
            if (annotation != null && annotation.css().length > 0) {
                for (String cssFile : annotation.css()) {
                    parent.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
                }
            }
            else {
                URL uri = getClass().getResource(CSS_PATH_ROOT + getConventionalName(".css"));
                if (uri != null) {
                    parent.getStylesheets().add(uri.toExternalForm());
                }
            } 
        }
	}
    
    private String getBundleName() {
        FXMLController annotation = getFxmlAnnotation();
        return (annotation == null || annotation.bundle().equals("")) ?
            getClass().getPackage().getName() + "." + getConventionalName() :    
            annotation.bundle();    
	}
    
	private ResourceBundle getResourceBundle(String name) {
		try {
			return ResourceBundle.getBundle(name);
		} catch (MissingResourceException ex) {
			return null;
		}
	}
    
    public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
    
    public void setTitle(String title) {
	    this.title.set(title);
	}   
    public String getTitle() {
	    return title.get();
	}	
	public StringProperty titleProperty() {
	    return title;
	}
}


