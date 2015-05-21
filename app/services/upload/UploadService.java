package services.upload;

import java.io.File;

import services.configuration.ConfigurationServiceException;
import services.indexation.IndexationServiceException;
import utils.file.FileUtilsException;

public interface UploadService {
    
    boolean upload(File file, String filename)
            throws FileUtilsException, ConfigurationServiceException;
    
    /**
     * Uploads and indexing file with tags.
     * 
     * @param file java.io.file parameter.
     * @param filename string parameter.
     * @param tags string parameter.
     * 
     * @throws FileUtilsException
     * @throws ConfigurationServiceException
     * @throws IndexationServiceException
     */
    void uploadAndIndexing(File file, String filename, String tags)
            throws FileUtilsException, ConfigurationServiceException,
            IndexationServiceException;
    
}
