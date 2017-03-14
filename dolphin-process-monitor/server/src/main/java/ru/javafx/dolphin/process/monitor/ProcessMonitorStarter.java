
package ru.javafx.dolphin.process.monitor;

import com.canoo.dolphin.server.spring.DolphinPlatformApplication;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

@DolphinPlatformApplication
public class ProcessMonitorStarter {

    public static void main(String... args) {
        SpringApplication.run(ProcessMonitorStarter.class, args);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AsyncServerRunner createRunner() {
        final List<Runnable> myTasks = new CopyOnWriteArrayList<>();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (!myTasks.isEmpty()) {
                        Runnable task = myTasks.remove(0);
                        task.run();
                    }
                }
            }
        });

        return new AsyncServerRunner() {
            @Override
            public void execute(Runnable task) {
                myTasks.add(task);
            }
        };
    }

}
