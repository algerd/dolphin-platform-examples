package com.guigarage;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.ClientInitializationException;
import com.canoo.dolphin.client.ClientShutdownException;
import com.canoo.dolphin.client.DolphinRuntimeException;
import com.canoo.dolphin.client.javafx.DolphinPlatformApplication;
import com.guigarage.view.ChartViewBinder;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ClientApplication extends DolphinPlatformApplication {
    
    public static final String SERVER_ENDPOINT = "http://localhost:8080/dolphin";

    @Override
    protected URL getServerEndpoint() throws MalformedURLException {
        return new URL(SERVER_ENDPOINT);
    }

    @Override
    public void start(Stage primaryStage, ClientContext clientContext) throws Exception {
        ChartViewBinder chartViewBinder = new ChartViewBinder(clientContext);
        Scene scene = new Scene(chartViewBinder.getParent());
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        }); 
        primaryStage.show();
    }
    
    private void showError(Window parent, String header, String content, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();        
    }

    @Override
    protected void onInitializationError(Stage primaryStage, ClientInitializationException initializationException) {
        showError(primaryStage, "Error on initialization", "A error happened while initializing the Client and Connection", initializationException);
        super.onInitializationError(primaryStage, initializationException);
        System.exit(-1);
    }

    @Override
    protected void onRuntimeError(Stage primaryStage, DolphinRuntimeException runtimeException) {
        showError(primaryStage, "Error at runtime", "A error happened at runtime", runtimeException);
        super.onRuntimeError(primaryStage, runtimeException);
        System.exit(-1);
    }

    public static void main(String... args) {
        launch(args);
    }
}
