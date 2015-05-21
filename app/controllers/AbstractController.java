package controllers;

import play.mvc.Controller;
import services.configuration.ConfigurationService;
import utils.json.JsonHelper;

import com.google.inject.Singleton;

@Singleton
public class AbstractController extends Controller {
    
    protected final ConfigurationService configurationService;
    protected final String hostName;

    protected AbstractController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
        this.hostName = configurationService.getHostName();
    }
    
    protected static Status _internalServerError(String key) {
        return internalServerError(
                JsonHelper.toResultExceptionModel(key));
    }
    
    protected static Status _preconditionFailed(String key) {
        return status(PRECONDITION_FAILED,
                JsonHelper.toResultExceptionModel(key));
    }
    
    protected static Status _notFound(String key) {
        return status(NOT_FOUND,
                JsonHelper.toResultExceptionModel(key));
    }
    
    protected void clearSession() {
        session().clear();
    }

    protected void flashSuccess(String message) {
        flash("success", message);
    }
}
