package io.takima.master3.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MaStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaStoreApplication.class, args);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
    }

}
