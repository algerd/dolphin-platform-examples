/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.javafx.dolphin.todo.example.model;

import com.canoo.dolphin.mapping.DolphinBean;
import com.canoo.dolphin.mapping.Property;

@DolphinBean
public class ToDoItem {

    private Property<String> text;
    private Property<Boolean> completed;

    public String getText() {
        return text.get();
    }
    public void setText(String text) {
        this.text.set(text);
    }
    public Property<String> getTextProperty() {
        return text;
    }


    public ToDoItem withText(final String text) {
        setText(text);
        return this;
    }


    public boolean isCompleted() {
        return Boolean.TRUE == completed.get();
    }
    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }
    public Property<Boolean> getCompletedProperty() {
        return completed;
    }
    public ToDoItem withState(final boolean state) {
        setCompleted(state);
        return this;
    }

}
