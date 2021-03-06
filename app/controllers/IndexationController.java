package controllers;

import java.nio.file.Path;

import play.mvc.Result;
import play.mvc.Security;
import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import services.indexation.IndexationService;
import services.indexation.IndexationServiceException;
import utils.json.JsonHelper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IndexationController extends AbstractController {
    
    private final IndexationService indexationService;
    
    @Inject
    private IndexationController(ConfigurationService configurationService,
            IndexationService indexationService) {

        super(configurationService);
        this.indexationService = indexationService;
    }
    
    @Security.Authenticated(Secured.class)
    public Result index() {
        try {
            Path path = configurationService.getPathDirToIndexFiles();
            indexationService.indexingAllFilesInADirectory(path);
            return ok();
        } catch (ConfigurationServiceException e) {
            return _preconditionFailed("module.configuration.error");
        } catch (IndexationServiceException e) {
            return _internalServerError("module.global.error");
        }
    }
    
    @Security.Authenticated(Secured.class)
    public Result getNumber() {
        try {
            int number = indexationService.getNumberOfFilesWaitingToBeIndexed();
            return ok(JsonHelper.toObjectNode("number", number));
        } catch (ConfigurationServiceException e) {
            return _preconditionFailed("module.configuration.error");
        } catch (IndexationServiceException e) {
            return _internalServerError("module.global.error");
        }
    }
    
}
