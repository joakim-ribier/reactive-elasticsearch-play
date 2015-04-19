package controllers;

import java.util.Map;

import play.libs.Json;
import play.mvc.Controller;
import services.ConfigurationService;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;

@Singleton
public class AbstractController extends Controller {

    protected static final String _TITLE = "Reactive AngularApp's - Elasticsearch 1.4.1";

    protected final ConfigurationService configurationService;
    protected final String hostName;

    protected AbstractController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
        this.hostName = configurationService.getHostName();
    }
    
    protected ObjectNode toObjectNode(Map<String, String> map) {
        ObjectNode objectNode = Json.newObject();
        map.forEach((key, value) -> {
            objectNode.put(key, value);
        });
        return objectNode;
    }

    protected void clearSession() {
        session().clear();
    }

    protected void flashSuccess(String message) {
        flash("success", message);
    }
}
