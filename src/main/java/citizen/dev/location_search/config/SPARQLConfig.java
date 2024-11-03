package citizen.dev.location_search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SPARQLConfig {

    @Value("${dbpedia.endpoint.url}")
    private String dbpediaEndpointUrl;

    @Value("${wikidata.endpoint.url}")
    private String wikiDataEndpointUrl;


    @Bean
    public SPARQLEndpoints sparqlEndpoints(){
        return new SPARQLEndpoints(wikiDataEndpointUrl, dbpediaEndpointUrl);
    }

    //necessary to declare SPARQLEndpoints in the config class
    public class SPARQLEndpoints {
        public final String dbpediaEndpointUrl;
        public final String wikiDataEndpointUrl;

        public SPARQLEndpoints(String wikiDataEndpointUrl, String dbpediaEndpointUrl) {
            this.dbpediaEndpointUrl = dbpediaEndpointUrl;
            this.wikiDataEndpointUrl = wikiDataEndpointUrl;
        }
    }

}
