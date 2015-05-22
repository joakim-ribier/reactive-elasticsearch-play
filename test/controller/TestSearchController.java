package controller;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import guice.GlobalTest;

import org.junit.Test;

import play.mvc.Http.Status;
import play.mvc.Result;
import play.test.FakeRequest;

public class TestSearchController {
    
    private static final String API = "/search/";
    private static final String BY_QUERY = API + "by/query/";
    private static final String TAGS = API + "tags/";
    
    @Test
    public void testSearchByValueOk() {
        running(
            fakeApplication(new GlobalTest(true)), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", BY_QUERY + "my-file.png");
                fakeIndexRequest.withSession("username", "admin");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.OK);
            }
        );
    }
    
    @Test
    public void testSearchByValueUnAuthorized() {
        running(
            fakeApplication(new GlobalTest(false)), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", BY_QUERY + "my-value");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.SEE_OTHER);
            }
        );
    }
    
    @Test
    public void testGetTagsOk() {
        running(
            fakeApplication(new GlobalTest(true)), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", TAGS + "10");
                fakeIndexRequest.withSession("username", "admin");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.OK);
            }
        );
    }
    
    @Test
    public void testGetTagsUnAuthorized() {
        running(
            fakeApplication(new GlobalTest()), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", TAGS + "10");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.SEE_OTHER);
            }
        );
    }
    
}
