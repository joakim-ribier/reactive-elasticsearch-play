package controller;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import guice.GlobalTest;
import guice.GuiceTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import play.mvc.Http.Status;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

@RunWith(GuiceTestRunner.class)
public class TestSearchController {

    @Test
    public void testSearchByValue() {
        FakeApplication fakeApplication = fakeApplication(new GlobalTest(true));
        running(
                fakeApplication, () -> {
                    FakeRequest fakeIndexRequest = fakeRequest("GET", "/search/by/query/" + "my-file.png");
                    fakeIndexRequest.withSession("username", "admin");
                    
                    Result result = route(fakeIndexRequest);
                    
                    assertThat(status(result)).isEqualTo(Status.OK);
                }
        );
    }
    
    @Test
    public void testSearchByValueNotLogged() {
        FakeApplication fakeApplication = fakeApplication(new GlobalTest(true));
        running(
                fakeApplication, () -> {
                    FakeRequest fakeIndexRequest = fakeRequest("GET", "/search/by/query/" + "my-value");
                    
                    Result result = route(fakeIndexRequest);
                    
                    assertThat(status(result)).isEqualTo(Status.SEE_OTHER);
                }
        );
    }
}
