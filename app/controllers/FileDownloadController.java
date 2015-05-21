package controllers;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import models.exceptions.ESDocumentNotFound;
import models.search.PathModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Result;
import play.mvc.Security;
import services.ZipService;
import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import services.search.ESSearchService;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
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
            PathModel pathModel = esSearchService.searchFileById(id);
            Optional<Path> path = pathModel.getPath();
            if (path.isPresent()) {
                Optional<String> filename = pathModel.getFilename();
                if (filename.isPresent()) {
                    response().setHeader(
                            "Content-disposition", "attachment; filename=" + filename.get());
                }
                return ok(path.get().toFile());
            }
            return _notFound("module.download.document.not.found");
        } catch (ConfigurationServiceException e) {
            return _preconditionFailed("module.configuration.error");
        } catch (ESDocumentNotFound e) {
            return _notFound("module.download.document.not.found");
        }
    }
    
    @Security.Authenticated(Secured.class)
    public Result generateZip(String ids) {
        List<PathModel> files = getFilesFromIDs(ids);
        if(zipService.create(files)) {
            return ok();
        } else {
            return _internalServerError("module.global.error");
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
        return _internalServerError("module.global.error");
    }
    
    private List<PathModel> getFilesFromIDs(String ids) {
        List<PathModel> files = Lists.newArrayList();
        Iterable<String> iterable = Splitter.on(";").split(ids);
        iterable.forEach(id -> {
            try {
                if (!Strings.isNullOrEmpty(id)) {
                    PathModel pathModel = esSearchService.searchFileById(id);
                    Optional<Path> path = pathModel.getPath();
                    if (path.isPresent()) {
                        files.add(pathModel);
                    }
                }
            } catch (ESDocumentNotFound | ConfigurationServiceException e) {
                LOG.error(e.getMessage(), e);
            }
        });
        return files;
    }
}
