
package ru.javafx.dolphin.springbootfx;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.ClientInitializationException;
import com.canoo.dolphin.client.DolphinRuntimeException;
import com.canoo.dolphin.client.HttpURLConnectionFactory;
import com.canoo.dolphin.client.HttpURLConnectionResponseHandler;
import com.canoo.dolphin.client.javafx.DolphinPlatformApplication;
import com.canoo.dolphin.client.javafx.JavaFXConfiguration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.javafx.dolphin.springbootfx.view.ViewController;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class ClientApplication extends DolphinPlatformApplication {
   
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    private ConfigurableApplicationContext springContext;
    
    @Autowired
    private HttpURLConnectionFactory connectionFactory;
    @Autowired
    private HttpURLConnectionResponseHandler responseHandler;
    @Value("${dolphin.server.endpoint}")
    private String serverEndpoint;
    
    @Override
    public void init() throws Exception {
        super.init();             
    }
    
    private void initSpringContext() {
        springContext = SpringApplication.run(getClass()); 
        springContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    protected URL getServerEndpoint() throws MalformedURLException {
         // инициализировать springContext тут(не в init() и start()), чтобы были доступны бины при конфигурировании
        initSpringContext();
        return new URL(serverEndpoint);
    }
    
    @Override
    protected void start(Stage primaryStage, ClientContext clientContext) throws Exception {
        springContext.getBeanFactory().registerSingleton("primaryStage", primaryStage);
        springContext.getBeanFactory().registerSingleton("clientContext", clientContext);
              
        ViewController viewController = springContext.getBean(ViewController.class);       
        Scene scene = new Scene(viewController.getParent());
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }
    
    @Override
    protected JavaFXConfiguration getClientConfiguration() {     
        JavaFXConfiguration configuration = super.getClientConfiguration();
        configuration.setConnectionFactory(connectionFactory);
        configuration.setResponseHandler(responseHandler);  // bug: do not call
        return configuration;
    }

    private void showError(Window parent, String header, String content, Exception e) {
        LOGGER.error("Dolphin Platform error!", e);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        Platform.exit();
        System.exit(0);
    }

    @Override
    protected void onInitializationError(Stage primaryStage, ClientInitializationException initializationException) {
        showError(primaryStage, "Error on initialization", "A error happened while initializing the Client and Connection", initializationException);
    }

    @Override
    protected void onRuntimeError(Stage primaryStage, DolphinRuntimeException runtimeException) {
        showError(primaryStage, "Error at runtime", "A error happened at runtime", runtimeException);
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
