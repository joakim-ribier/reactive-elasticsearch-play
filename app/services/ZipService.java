package services;

import java.io.File;
import java.util.List;

public interface ZipService {

    boolean create(List<File> files);

    boolean create(String fileZipPathName, List<File> files);

}
