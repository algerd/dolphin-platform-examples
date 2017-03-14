
package ru.javafx.dolphin.process.monitor.view;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.binding.FXBinder;
import com.canoo.dolphin.client.javafx.binding.FXWrapper;
import com.canoo.dolphin.client.javafx.view.AbstractViewBinder;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static ru.javafx.dolphin.process.monitor.Constants.CONTROLLER_NAME;
import ru.javafx.dolphin.process.monitor.model.ProcessBean;
import ru.javafx.dolphin.process.monitor.model.ProcessListBean;

public class ProcessMonitorView extends AbstractViewBinder<ProcessListBean> {

    private TableView<ProcessBean> table;

    public ProcessMonitorView(ClientContext clientContext) {
        super(clientContext, CONTROLLER_NAME);
        table = new TableView<>();

        TableColumn<ProcessBean, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(e -> FXWrapper.wrapObjectProperty(e.getValue().processIDProperty()));

        TableColumn<ProcessBean, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(e -> FXWrapper.wrapObjectProperty(e.getValue().nameProperty()));

        TableColumn<ProcessBean, String> cpuColumn = new TableColumn<>("CPU %");
        cpuColumn.setCellValueFactory(e -> FXWrapper.wrapObjectProperty(e.getValue().cpuPercentageProperty()));

        TableColumn<ProcessBean, String> memoryColumn = new TableColumn<>("Memory %");
        memoryColumn.setCellValueFactory(e -> FXWrapper.wrapObjectProperty(e.getValue().memoryPercentageProperty()));

        table.getColumns().addAll(idColumn, nameColumn, cpuColumn, memoryColumn);
    }

    @Override
    protected void init() {
        FXBinder.bind(table.getItems()).bidirectionalTo(getModel().getItems());
    }

    @Override
    public Node getRootNode() {
        return table;
    }

}
