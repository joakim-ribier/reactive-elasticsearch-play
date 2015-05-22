package controller;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import guice.GlobalTest;

import org.junit.Test;

import play.mvc.Http.Status;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

public class TestIndexationController {
    
    private static final String API = "/index/";
    private static final String GET_NUMBER_API = API + "number-files-waiting-to-be-indexed";
    private static final String INDEX_ALL_API = API + "index-all-files-in-directory";
    
    @Test
    public void testIndexingOk() {
        running(
            fakeApplication(new GlobalTest(true)), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", INDEX_ALL_API);
                fakeIndexRequest.withSession("username", "admin");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.INTERNAL_SERVER_ERROR);
            }
        );
    }
    
    @Test
    public void testIndexingUnAuthorized() {
        running(
            fakeApplication(new GlobalTest()), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", INDEX_ALL_API);
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.SEE_OTHER);
            }
        );
    }
    
    @Test
    public void testGetNumberOk() {
        FakeApplication fakeApplication = fakeApplication(new GlobalTest());
        running(
            fakeApplication, () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", GET_NUMBER_API);
                fakeIndexRequest.withSession("username", "admin");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.OK);
                assertThat(contentAsString(result)).contains("{\"number\":\"1\"}");
            }
        );
    }
    
    @Test
    public void testGetNumberUnAuthorized() {
        FakeApplication fakeApplication = fakeApplication(new GlobalTest());
        running(
            fakeApplication, () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", GET_NUMBER_API);
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.SEE_OTHER);
            }
        );
    }
    
}
