
package ru.javafx.dolphin.security;

import com.canoo.dolphin.server.event.DolphinEventBus;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import static ru.javafx.dolphin.security.EventTopics.ITEM_ADDED;
import static ru.javafx.dolphin.security.EventTopics.ITEM_MARK_CHANGED;
import static ru.javafx.dolphin.security.EventTopics.ITEM_REMOVED;

public class ItemStore {

    private final DolphinEventBus eventBus;

    private final Map<String, Boolean> items = new HashMap<>();

    public ItemStore(DolphinEventBus eventBus) {
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
