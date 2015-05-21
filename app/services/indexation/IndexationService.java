package services.indexation;

import java.nio.file.Path;

import services.configuration.ConfigurationServiceException;

public interface IndexationService {
    
    void indexingAllFilesInADirectory(Path path)
            throws IndexationServiceException, ConfigurationServiceException;
    
    /**
     * Gets the number of files waiting to be indexed.
     * 
     * @return int value.
     * @throws ConfigurationServiceException
     * @throws IndexationServiceException
     */
    int getNumberOfFilesWaitingToBeIndexed()
            throws ConfigurationServiceException, IndexationServiceException;
    
    /**
     * Indexing a target file parameter with tags.
     * 
     * @param target file parameter.
     * @param tags string parameter.
     * 
     * @throws IndexationServiceException
     * @throws ConfigurationServiceException
     */
    void indexing(Path target, String tags) 
            throws IndexationServiceException, ConfigurationServiceException;
    
}
