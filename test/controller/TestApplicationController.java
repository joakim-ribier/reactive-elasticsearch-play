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

public class TestApplicationController {

    @Test
    public void testIndex() {
        FakeApplication fakeApplication = fakeApplication(new GlobalTest());
        running(
                fakeApplication, () -> {
                    FakeRequest fakeIndexRequest = fakeRequest("GET", "/");
                    fakeIndexRequest.withSession("username", "admin");
                    
                    Result result = route(fakeIndexRequest);
                    
                    assertThat(status(result)).isEqualTo(Status.OK);
                }
        );
    }
    
    @Test
    public void testIndexNotLogged() {
        FakeApplication fakeApplication = fakeApplication(new GlobalTest());
        running(
                fakeApplication, () -> {
                    FakeRequest fakeIndexRequest = fakeRequest("GET", "/");
                    
                    Result result = route(fakeIndexRequest);
                    
                    assertThat(status(result)).isEqualTo(Status.SEE_OTHER);
                }
        );
    }
}
