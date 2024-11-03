package citizen.dev.location_search.services;

import citizen.dev.location_search.repository.SPARQLRepository;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WikidataService {
    @Autowired
    private SPARQLRepository sparqlRepository;
    private static final Logger logger = LoggerFactory.getLogger(SparqlService.class);
    //query for city attractions of vienna
/*
    SELECT ?item ?itemLabel ?image
    WHERE {
  ?item wdt:P31/wdt:P279* wd:Q570116;  # ?item is a type of tourist attraction
        wdt:P18 ?image;               # ?item has an image
        wdt:P131 wd:Q1741.            # ?item is located in Vienna
        SERVICE wikibase:label { bd:serviceParam wikibase:language "de". }
    }
    LIMIT 10

 */

    public List<String[]> fetchImagesByText(String text) {
        String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
                "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +
                "SELECT ?item ?itemLabel ?image ?imageLegend WHERE { \n" +
                "  ?item rdfs:label \"" + text + "\"@de; \n" +
                "        wdt:P18 ?image. \n" +
                "  OPTIONAL { ?item wdt:P2096 ?imageLegend. }\n" +
                "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"de\". } \n" +
                "} LIMIT 10";

        logger.info("Executing query for image by text: {}", queryString);

        ResultSet resultSet = sparqlRepository.executeQueryOnWikidata(queryString);

        List<String[]> imagesWithLegends = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String image = solution.contains("image") ? solution.get("image").toString() : null;
                String legend = solution.contains("imageLegend") ? solution.get("imageLegend").toString() : null;
                imagesWithLegends.add(new String[]{image, legend});
            }
        }

        return imagesWithLegends;
    }

    public List<String[]> fetchImagesByTextEnglisch(String text) {
        String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
                "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "SELECT ?item ?itemLabel ?image ?description WHERE { \n" +
                "  ?item rdfs:label \"" + text + "\"@en; \n" +
                "        wdt:P18 ?image; \n" +
                "        wdt:P17 wd:Q40. \n" +
                "  OPTIONAL { ?item schema:description ?description. FILTER (lang(?description) = \"en\") }\n" +
                "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"de\". } \n" +
                "} LIMIT 1";

        logger.info("Executing query for image by text: {}", queryString);

        ResultSet resultSet = sparqlRepository.executeQueryOnWikidata(queryString);

        List<String[]> imagesWithDescriptions = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String image = solution.contains("image") ? solution.get("image").toString() : null;
                String description = solution.contains("description") ? solution.get("description").toString().replace("@en", "") : null;
                imagesWithDescriptions.add(new String[]{image, description});
            }
        }

        return imagesWithDescriptions;
    }
}
