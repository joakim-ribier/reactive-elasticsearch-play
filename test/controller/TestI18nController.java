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


public class TestI18nController {
    
    private static final String API = "/i18n/";
    private static final String GET = API + "get";
    
    @Test
    public void testGetOk() {
        running(
            fakeApplication(new GlobalTest()), () -> {
                FakeRequest fakeIndexRequest = fakeRequest("GET", GET + "?lang=en");
                
                Result result = route(fakeIndexRequest);
                
                assertThat(status(result)).isEqualTo(Status.OK);
            }
        );
    }
    
}
