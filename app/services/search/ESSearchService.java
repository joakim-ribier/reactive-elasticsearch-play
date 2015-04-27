package services.search;

import java.io.File;
import java.util.List;
import java.util.Optional;

import models.HitModel;
import models.exceptions.ESDocumentFieldNotFound;
import models.exceptions.ESDocumentNotFound;

public interface ESSearchService {

    List<HitModel> searchByQuery(String value);

    Optional<File> findFileById(String id) throws ESDocumentNotFound, ESDocumentFieldNotFound;
}
