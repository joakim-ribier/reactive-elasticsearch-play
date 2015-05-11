package services.upload;

import java.io.File;

import services.configuration.ConfigurationServiceException;
import utils.file.FileUtilsException;

public interface UploadService {
    
    boolean upload(File file, String filename)
            throws FileUtilsException, ConfigurationServiceException;
    
}
