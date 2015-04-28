package services.search;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import models.HitModel;
import models.exceptions.ESDocumentNotFound;
import services.configuration.ConfigurationServiceException;

public interface ESSearchService {
    
    List<HitModel> searchByQuery(String value);
    
    Optional<Path> searchFileById(String id) throws ESDocumentNotFound, ConfigurationServiceException;
    
}
