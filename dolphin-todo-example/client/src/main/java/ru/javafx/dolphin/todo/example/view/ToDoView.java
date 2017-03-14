
package ru.javafx.dolphin.todo.example.view;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.binding.FXBinder;
import com.canoo.dolphin.client.javafx.view.AbstractFXMLViewBinder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.ADD_ACTION;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.TODO_CONTROLLER_NAME;
import ru.javafx.dolphin.todo.example.model.ToDoItem;
import ru.javafx.dolphin.todo.example.model.ToDoList;

public class ToDoView extends AbstractFXMLViewBinder<ToDoList> {

    @FXML
    private TextField createField;

    @FXML
    private Button createButton;

    @FXML
    private ListView<ToDoItem> itemList;

    public ToDoView(ClientContext clientContext) {
        super(clientContext, TODO_CONTROLLER_NAME, ToDoView.class.getResource("view.fxml"));
    }

    @Override
    protected void init() {
        itemList.setCellFactory(c -> new ToDoItemCell(getControllerProxy()));
        FXBinder.bind(createField.textProperty()).bidirectionalTo(getModel().getNewItemText());
        FXBinder.bind(itemList.getItems()).bidirectionalTo(getModel().getItems());
        createButton.setOnAction(event -> invoke(ADD_ACTION));
    }
}
