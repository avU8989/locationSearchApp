package citizen.dev.location_search.services;

import citizen.dev.location_search.repository.SPARQLRepository;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SparqlService {
    @Autowired
    private SPARQLRepository sparqlRepository;
    private static final Logger logger = LoggerFactory.getLogger(SparqlService.class);

    public String fetchSingleSubjectFromDbpedia(String subjectName){
        // Query for a single subject
        String queryString = "SELECT ?subject WHERE { "
                + "?subject ?predicate ?object . "
                + "FILTER(?subject = <http://dbpedia.org/resource/" + subjectName + ">) } LIMIT 1";

        logger.info("Executing query: {}", queryString);

        ResultSet resultSet = null;
        try {
            resultSet = sparqlRepository.executeQueryOnDbpedia(queryString);
        } catch (Exception e) {
            logger.error("Error executing SPARQL query", e);
        }

        if (resultSet == null || !resultSet.hasNext()) {
            logger.warn("No subject found for query: {}", queryString);
            return null; // Return null if no result is found
        }

        // Retrieve the first subject
        QuerySolution solution = resultSet.nextSolution();
        if (solution.contains("subject")) {
            String subject = solution.get("subject").toString();
            logger.info("Fetched subject: {}", subject);
            return subject;
        } else {
            logger.warn("QuerySolution does not contain 'subject'");
            return null; // Return null if subject is not found in the result
        }
    }



    public String fetchSubjectInfo(String subjectURI) {
        try {
            // URL encode the subjectURI to handle special characters like umlauts
            subjectURI = URLEncoder.encode(subjectURI.trim(), StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while encoding: " + e.getMessage();
        }

        logger.info(subjectURI);

        String sparqlQueryTemplate = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX bif: <bif:>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                "\n" +
                "SELECT ?place ?abstract ?comment\n" +
                "WHERE {\n" +
                "  ?place rdfs:label ?label .\n" +
                "  ?place dbo:abstract ?abstract .\n" +
                "  ?place rdfs:comment ?comment .\n" +
                "  FILTER (lang(?abstract) = \"en\")\n" +
                "  FILTER (bif:contains(?label, \"%s\"))\n" +
                "  {\n" +
                "    ?place dct:subject dbc:Squares_in_Vienna .\n" +
                "  } UNION {\n" +
                "    ?place dct:subject dbc:Streets_in_Vienna .\n" +
                "  } UNION {\n" +
                "    ?place dct:subject dbc:Geography_of_Vienna .\n" +
                "  } UNION {\n" +
                "    ?place dbo:location <http://dbpedia.org/resource/Vienna> .\n" +
                "  }\n" +
                "}\n" +
                "LIMIT 1\n";

        String sparqlQuery = String.format(sparqlQueryTemplate, subjectURI);

        Query query = QueryFactory.create(sparqlQuery);
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService("https://dbpedia.org/sparql", query)) {
            ResultSet results = qexec.execSelect();
            if (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                RDFNode place = soln.get("place");
                RDFNode abstractText = soln.get("abstract");
                return String.format("Abstract: %s", abstractText.toString());
            } else {
                return "No results found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    public String fetchAbstractWithResource(String subjectLabel) {
        // Ensure subjectLabel is trimmed
        subjectLabel = subjectLabel.trim();
        logger.info(subjectLabel);

        // Escape special characters in subjectLabel
        subjectLabel = subjectLabel.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\'", "\\\'")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

        String sparqlQueryTemplate = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "\n" +
                "SELECT ?place ?abstract\n" +
                "WHERE {\n" +
                "  ?place rdfs:label ?label .\n" +
                "  ?place dbo:originalName ?originalName .\n" +
                "  ?place dbo:abstract ?abstract .\n" +
                "  FILTER (REGEX(?label, \"%s\", \"i\"))\n" +
                "  FILTER (lang(?label) = \"de\" || lang(?label) = \"en\")\n" +
                "  FILTER (lang(?abstract) = \"en\")\n" +
                "}\n" +
                "LIMIT 10\n";

        String sparqlQuery = String.format(sparqlQueryTemplate, subjectLabel);

        Query query = QueryFactory.create(sparqlQuery);
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService("https://dbpedia.org/sparql", query)) {
            ResultSet results = qexec.execSelect();
            StringBuilder resultString = new StringBuilder();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                RDFNode abstractText = soln.get("abstract");
                resultString.append(String.format(abstractText.toString()));
            }
            if (resultString.length() > 0) {
                return resultString.toString();
            } else {
                return "No results found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }


    public String fetchSubjectComment(String subjectURI) {
        subjectURI = subjectURI.trim();
        logger.info(subjectURI);

        String sparqlQueryTemplate = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX bif: <bif:>\n" +
                "\n" +
                "SELECT ?place ?abstract ?comment\n" +
                "WHERE {\n" +
                "  ?place rdfs:label ?label .\n" +
                "  ?place rdfs:comment ?comment .\n" +
                "  FILTER (lang(?comment) = \"en\")\n" +
                "  FILTER (bif:contains(?label, \"%s\"))\n" +
                "}\n" +
                "LIMIT 1\n";

        String sparqlQuery = String.format(sparqlQueryTemplate, subjectURI);

        Query query = QueryFactory.create(sparqlQuery);
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService("https://dbpedia.org/sparql", query)) {
            ResultSet results = qexec.execSelect();
            if (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                RDFNode commentText = soln.get("comment");
                return String.format("Comment: %s", commentText.toString());
            } else {
                return "No results found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }


}
