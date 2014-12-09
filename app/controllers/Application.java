package controllers;

import play.mvc.Result;
import play.mvc.Security;
import services.ConfigurationService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Application extends AbstractController {

    @Inject
    private Application(ConfigurationService configurationService) {
        super(configurationService);
    }

    @Security.Authenticated(Secured.class)
    public Result index() {
        return ok(views.html.index.render(_TITLE, hostName));
    }
}
