package services.indexation;

import java.nio.file.Path;

import services.configuration.ConfigurationServiceException;

public interface IndexationService {
    
    void indexAllFilesInADirectory(Path path)
            throws IndexationServiceException;
    
    /**
     * Gets the number of files waiting to be indexed.
     * 
     * @return int value.
     * @throws ConfigurationServiceException
     * @throws IndexationServiceException
     */
    int getNumberOfFilesWaitingToBeIndexed()
            throws ConfigurationServiceException, IndexationServiceException;
    
}
