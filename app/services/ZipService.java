package services;

import java.util.List;

import models.search.PathModel;

public interface ZipService {

    boolean create(List<PathModel> pathModels);

    boolean create(String fileZipPathName, List<PathModel> pathModels);

}
