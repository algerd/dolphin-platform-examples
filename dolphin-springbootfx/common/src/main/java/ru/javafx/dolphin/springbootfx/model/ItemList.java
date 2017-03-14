
package ru.javafx.dolphin.springbootfx.model;

import com.canoo.dolphin.collections.ObservableList;
import com.canoo.dolphin.mapping.DolphinBean;
import com.canoo.dolphin.mapping.Property;

@DolphinBean
public class ItemList {

    private ObservableList<Item> items;
    private Property<String> newItemText;

    public ObservableList<Item> getItems() {
        return items;
    }

    public Property<String> getNewItemText() {
        return newItemText;
    }
}
