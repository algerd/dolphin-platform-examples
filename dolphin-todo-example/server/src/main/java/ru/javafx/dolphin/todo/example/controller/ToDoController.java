
package ru.javafx.dolphin.todo.example.controller;

import com.canoo.dolphin.BeanManager;
import com.canoo.dolphin.server.DolphinAction;
import com.canoo.dolphin.server.DolphinController;
import com.canoo.dolphin.server.DolphinModel;
import com.canoo.dolphin.server.Param;
import com.canoo.dolphin.server.event.DolphinEventBus;
import javax.annotation.PostConstruct;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.dolphin.todo.example.TodoItemStore;
import static ru.javafx.dolphin.todo.example.ToDoEventTopics.ITEM_ADDED;
import static ru.javafx.dolphin.todo.example.ToDoEventTopics.ITEM_MARK_CHANGED;
import static ru.javafx.dolphin.todo.example.ToDoEventTopics.ITEM_REMOVED;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.ADD_ACTION;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.CHANGE_ACTION;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.ITEM_PARAM;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.REMOVE_ACTION;
import static ru.javafx.dolphin.todo.example.TodoAppConstants.TODO_CONTROLLER_NAME;
import ru.javafx.dolphin.todo.example.model.ToDoItem;
import ru.javafx.dolphin.todo.example.model.ToDoList;

@DolphinController(TODO_CONTROLLER_NAME)
public class ToDoController {

    private final BeanManager beanManager;

    private final DolphinEventBus eventBus;

    private final TodoItemStore todoItemStore;

    @DolphinModel
    private ToDoList toDoList;

    @Autowired
    public ToDoController(BeanManager beanManager, DolphinEventBus eventBus, TodoItemStore todoItemStore) {
        this.beanManager = beanManager;
        this.eventBus = eventBus;
        this.todoItemStore = todoItemStore;
    }

    @PostConstruct
    public void onInit() {
        eventBus.subscribe(ITEM_MARK_CHANGED, message -> updateItemState(message.getData()));
        eventBus.subscribe(ITEM_REMOVED, message -> removeItem(message.getData()));
        eventBus.subscribe(ITEM_ADDED, message -> addItem(message.getData()));
        todoItemStore.itemNameStream().forEach(name -> addItem(name));
    }

    @DolphinAction(ADD_ACTION)
    public void onItemAddAction() {
        todoItemStore.addItem(toDoList.getNewItemText().get());
        toDoList.getNewItemText().set("");
    }

    @DolphinAction(CHANGE_ACTION)
    public void onItemStateChangedAction(@Param(ITEM_PARAM) String name) {
        todoItemStore.changeItemState(name);
    }

    @DolphinAction(REMOVE_ACTION)
    public void onItemRemovedAction(@Param(ITEM_PARAM) String name) {
        todoItemStore.removeItem(name);
    }

    private void addItem(String name) {
        toDoList.getItems().add(beanManager.create(ToDoItem.class).withText(name).withState(todoItemStore.getState(name)));
    }

    private void removeItem(String name) {
        getItemByName(name).ifPresent(i -> toDoList.getItems().remove(i));
    }

    private void updateItemState(String name) {
        getItemByName(name).ifPresent(i -> i.setCompleted(todoItemStore.getState(name)));
    }

    private Optional<ToDoItem> getItemByName(String name) {
        return toDoList.getItems().stream().filter(i -> i.getText().equals(name)).findAny();
    }
}
