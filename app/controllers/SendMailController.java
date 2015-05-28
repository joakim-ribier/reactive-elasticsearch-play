package controllers;

import models.MailModel;
import models.exceptions.ESDocumentNotFound;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import play.mvc.Security;
import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import services.send.SendMailService;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SendMailController extends AbstractController {
    
    private final SendMailService sendService;
    
    @Inject
    private SendMailController(ConfigurationService configurationService, SendMailService sendService) {
        super(configurationService);
        this.sendService = sendService;
    }
    
    @Security.Authenticated(Secured.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result index() {
        try {
            JsonNode json = request().body().asJson();
            MailModel mailModel = Json.fromJson(
                    json.findPath("mail"), MailModel.class);
            
            sendService.send(mailModel);
            return ok();
        } catch (ConfigurationServiceException e) {
            return _preconditionFailed("module.configuration.error");
        } catch (ESDocumentNotFound e) {
            return _internalServerError("module.global.error");
        }
    }
    
}
