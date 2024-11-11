package citizen.dev.location_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"citizen.dev.location_search", "test_clients"})
public class LocationSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LocationSearchApplication.class, args);
    }
}
