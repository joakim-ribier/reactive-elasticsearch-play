package controllers;

import java.util.List;

import models.HitModel;
import play.mvc.Result;
import play.mvc.Security;
import services.ConfigurationService;
import services.ESSearchService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SearchController extends AbstractController {

    private final ESSearchService esSearchService;

    @Inject
    private SearchController(ConfigurationService configurationService,
            ESSearchService esSearchService) {

        super(configurationService);
        this.esSearchService = esSearchService;
    }

    @Security.Authenticated(Secured.class)
    public Result index(String value) {
        List<HitModel> hitModels = esSearchService.searchByQuery(value);
        return ok(toArrayNode(hitModels));
    }
}
