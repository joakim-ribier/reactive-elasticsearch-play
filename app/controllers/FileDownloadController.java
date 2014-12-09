package controllers;

import java.io.File;
import java.util.Optional;

import models.exceptions.ESDocumentNotFound;

import org.elasticsearch.common.base.Strings;

import play.mvc.Result;
import play.mvc.Security;
import services.ConfigurationService;
import services.ESSearchService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FileDownloadController extends AbstractController {

    private final ESSearchService esSearchService;
    
    @Inject
    private FileDownloadController(ConfigurationService configurationService,
            ESSearchService esSearchService) {
        
        super(configurationService);
        this.esSearchService = esSearchService;
    }
    
    @Security.Authenticated(Secured.class)
    public Result index(String id) {
        if (Strings.isNullOrEmpty(id)) {
            return _badRequest(
                    "The 'id' parameter is required to fetch the document.");
            
        } else {
            try {
                Optional<File> file = esSearchService.findFileById(id);
                if (file.isPresent()) {
                    return ok(file.get());
                }
                return _internalServerError("Document not found.");
            } catch (ESDocumentNotFound e) {
                return _internalServerError(e.getMessage());
            }
        }
    }
}
