package citizen.dev.location_search.services;

import citizen.dev.location_search.repository.SPARQLRepository;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class DBPediaService {
    @Autowired
    private SPARQLRepository sparqlRepository;

    private static final String QUERY_TEMPLATE =
            "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                    "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                    "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                    "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                    "SELECT ?location ?locationName ?latitude ?longitude\n" +
                    "WHERE {\n" +
                    "  ?location rdfs:label ?locationName ;\n" +
                    "            geo:lat ?latitude ;\n" +
                    "            geo:long ?longitude ;\n" +
                    "            dct:subject dbc:Squares_in_Vienna .\n" +
                    "  FILTER (lang(?locationName) = \"de\")\n" +
                    "  FILTER (CONTAINS(?locationName, \"%s\"))\n" +
                    "}";

    @Async
    @Cacheable("coordinates")
    public CompletableFuture<String> getCoordinates(String location) {
        // Ensure the location does not have leading/trailing spaces
        location = location.trim();

        String query = String.format(QUERY_TEMPLATE, location);
        ResultSet resultSet = sparqlRepository.executeQueryOnDbpedia(query);

        if (resultSet == null) {
            return CompletableFuture.completedFuture("Error executing SPARQL query.");
        }

        String result = extractLatLong(resultSet);
        return CompletableFuture.completedFuture(result);
    }

    private String extractLatLong(ResultSet resultSet) {
        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            String latitude = solution.get("latitude").asLiteral().getString();
            String longitude = solution.get("longitude").asLiteral().getString();
            return String.format("<%s, %s>", latitude, longitude);
        }
        return "Coordinates not found.";
    }
}