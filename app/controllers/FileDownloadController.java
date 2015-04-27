package controllers;

import java.io.File;
import java.util.List;
import java.util.Optional;

import models.exceptions.ESDocumentFieldNotFound;
import models.exceptions.ESDocumentNotFound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.collections.Lists;
import org.testng.util.Strings;

import play.mvc.Result;
import play.mvc.Security;
import services.ZipService;
import services.configuration.ConfigurationService;
import services.search.ESSearchService;

import com.google.common.base.Splitter;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FileDownloadController extends AbstractController {

    private static final Logger LOG = LoggerFactory
            .getLogger(FileDownloadController.class);
    
    private final ESSearchService esSearchService;
    private final ZipService zipService;

    @Inject
    private FileDownloadController(ConfigurationService configurationService,
            ESSearchService esSearchService, ZipService zipService) {

        super(configurationService);
        this.esSearchService = esSearchService;
        this.zipService = zipService;
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
    
    @Security.Authenticated(Secured.class)
    public Result generateZip(String ids) {
        List<File> files = getFilesFromIDs(ids);
        if(zipService.create(files)) {
            return ok();
        } else {
            return internalServerError();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result downloadZip() {
        String zipFilePathName = configurationService.getTmpZipFilePathName();
        File file = new File(zipFilePathName);
        if(file.isFile()) {
            return ok(file);
        }
        LOG.error("Zip file path name '{}' not found on the server.", zipFilePathName);
        return notFound("Zip not found.");
    }
    
    private List<File> getFilesFromIDs(String ids) {
        List<File> files = Lists.newArrayList();
        Iterable<String> iterable = Splitter.on(";").split(ids);
        iterable.forEach(id -> {
            try {
                if (!Strings.isNullOrEmpty(id)) {
                    Optional<File> file = esSearchService.findFileById(id);
                    if (file.isPresent()) {
                        files.add(file.get());
                    }
                }
            } catch (ESDocumentNotFound | ESDocumentFieldNotFound e) {
                LOG.error(e.getMessage(), e);
            }
        });
        return files;
    }
}
