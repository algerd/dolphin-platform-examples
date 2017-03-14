
package ru.javafx.dolphin.springbootfx.view;

import ru.javafx.dolphin.springbootfx.core.BaseFXMLViewBinder;
import ru.javafx.dolphin.springbootfx.core.FXMLController;
import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.binding.FXBinder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import static ru.javafx.dolphin.springbootfx.AppConstants.ADD_ACTION;
import ru.javafx.dolphin.springbootfx.model.Item;
import ru.javafx.dolphin.springbootfx.model.ItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import static ru.javafx.dolphin.springbootfx.AppConstants.CONTROLLER_NAME;

@FXMLController(
    value = "/fxml/view.fxml",
    css = {"/styles/style.css"},
    title = "ToDoView"
)
@Scope("prototype")
public class ViewController extends BaseFXMLViewBinder<ItemList> {

    @FXML
    private TextField createField;
    @FXML
    private Button createButton;
    @FXML
    private ListView<Item> itemList;

    @Autowired
    public ViewController(ClientContext clientContext) {
        // прямое указание пути fxml-файла, не будет использоваться путь в @FXMLController аннотации
        //super(clientContext, TODO_CONTROLLER_NAME, ViewController.class.getResource("view.fxml"));
        
        // путь fxml-файла будет браться из @FXMLController аннотации
        super(clientContext, CONTROLLER_NAME);
    }

    @Override
    protected void init() {
        itemList.setCellFactory(c -> new ItemCell(getControllerProxy()));
        FXBinder.bind(createField.textProperty()).bidirectionalTo(getModel().getNewItemText());
        FXBinder.bind(itemList.getItems()).bidirectionalTo(getModel().getItems());
        createButton.setOnAction(event -> invoke(ADD_ACTION));
    }
    
}
