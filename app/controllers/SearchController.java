package controllers;

import java.util.List;

import models.HitModel;
import play.mvc.Result;
import services.ConfigurationService;
import services.ESSearchService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SearchController extends AbstractController {

    @Inject
    private SearchController(ConfigurationService configurationService,
            ESSearchService esSearchService) {
        
        super(configurationService, esSearchService);
    }

    public Result index(String value) {
        List<HitModel> hitModels = esSearchService.searchByQuery(value);
        return ok(toArrayNode(hitModels));
    }
}
