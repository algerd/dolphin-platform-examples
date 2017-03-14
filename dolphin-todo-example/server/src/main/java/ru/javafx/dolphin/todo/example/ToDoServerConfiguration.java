package ru.javafx.dolphin.todo.example;

import com.canoo.dolphin.server.event.DolphinEventBus;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ToDoServerConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TodoItemStore createStore(DolphinEventBus eventBus) {
        return new TodoItemStore(eventBus);
    }

}
