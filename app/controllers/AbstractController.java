package controllers;

import java.util.List;

import models.HitModel;
import models.ResultOkModel;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ConfigurationService;
import services.ESSearchService;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.Singleton;

@Singleton
public class AbstractController extends Controller {

    protected final ConfigurationService configurationService;
    protected final ESSearchService esSearchService;

    protected final String hostName;

    protected AbstractController(ConfigurationService configurationService, ESSearchService esSearchService) {
        this.configurationService = configurationService;
        this.esSearchService = esSearchService;
        this.hostName = configurationService.getHostName();
    }

    protected Result _badRequest(String message) {
        return badRequest(
                new ResultOkModel("ko", message).toJson());
    }
    
    protected Result _internalServerError(String message) {
        return internalServerError(
                new ResultOkModel("ko", message).toJson());
    }
    
    protected ArrayNode toArrayNode(List<HitModel> hitModels) {
        ArrayNode arrayNode = Json.newObject().arrayNode();
        hitModels.forEach((hitModel) -> {
            arrayNode.add(Json.toJson(hitModel));
        });
        return arrayNode;
    }
}
