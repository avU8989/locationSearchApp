package citizen.dev.location_search.repository;

import citizen.dev.location_search.config.SPARQLConfig;
import org.apache.jena.query.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SPARQLRepository {
    private static final String serviceURL ="http://localhost:9014/api/sparql";
    private static final Logger logger = LoggerFactory.getLogger(SPARQLRepository.class);

    @Autowired
    private SPARQLConfig.SPARQLEndpoints sparqlEndpoints;

    public ResultSet executeQuery(String sparqlQueryString, String endpointURL) {
        logger.info("Executing SPARQL query on endpoint: {}", endpointURL);
        logger.info("SPARQL query: {}", sparqlQueryString);

        try {
            QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpointURL, sparqlQueryString);
            return queryExecution.execSelect();
        } catch (Exception e) {
            logger.error("Error executing SPARQL query", e);
            return null;
        }
    }

    public ResultSet executeQueryOnDbpedia(String sparqlQueryString) {
        return executeQuery(sparqlQueryString, sparqlEndpoints.dbpediaEndpointUrl);
    }

    public ResultSet executeQueryOnWikidata(String sparqlQueryString) {
        return executeQuery(sparqlQueryString, sparqlEndpoints.wikiDataEndpointUrl);
    }


}
