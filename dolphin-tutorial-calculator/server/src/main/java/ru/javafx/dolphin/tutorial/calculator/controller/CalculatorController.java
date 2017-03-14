
package ru.javafx.dolphin.tutorial.calculator.controller;

import com.canoo.dolphin.server.DolphinAction;
import com.canoo.dolphin.server.DolphinController;
import com.canoo.dolphin.server.DolphinModel;
import javax.annotation.PostConstruct;
import ru.javafx.dolphin.tutorial.calculator.Constants;
import ru.javafx.dolphin.tutorial.calculator.model.CalculatorModel;

@DolphinController(Constants.CALCULATOR_CONTROLLER)
public class CalculatorController {
   
    @DolphinModel
    private CalculatorModel model;

    @PostConstruct
    public void init() {
        model.firstValueProperty().onChanged(e -> calc());
        model.secondValueProperty().onChanged(e -> calc());
    }

    @DolphinAction(Constants.RESET_ACTION)
    public void reset() {
        model.setFirstValue(null);
        model.setSecondValue(null);
        model.setResult(null);
    }

    private void calc() {
        try {
            int valueA = 0;
            int valueB = 0;
            String stringValue = model.getFirstValue();
            if (stringValue != null && !stringValue.trim().isEmpty()) {
                valueA = Integer.parseInt(stringValue);
            }
            stringValue = model.getSecondValue();
            if (stringValue != null && !stringValue.trim().isEmpty()) {
                valueB = Integer.parseInt(stringValue);
            }
            model.resultProperty().set((valueA + valueB) + "");
        } catch (Exception e) {
            model.resultProperty().set("Error");
        }
    }

}
