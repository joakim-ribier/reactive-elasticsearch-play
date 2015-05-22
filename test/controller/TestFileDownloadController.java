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

public class TestFileDownloadController {
    
    private static final String API = "/file/download/";
    private static final String BY_ID = API + "by/id/";
    
    @Test
    public void testFileDownloadByIdOk() {
        running(
            fakeApplication(new GlobalTest(true)), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", BY_ID + "1000");
                fakeIndexRequest.withSession("username", "admin");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.NOT_FOUND);
            }
        );
    }
    
    @Test
    public void testFileDownloadByIdUnAuthorized() {
        running(
            fakeApplication(new GlobalTest()), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", BY_ID + "1000");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.SEE_OTHER);
            }
        );
    }
    
}
