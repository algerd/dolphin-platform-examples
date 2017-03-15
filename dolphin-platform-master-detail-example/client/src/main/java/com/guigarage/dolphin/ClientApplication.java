package com.guigarage.dolphin;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.DolphinPlatformApplication;
import com.guigarage.dolphin.view.DetailView;
import com.guigarage.dolphin.view.MasterView;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class ClientApplication extends DolphinPlatformApplication {
    
    public static final String SERVER_ENDPOINT = "http://localhost:8080/dolphin";

    @Override
    protected URL getServerEndpoint() throws MalformedURLException {
        return new URL(SERVER_ENDPOINT);
    }

    @Override
    protected void start(Stage primaryStage, ClientContext clientContext) throws Exception {
        MasterView masterView = new MasterView(clientContext);
        DetailView detailView = new DetailView(clientContext);
        masterView.selectedStockIdentProperty().addListener((obs, oldVal, newVal) -> detailView.showStock(newVal));

        HBox mainPane = new HBox(masterView.getRootNode(), detailView.getRootNode());
        mainPane.setSpacing(12);
        mainPane.setPadding(new Insets(12));
        HBox.setHgrow(masterView.getRootNode(), Priority.NEVER);
        HBox.setHgrow(detailView.getRootNode(), Priority.ALWAYS);

        primaryStage.setScene(new Scene(mainPane));
         primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        }); 
        primaryStage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
