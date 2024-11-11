import citizen.dev.location_search.LocationSearchApplication
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import test_clients.UserAccountRestAPITestClient
import test_utils.BaseFullContextTest
import test_utils.TestConfig

// Import your main application class
// Import your main application class
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
// Enables MockMvc for dependency injection in TestRestOperationsImpl
@ActiveProfiles("test")
@ContextConfiguration(classes = [LocationSearchApplication.class, TestConfig.class])
@SpringBootTest
class TestClientInGroovyUserAccount extends BaseFullContextTest {
    @Autowired
    private UserAccountRestAPITestClient apiTestClient

    @Test
    @Order(0)
    void createNewAccount() {
        def response = apiTestClient.createAccount([
                "firstname": "Anh",
                "surname"  : "Vu",
                "email"    : "test123@live.de",
                "password" : "securepassword",
                "birthday" : "1990-01-01"
        ])

        assert response["firstname"] == "Anh"
        assert response["surname"] == "Vu"
        assert response["email"] == "test123@live.de"
        assert response["password"] != "securepassword"
        assert response["password"].startsWith('$2a$') // Indicates BCrypt hashing format
        assert response["birthday"] == "1990-01-01"
    }

}
