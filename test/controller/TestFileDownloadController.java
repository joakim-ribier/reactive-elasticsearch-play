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
import play.test.FakeApplication;
import play.test.FakeRequest;

public class TestFileDownloadController {
    
    @Test
    public void testDownloadFileWithIdNotExists() {
        FakeApplication fakeApplication = fakeApplication(new GlobalTest(true));
        running(
                fakeApplication, () -> {
                    String id = "1";
                    
                    FakeRequest fakeIndexRequest = fakeRequest("GET", "/file/download/by/id/" + id);
                    fakeIndexRequest.withSession("username", "admin");
                    
                    Result result = route(fakeIndexRequest);
                    
                    assertThat(status(result)).isEqualTo(Status.NOT_FOUND);
                }
        );
    }
    
}
