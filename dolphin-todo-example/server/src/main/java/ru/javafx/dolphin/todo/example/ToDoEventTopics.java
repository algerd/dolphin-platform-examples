
package ru.javafx.dolphin.todo.example;

import com.canoo.dolphin.server.event.Topic;

public interface ToDoEventTopics {

    Topic<String> ITEM_MARK_CHANGED = Topic.create("item_mark_changed");

    Topic<String> ITEM_ADDED = Topic.create("item_added");

    Topic<String> ITEM_REMOVED = Topic.create("item_removed");

}
