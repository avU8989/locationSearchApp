package citizen.dev.location_search.controller;

import citizen.dev.location_search.services.LocationService;
import citizen.dev.location_search.services.SparqlService;
import citizen.dev.location_search.services.WikidataService;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/sparql")
@CrossOrigin(origins = "http://localhost:3000")  // only for testing locally with react
public class SPARQLController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private SparqlService sparqlService;

    @GetMapping("/dbpedia/subjects")
    public ResponseEntity<String> getSubjectsFromDbpedia(@RequestParam String subjectName) {
        String subject = sparqlService.fetchSingleSubjectFromDbpedia(subjectName);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(subject);
    }

    @GetMapping("/dbpedia/subject/abstract")
    public ResponseEntity<String> fetchSubjectAbstract(@RequestParam String locationName) {
        String abstractFromSubject = sparqlService.fetchSubjectInfo(locationName);

        if (abstractFromSubject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String cleanAbstract = abstractFromSubject.replaceFirst("^Abstract:\\s*", "").replaceFirst("@[\\w-]+$", "").trim();
        return ResponseEntity.ok(cleanAbstract);
    }

    @GetMapping("/dbpedia/subjectWithResource/abstract")
    public ResponseEntity<String> fetchAbstractWithResource(@RequestParam String locationName) {
        String abstractFromSubject = sparqlService.fetchAbstractWithResource(locationName);

        if (abstractFromSubject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String cleanAbstract = abstractFromSubject.replaceFirst("^Abstract:\\s*", "").replaceFirst("@[\\w-]+$", "").trim();
        return ResponseEntity.ok(cleanAbstract);
    }

    @GetMapping("/dbpedia/subject/comment")
    public ResponseEntity<String> fetchSubjectComment(@RequestParam String locationName) {
        String abstractFromSubject = sparqlService.fetchSubjectComment(locationName);

        if (abstractFromSubject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String cleanAbstract = abstractFromSubject.replaceFirst("^Comment:\\s*", "").replaceFirst("@[\\w-]+$", "").trim();
        return ResponseEntity.ok(cleanAbstract);
    }

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

    @GetMapping("/nearby-bars")
    public CompletableFuture<ResponseEntity<List<Map<String, String>>>> getNearbyBars(@RequestParam double latitude, @RequestParam double longitude) {
        return locationService.fetchNearbyBars(latitude, longitude)
                .thenApply(bars -> {
                    if (bars == null || bars.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                    } else {
                        return ResponseEntity.ok(bars);
                    }
                });
    }

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

    @GetMapping("/nearby-tourist-attractions")
    public CompletableFuture<ResponseEntity<List<Map<String, String>>>> getNearbyTouristAttractions(@RequestParam double latitude, @RequestParam double longitude) {
        return locationService.fetchNearbyTouristAttractions(latitude, longitude)
                .thenApply(touristAttractions -> {
                    if (touristAttractions == null || touristAttractions.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                    } else {
                        return ResponseEntity.ok(touristAttractions);
                    }
                });
    }


}
