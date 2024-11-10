package citizen.dev.location_search.controller.nearbyservices;

import citizen.dev.location_search.services.LocationService;
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
@RequestMapping("/api/sparql")
public class NearbySightController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/nearby-sights")
    public CompletableFuture<ResponseEntity<List<Map<String, String>>>> getNearbySights(@RequestParam double latitude, @RequestParam double longitude) {
        return locationService.fetchNearbySights(latitude, longitude)
                .thenApply(sights -> {
                    if (sights == null || sights.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                    } else {
                        return ResponseEntity.ok(sights);
                    }
                });
    }
}