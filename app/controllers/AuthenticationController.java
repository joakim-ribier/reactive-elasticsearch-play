package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.BodyParser;
import play.mvc.Result;
import services.AuthenticationService;
import services.ConfigurationService;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AuthenticationController extends AbstractController {

    private static final Logger LOG = LoggerFactory
            .getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Inject
    protected AuthenticationController(
            ConfigurationService configurationService,
            AuthenticationService authenticationService) {

        super(configurationService);
        this.authenticationService = authenticationService;
    }

    public Result login() {
        clearSession();
        return ok(views.html.login.render(_TITLE, hostName));
    }

    public Result logout() {
        clearSession();
        flashSuccess("You've been logged out.");
        return redirect(routes.AuthenticationController.login());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result authentication() {
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").textValue();
        String password = json.findPath("password").textValue();

        boolean isConnect = authenticationService.connect(username, password);
        if (isConnect) {
            LOG.info("The user '{}' is now connected.", username);
            clearSession();
            session("username", username);
            return ok();
        } else {
            LOG.warn("The user '{}' tries to connect to the application.",
                    username);
            return unauthorized("Wrong username or password.");
        }
    }
}
