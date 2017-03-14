
package ru.javafx.dolphin.security.view;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.binding.FXBinder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import static ru.javafx.dolphin.security.AppConstants.ADD_ACTION;
import static ru.javafx.dolphin.security.AppConstants.SECURITY_CONTROLLER_NAME;
import ru.javafx.dolphin.security.core.BaseFXMLViewBinder;
import ru.javafx.dolphin.security.core.FXMLController;
import ru.javafx.dolphin.security.model.Item;
import ru.javafx.dolphin.security.model.ItemList;

@FXMLController(
    value = "/fxml/view.fxml",
    css = {"/styles/style.css"},
    title = "ToDoView"
)
public class ViewController extends BaseFXMLViewBinder<ItemList> {

    @FXML
    private TextField createField;
    @FXML
    private Button createButton;
    @FXML
    private ListView<Item> itemList;

    public ViewController(ClientContext clientContext) {
        // прямое указание пути fxml-файла, не будет использоваться путь в @FXMLController аннотации
        //super(clientContext, SECURITY_CONTROLLER_NAME, ToDoView.class.getResource("view.fxml"));
        
        // путь fxml-файла будет браться из @FXMLController аннотации
        super(clientContext, SECURITY_CONTROLLER_NAME);
    }

    @Override
    protected void init() {
        itemList.setCellFactory(c -> new ItemCell(getControllerProxy()));
        FXBinder.bind(createField.textProperty()).bidirectionalTo(getModel().getNewItemText());
        FXBinder.bind(itemList.getItems()).bidirectionalTo(getModel().getItems());
        createButton.setOnAction(event -> invoke(ADD_ACTION));
    }
    
}
