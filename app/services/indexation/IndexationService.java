package services.indexation;

import java.nio.file.Path;

public interface IndexationService {
    
    void indexAllFilesInADirectory(Path path)
            throws IndexationServiceException;
    
}
