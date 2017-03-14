
package ru.javafx.dolphin.security;

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
import java.net.HttpURLConnection;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import ru.javafx.dolphin.security.view.ViewController;

public class ClientApplication extends DolphinPlatformApplication {
    
    public static final String SERVER_ENDPOINT_STRING = "http://localhost:8080/security/dolphin";
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Override
    public void init() throws Exception {
        super.init();     
    }

    @Override
    protected URL getServerEndpoint() throws MalformedURLException {
        return new URL(SERVER_ENDPOINT_STRING);
    }
    
    @Override
    protected void start(Stage primaryStage, ClientContext clientContext) throws Exception {     
        ViewController viewController = new ViewController(clientContext);       
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
        
        HttpURLConnectionFactory connectionFactory = url -> {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();              
            String authorStr = "user" + ":" + "green";
            String encoding = Base64.getEncoder().encodeToString(authorStr.getBytes("utf-8"));
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            return connection;
        };
        // bug: do not call
        HttpURLConnectionResponseHandler responseHandler = response -> {
            Map<String, List<String>> headerFields = response.getHeaderFields();
            LOGGER.info("HeaderFields: {}", headerFields);
        };

        configuration.setConnectionFactory(connectionFactory);
        configuration.setResponseHandler(responseHandler);
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
