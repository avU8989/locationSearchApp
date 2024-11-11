package test_utils;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

public interface TestRESTOperations {

    //accepts "raw" json, expects 200 status and returns mockmvc result
    ResultActions postActions(String path, String content);

    ResultActions putActions(String path, String content);

    //perform mockmvc-get, expect 200 status and reads response into map/list objects
    <T> T doGet(String path);

    //perform mockmvc-get, expect given status and parse response into object
    <T> T doGet(HttpStatus expectedStatus, String path);

    <T> T doPostWithStatus(HttpStatus expectedStatus, String path, String content);

    <T> T doPost(String path, Object request);

    <T> T doPatch(String path, Object request);

    <T> T doPut(String path, Object request);

    void doDelete(String path);


}
