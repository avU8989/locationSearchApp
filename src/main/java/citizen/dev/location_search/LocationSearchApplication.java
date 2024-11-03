package citizen.dev.location_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {
    "citizen.dev.location_search",
    "citizen.dev.location_search.config",
    "citizen.dev.location_search.controller",
    "citizen.dev.location_search.services",
    "citizen.dev.location_search.repository"
})
public class LocationSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LocationSearchApplication.class, args);
    }
}
