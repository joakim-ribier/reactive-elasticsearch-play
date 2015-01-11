package controllers;

import java.util.List;

import models.HitModel;
import play.libs.Json;
import play.mvc.Controller;
import services.ConfigurationService;

import com.fasterxml.jackson.databind.node.ArrayNode;
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

    protected ArrayNode toArrayNode(List<HitModel> hitModels) {
        ArrayNode arrayNode = Json.newObject().arrayNode();
        hitModels.forEach((hitModel) -> {
            arrayNode.add(Json.toJson(hitModel));
        });
        return arrayNode;
    }

    protected void clearSession() {
        session().clear();
    }

    protected void flashSuccess(String message) {
        flash("success", message);
    }
}
