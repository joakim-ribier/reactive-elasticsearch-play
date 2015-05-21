package controllers;

import java.io.File;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import services.indexation.IndexationServiceException;
import services.upload.UploadService;
import utils.file.FileUtilsException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FileUploadController extends AbstractController {
    
    private final UploadService uploadService;
    
    @Inject
    private FileUploadController(ConfigurationService configurationService, UploadService uploadService) {
        super(configurationService);
        this.uploadService = uploadService;
    }
    
    @Security.Authenticated(Secured.class)
    public Result upload() {
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart filePart = body.getFile("file");
        if (filePart != null) {
            File file = filePart.getFile();
                try {
                    if (!uploadService.upload(file, filePart.getFilename())) {
                        return _internalServerError("module.global.error");
                    }
                    return ok("File uploaded");
                } catch (FileUtilsException e) {
                    return _internalServerError("module.global.error");
                } catch (ConfigurationServiceException e) {
                    return _internalServerError("module.configuration.error");
                }
        } else {
            return _preconditionFailed("module.global.error");
        }
    }
    
    @Security.Authenticated(Secured.class)
    public Result uploadAndIndex() {
        MultipartFormData body = request().body().asMultipartFormData();
        String[] tags = body.asFormUrlEncoded().get("tags");
        FilePart filePart = body.getFile("file");
        if (filePart != null) {
            File file = filePart.getFile();
                try {
                    uploadService.uploadAndIndexing(file, filePart.getFilename(), tags[0]);
                    return ok("File uploaded");
                } catch (FileUtilsException | IndexationServiceException e) {
                    return _internalServerError("module.global.error");
                } catch (ConfigurationServiceException e) {
                    return _internalServerError("module.configuration.error");
                }
        } else {
            return _preconditionFailed("module.global.error");
        }
    }
    
}
