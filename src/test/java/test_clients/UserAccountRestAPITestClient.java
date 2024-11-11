package test_clients;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface UserAccountRestAPITestClient {
    Map getAccountPage();

    Map createAccount(Map request);

    Map getAccount(String id);

    Map getAccount(HttpStatus status, String id);

    Map updateAccount(String id, Map request);

    void deleteAccount(String id);
}
