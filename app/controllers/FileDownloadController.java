package controllers;

import java.io.File;
import java.util.Optional;

import models.exceptions.ESDocumentFieldNotFound;
import models.exceptions.ESDocumentNotFound;
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
        try {
            Optional<File> file = esSearchService.findFileById(id);
            if (file.isPresent()) {
                return ok(file.get());
            }
            return notFound("Document '" + id + "' not found.");
        } catch (ESDocumentNotFound | ESDocumentFieldNotFound e) {
            return notFound(e.getMessage());
        }
    }
}
