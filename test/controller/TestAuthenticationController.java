package controller;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.headers;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import guice.GlobalTest;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.mvc.Http.Status;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

public class TestAuthenticationController {

	@Test
	public void testLogin() {
		FakeApplication fakeApplication = fakeApplication(new GlobalTest());
		running(fakeApplication, () -> {
			Result result =  route(fakeRequest("GET", "/page/login"));
			
			assertThat(status(result)).isEqualTo(Status.OK);
			assertThat(contentType(result)).isEqualTo("text/html");
			assertThat(charset(result)).isEqualTo("utf-8");
		});
	}
	
	@Test
	public void testLogout() {
		FakeApplication fakeApplication = fakeApplication(new GlobalTest());
		running(fakeApplication, () -> {
			Result result =  route(fakeRequest("GET", "/logout"));
			
			assertThat(status(result)).isEqualTo(Status.SEE_OTHER);
			assertThat(headers(result).get("Location")).isEqualTo("/page/login");
		});
	}
	
	@Ignore
	@Test
	public void testAuthentication() {
		FakeApplication fakeApplication = fakeApplication(new GlobalTest());
		running(fakeApplication, () -> {
			FakeRequest fakeRequest = fakeRequest("POST", "/page/login/authentication");
			ObjectNode put = Json.newObject()
					.put("username", "admin")
					.put("password", "Banzai !!!");
			
			fakeRequest.withJsonBody(put);
			
			Result result =  route(fakeRequest);
			
			assertThat(status(result)).isEqualTo(Status.OK);
		});
	}
	
	@Test
	public void testBadAuthentication() {
		FakeApplication fakeApplication = fakeApplication(new GlobalTest());
		running(fakeApplication, () -> {
			FakeRequest fakeRequest = fakeRequest("POST", "/page/login/authentication");
			ObjectNode put = Json.newObject()
					.put("username", "bad username")
					.put("password", "bad password");
			fakeRequest.withJsonBody(put);
			
			Result result =  route(fakeRequest);
			
			assertThat(status(result)).isEqualTo(Status.UNAUTHORIZED);
		});
	}
}
