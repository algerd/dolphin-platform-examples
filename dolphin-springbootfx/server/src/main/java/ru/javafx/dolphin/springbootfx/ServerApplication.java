
package ru.javafx.dolphin.springbootfx;

import com.canoo.dolphin.server.spring.DolphinPlatformApplication;
import org.springframework.boot.SpringApplication;

@DolphinPlatformApplication
public class ServerApplication {

    public static void main(String... args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
