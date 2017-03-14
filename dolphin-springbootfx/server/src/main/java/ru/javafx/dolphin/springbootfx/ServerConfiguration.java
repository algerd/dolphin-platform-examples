package ru.javafx.dolphin.springbootfx;

import com.canoo.dolphin.server.event.DolphinEventBus;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ServerConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ItemStore createStore(DolphinEventBus eventBus) {
        return new ItemStore(eventBus);
    }

}
