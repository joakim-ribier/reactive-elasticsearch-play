package services.search;

import java.util.List;

import models.HitModel;
import models.exceptions.ESDocumentNotFound;
import models.search.PathModel;
import services.configuration.ConfigurationServiceException;

public interface ESSearchService {
    
    List<HitModel> searchByQuery(String value);
    
    PathModel searchFileById(String id) throws ESDocumentNotFound, ConfigurationServiceException;
    
}
