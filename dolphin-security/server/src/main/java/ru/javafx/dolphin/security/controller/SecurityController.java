
package ru.javafx.dolphin.security.controller;

import com.canoo.dolphin.BeanManager;
import com.canoo.dolphin.server.DolphinAction;
import com.canoo.dolphin.server.DolphinController;
import com.canoo.dolphin.server.DolphinModel;
import com.canoo.dolphin.server.Param;
import com.canoo.dolphin.server.event.DolphinEventBus;
import javax.annotation.PostConstruct;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import static ru.javafx.dolphin.security.AppConstants.ADD_ACTION;
import static ru.javafx.dolphin.security.AppConstants.CHANGE_ACTION;
import static ru.javafx.dolphin.security.AppConstants.ITEM_PARAM;
import static ru.javafx.dolphin.security.AppConstants.REMOVE_ACTION;
import static ru.javafx.dolphin.security.AppConstants.SECURITY_CONTROLLER_NAME;
import static ru.javafx.dolphin.security.EventTopics.ITEM_ADDED;
import static ru.javafx.dolphin.security.EventTopics.ITEM_MARK_CHANGED;
import static ru.javafx.dolphin.security.EventTopics.ITEM_REMOVED;
import ru.javafx.dolphin.security.ItemStore;
import ru.javafx.dolphin.security.model.Item;
import ru.javafx.dolphin.security.model.ItemList;

@DolphinController(SECURITY_CONTROLLER_NAME)
public class SecurityController {

    private final BeanManager beanManager;
    private final DolphinEventBus eventBus;
    private final ItemStore itemStore;

    @DolphinModel
    private ItemList itemList;

    @Autowired
    public SecurityController(BeanManager beanManager, DolphinEventBus eventBus, ItemStore todoItemStore) {
        this.beanManager = beanManager;
        this.eventBus = eventBus;
        this.itemStore = todoItemStore;
    }

    @PostConstruct
    public void onInit() {
        eventBus.subscribe(ITEM_MARK_CHANGED, message -> updateItemState(message.getData()));
        eventBus.subscribe(ITEM_REMOVED, message -> removeItem(message.getData()));
        eventBus.subscribe(ITEM_ADDED, message -> addItem(message.getData()));
        itemStore.itemNameStream().forEach(name -> addItem(name));
    }

    @DolphinAction(ADD_ACTION)
    public void onItemAddAction() {
        itemStore.addItem(itemList.getNewItemText().get());
        itemList.getNewItemText().set("");
    }

    @DolphinAction(CHANGE_ACTION)
    public void onItemStateChangedAction(@Param(ITEM_PARAM) String name) {
        itemStore.changeItemState(name);
    }

    @DolphinAction(REMOVE_ACTION)
    public void onItemRemovedAction(@Param(ITEM_PARAM) String name) {
        itemStore.removeItem(name);
    }

    private void addItem(String name) {
        itemList.getItems().add(beanManager.create(Item.class).withText(name).withState(itemStore.getState(name)));
    }

    private void removeItem(String name) {
        getItemByName(name).ifPresent(i -> itemList.getItems().remove(i));
    }

    private void updateItemState(String name) {
        getItemByName(name).ifPresent(i -> i.setCompleted(itemStore.getState(name)));
    }

    private Optional<Item> getItemByName(String name) {
        return itemList.getItems().stream().filter(i -> i.getText().equals(name)).findAny();
    }
}
