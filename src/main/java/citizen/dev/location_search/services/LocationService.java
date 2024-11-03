package citizen.dev.location_search.services;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class LocationService {

    @Autowired
    private SPARQLRepository sparqlRepository;

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    @Async
    public CompletableFuture<List<Map<String, String>>> fetchNearbyLocations(double latitude, double longitude, String locationType) {
        String sparqlQueryString = String.format(
                "SELECT ?place ?placeLabel ?location (ROUND(?dist * 100) AS ?distRounded) WHERE { " +
                        "BIND(\"Point(%f %f)\"^^geo:wktLiteral AS ?loc) . " +
                        "SERVICE wikibase:around { " +
                        "?place wdt:P625 ?location . " +
                        "bd:serviceParam wikibase:center ?loc . " +
                        "bd:serviceParam wikibase:radius \"5\" . " +
                        "} " +
                        "FILTER EXISTS { ?place wdt:P31/wdt:P279* wd:%s } . " +
                        "OPTIONAL { ?place wdt:P31 ?instance } " +
                        "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" } " +
                        "BIND(geof:distance(?loc, ?location) as ?dist) " +
                        "} " +
                        "GROUP BY ?place ?placeLabel ?location ?dist " +
                        "ORDER BY ?distRounded " +
                        "LIMIT 10", longitude, latitude, locationType
        );

        List<Map<String, String>> locations = new ArrayList<>();

        try {
            sparqlRepository.init();
            try (RepositoryConnection conn = sparqlRepository.getConnection()) {
                TupleQueryResult result = conn.prepareTupleQuery(sparqlQueryString).evaluate();
                while (result.hasNext()) {
                    BindingSet bindingSet = result.next();
                    Map<String, String> location = new HashMap<>();
                    location.put("place", bindingSet.getValue("place").stringValue());
                    location.put("placeLabel", bindingSet.getValue("placeLabel").stringValue());
                    location.put("distance", bindingSet.getValue("distRounded").stringValue());
                    locations.add(location);
                }
            }
        } catch (Exception e) {
            logger.error("Error executing SPARQL query", e);
        }

        return CompletableFuture.completedFuture(locations);
    }

    @Async
    public CompletableFuture<List<Map<String, String>>> fetchNearbyRestaurants(double latitude, double longitude) {
        return fetchNearbyLocations(latitude, longitude, "Q27077054");
    }

    @Async
    public CompletableFuture<List<Map<String, String>>> fetchNearbyTouristAttractions(double latitude, double longitude) {
        return fetchNearbyLocations(latitude, longitude, "Q570116");
    }

    @Async
    public CompletableFuture<List<Map<String, String>>> fetchNearbyBars(double latitude, double longitude) {
        return fetchNearbyLocations(latitude, longitude, "Q5307737");
    }

    @Async
    public CompletableFuture<List<Map<String, String>>> fetchNearbySights(double latitude, double longitude) {
        return fetchNearbyLocations(latitude, longitude, "Q24398318");
    }

}
