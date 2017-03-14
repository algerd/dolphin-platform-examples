
package ru.javafx.dolphin.tutorial.calculator.model;

import com.canoo.dolphin.mapping.DolphinBean;
import com.canoo.dolphin.mapping.Property;

@DolphinBean
public class CalculatorModel {
       
    private Property<String> firstValue;
    private Property<String> secondValue;
    private Property<String> result;

    public Property<String> firstValueProperty() {
        return firstValue;
    }

    public Property<String> secondValueProperty() {
        return secondValue;
    }

    public Property<String> resultProperty() {
        return result;
    }

    public String getFirstValue() {
        return firstValueProperty().get();
    }

    public String getSecondValue() {
        return secondValueProperty().get();
    }

    public String getResult() {
        return resultProperty().get();
    }

    public void setFirstValue(String firstValue) {
        firstValueProperty().set(firstValue);
    }

    public void setSecondValue(String secondValue) {
        secondValueProperty().set(secondValue);
    }

    public void setResult(String result) {
        resultProperty().set(result);
    }

}
