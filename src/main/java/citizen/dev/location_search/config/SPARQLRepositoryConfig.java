package citizen.dev.location_search.config;

import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SPARQLRepositoryConfig {

    private static final String SPARQL_ENDPOINT = "https://query.wikidata.org/sparql";

    @Bean
    public org.eclipse.rdf4j.repository.sparql.SPARQLRepository sparqlRepository() {
        return new SPARQLRepository(SPARQL_ENDPOINT);
    }
}
