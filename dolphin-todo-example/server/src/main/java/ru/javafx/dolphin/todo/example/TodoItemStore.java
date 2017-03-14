
package ru.javafx.dolphin.todo.example;

import com.canoo.dolphin.server.event.DolphinEventBus;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import static ru.javafx.dolphin.todo.example.ToDoEventTopics.ITEM_ADDED;
import static ru.javafx.dolphin.todo.example.ToDoEventTopics.ITEM_MARK_CHANGED;
import static ru.javafx.dolphin.todo.example.ToDoEventTopics.ITEM_REMOVED;

public class TodoItemStore {

    private final DolphinEventBus eventBus;

    private final Map<String, Boolean> items = new HashMap<>();

    public TodoItemStore(DolphinEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void addItem(String name) {
        if(name != null && !name.isEmpty() && !items.containsKey(name)) {
            items.put(name, false);
            eventBus.publish(ITEM_ADDED, name);
        }
    }

    public void removeItem(String name) {
        items.remove(name);
        eventBus.publish(ITEM_REMOVED, name);
    }

    public void changeItemState(String name) {
        items.put(name, !items.get(name));
        eventBus.publish(ITEM_MARK_CHANGED, name);
    }

    public Stream<String> itemNameStream() {
        return items.keySet().stream();
    }

    public boolean getState(String name) {
        return items.get(name);
    }
}
