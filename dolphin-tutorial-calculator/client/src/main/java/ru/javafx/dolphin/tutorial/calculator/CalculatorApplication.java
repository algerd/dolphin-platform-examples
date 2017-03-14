
package ru.javafx.dolphin.tutorial.calculator;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.DolphinPlatformApplication;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.javafx.dolphin.tutorial.calculator.view.CalculatorView;

public class CalculatorApplication extends DolphinPlatformApplication {
    
    @Override
    protected URL getServerEndpoint() throws MalformedURLException {
        return new URL("http://localhost:8080/dolphin");
    }
    
    @Override
    protected void start(Stage primaryStage, ClientContext clientContext) throws Exception {
        CalculatorView view = new CalculatorView(clientContext);
        Scene scene = new Scene(view.getParent());
        primaryStage.setScene(scene);
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
