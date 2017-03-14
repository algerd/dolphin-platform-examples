
package ru.javafx.dolphin.todo.example.view;

import com.canoo.dolphin.client.ControllerProxy;
import com.canoo.dolphin.client.Param;
import com.canoo.dolphin.client.javafx.binding.FXBinder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.util.Optional;
import static javafx.scene.layout.Priority.ALWAYS;
import static javafx.scene.layout.Priority.NEVER;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.CHANGE_ACTION;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.ITEM_PARAM;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.REMOVE_ACTION;
import ru.javafx.dolphin.todo.example.model.ToDoItem;
import ru.javafx.dolphin.todo.example.model.ToDoList;

public class ToDoItemCell extends ListCell<ToDoItem> {

    private final ControllerProxy<ToDoList> controllerProxy;

    public ToDoItemCell(ControllerProxy<ToDoList> controllerProxy) {
        this.controllerProxy = controllerProxy;

        getStyleClass().add("todo-item-cell");
        HBox layout = new HBox();
        layout.visibleProperty().bind(emptyProperty().not());
        setGraphic(layout);

        Text itemNameText = new Text();
        itemNameText.getStyleClass().add("name-text");
        HBox.setHgrow(itemNameText, NEVER);
        layout.getChildren().add(itemNameText);

        Label spacer = new Label();
        spacer.setMaxWidth(Double.MAX_VALUE - 1);
        HBox.setHgrow(spacer, ALWAYS);
        layout.getChildren().add(spacer);

        Button deleteButton = new Button("delete");
        deleteButton.setOnAction(e -> item().ifPresent(i -> controllerProxy.invoke(REMOVE_ACTION, new Param(ITEM_PARAM, i.getText()))));
        HBox.setHgrow(deleteButton, NEVER);
        layout.getChildren().add(deleteButton);

        itemProperty().addListener((obs, oldVal, newVal) -> {
            itemNameText.textProperty().unbind();
            itemNameText.strikethroughProperty().unbind();
            if(newVal != null) {
                FXBinder.bind(itemNameText.textProperty()).to(newVal.getTextProperty());
                FXBinder.bind(itemNameText.strikethroughProperty()).bidirectionalTo(newVal.getCompletedProperty());
            }
        });

        setOnMouseClicked(e -> {
            item().ifPresent(i -> controllerProxy.invoke(CHANGE_ACTION, new Param(ITEM_PARAM, i.getText())));
        });
    }

    private Optional<ToDoItem> item() {
        return Optional.ofNullable(getItem());
    }

}
