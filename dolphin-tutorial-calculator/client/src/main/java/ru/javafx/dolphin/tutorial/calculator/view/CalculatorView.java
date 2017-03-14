
package ru.javafx.dolphin.tutorial.calculator.view;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.binding.FXBinder;
import com.canoo.dolphin.client.javafx.view.AbstractFXMLViewBinder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.javafx.dolphin.tutorial.calculator.Constants;
import ru.javafx.dolphin.tutorial.calculator.model.CalculatorModel;

public class CalculatorView extends AbstractFXMLViewBinder<CalculatorModel> {
    
    @FXML
    private TextField valueAField;
    @FXML
    private TextField valueBField;
    @FXML
    private TextField resultField;
    @FXML
    private Button resetButton;

    public CalculatorView(ClientContext clientContext) {
        super(clientContext, Constants.CALCULATOR_CONTROLLER, CalculatorView.class.getResource("calculator.fxml"));
    }

    @Override
    protected void init() {
        FXBinder.bind(valueAField.textProperty()).bidirectionalTo(getModel().firstValueProperty());
        FXBinder.bind(valueBField.textProperty()).bidirectionalTo(getModel().secondValueProperty());
        FXBinder.bind(resultField.textProperty()).bidirectionalTo(getModel().resultProperty());
        resetButton.setOnAction(e -> invoke(Constants.RESET_ACTION));
    }

}
