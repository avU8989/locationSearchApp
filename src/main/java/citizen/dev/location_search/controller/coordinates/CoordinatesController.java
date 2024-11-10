package citizen.dev.location_search.controller.coordinates;

import citizen.dev.location_search.services.DBPediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "http://localhost:3000")  // only for testing locally with react
public class CoordinatesController {
    private static final Logger logger = LoggerFactory.getLogger(CoordinatesController.class);
    @Autowired
    private DBPediaService dbPediaService;

    @GetMapping("/coordinates")
    public CompletableFuture<String> getCoordinates(@RequestParam String location) {
        CompletableFuture<String> response = dbPediaService.getCoordinates(location);
        try {
            logger.info(response.get());
        } catch (Exception e) {

        }
        return response;
    }
}
