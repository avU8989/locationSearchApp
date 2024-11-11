package test_clients;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import test_utils.TestRESTOperations;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserAccountRestAPITestImpl implements UserAccountRestAPITestClient {

    private final TestRESTOperations restOperations;

    @Override
    public Map getAccountPage() {
        return restOperations.doGet("/api/accounts");
    }

    @Override
    public Map createAccount(Map request) {
        return restOperations.doPost("/api/accounts", request);
    }

    @Override
    public Map getAccount(String id) {
        return restOperations.doGet("/api/accounts/" + id);
    }

    @Override
    public Map getAccount(HttpStatus status, String id) {
        return restOperations.doGet(status, "/api/accounts/" + id);
    }

    @Override
    public Map updateAccount(String id, Map request) {
        return restOperations.doPatch("/api/accounts/" + id, request);
    }

    @Override
    public void deleteAccount(String id) {
        restOperations.doDelete("/api/accounts/" + id);
    }
}
