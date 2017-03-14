
package ru.javafx.dolphin.todo.example;

import com.canoo.dolphin.server.spring.DolphinPlatformApplication;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
public class ToDoServer {

    public static void main(String... args) {
        SpringApplication.run(ToDoServer.class, args);
    }
}
