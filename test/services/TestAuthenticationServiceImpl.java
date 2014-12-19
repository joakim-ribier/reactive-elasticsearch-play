package services;

import static org.fest.assertions.Assertions.assertThat;
import guice.GuiceTestRunner;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(GuiceTestRunner.class)
public class TestAuthenticationServiceImpl {

    private static final String BAD_PASSWORD = "bad password";
    private static final String GOOD_PASSWORD = "Banzai !!!";
    private static final String GOOD_USERNAME = "admin";
    
    @Inject
    AuthenticationService authenticationService;
    
    @Test
    public void testConnect() throws IOException {
        assertThat(
                authenticationService.connect(GOOD_USERNAME, GOOD_PASSWORD))
                .isTrue();
    }
    
    @Test
    public void testConnectWithBadPassword() throws IOException {
        assertThat(
                authenticationService.connect(GOOD_USERNAME, BAD_PASSWORD))
                .isFalse();
    }
    
    @Test
    public void testConnectWithBadUserName() throws IOException {
        assertThat(
                authenticationService.connect("bad username", GOOD_PASSWORD))
                .isFalse();
    }
}
