
package ru.javafx.dolphin.springbootfx.model;

import com.canoo.dolphin.mapping.DolphinBean;
import com.canoo.dolphin.mapping.Property;

@DolphinBean
public class Item {

    private Property<String> text;
    private Property<Boolean> completed;

    public String getText() {
        return text.get();
    }
    public void setText(String text) {
        this.text.set(text);
    }
    public Property<String> getTextProperty() {
        return text;
    }

    public Item withText(final String text) {
        setText(text);
        return this;
    }

    public boolean isCompleted() {
        return Boolean.TRUE == completed.get();
    }
    
    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }
    
    public Property<Boolean> getCompletedProperty() {
        return completed;
    }
    
    public Item withState(final boolean state) {
        setCompleted(state);
        return this;
    }

}
