package test_utils;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public TestRESTOperations testRestOperations(MockMvc mockMvc) {
        return new TestRestOperationsImpl(mockMvc); // Pass MockMvc to the constructor if needed
    }
}
