package utils.file;

import java.nio.file.Path;
import java.util.List;

public interface IFileUtils {
    
    String getContent(String filePath) throws FileUtilsException;
    String getContent(Path path) throws FileUtilsException;
    
    void write(Path path, String content) throws FileUtilsException;
    
    /**
     * Finds recursively all files in the path directory.
     * 
     * @param path directory object.
     * @return list of files.
     * @throws FileUtilsException 
     */
    List<Path> findRecursivelyAllFilesInADirectory(Path path)
            throws FileUtilsException;
    
}
