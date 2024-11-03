package citizen.dev.location_search.controller;

import citizen.dev.location_search.services.LocationService;
import citizen.dev.location_search.services.SparqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/locationSearch/api/sparql")
public class NearbyRestaurantController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/nearby-restaurants")
    public CompletableFuture<ResponseEntity<List<Map<String, String>>>> getNearbyRestaurants(@RequestParam double latitude, @RequestParam double longitude) {
        return locationService.fetchNearbyRestaurants(latitude, longitude)
                .thenApply(restaurants -> {
                    if (restaurants == null || restaurants.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                    } else {
                        return ResponseEntity.ok(restaurants);
                    }
                });
    }

}
