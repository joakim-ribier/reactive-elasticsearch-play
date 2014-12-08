package controllers;

import play.mvc.Result;
import services.ConfigurationService;
import services.ESSearchService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Application extends AbstractController {

    private static final String _TITLE = "Reactive AngularApp's - Elasticsearch 1.4.1";
    
    @Inject
    private Application(ConfigurationService configurationService,
            ESSearchService esSearchService) {
        
        super(configurationService, esSearchService);
    }

    public Result index() {
        return ok(views.html.index.render(_TITLE, hostName));
    }
}
