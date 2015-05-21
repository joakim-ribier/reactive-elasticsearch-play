package services.search;

import java.util.List;

import models.FacetModel;
import models.HitModel;
import models.exceptions.ESDocumentNotFound;
import models.search.PathModel;
import services.configuration.ConfigurationServiceException;

public interface ESSearchService {
    
    /**
     * Searches documents by the value parameter.
     * 
     * @param value string value.
     * @return list of HitModel object.
     */
    List<HitModel> searchByQuery(String value);
    
    /**
     * Searches a document by the id parameter.
     * 
     * @param id string value;
     * @return PathModel object.
     * 
     * @throws ESDocumentNotFound
     * @throws ConfigurationServiceException
     */
    PathModel searchFileById(String id) throws ESDocumentNotFound, ConfigurationServiceException;
    
    /**
     * Gets all tags to make a 'tag cloud'.
     * 
     * @return list of FacetModel object.
     */
    List<FacetModel> getTags();
    
}
