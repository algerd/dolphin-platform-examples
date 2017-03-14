
package ru.javafx.dolphin.process.monitor.model;

import com.canoo.dolphin.collections.ObservableList;
import com.canoo.dolphin.mapping.DolphinBean;

@DolphinBean
public class ProcessListBean {

    private ObservableList<ProcessBean> items;

    public ObservableList<ProcessBean> getItems() {
        return items;
    }

}
