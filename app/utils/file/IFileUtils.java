package utils.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IFileUtils {
    
    String getContent(String filePath) throws IOException;
    
    void write(String filePath, String content) throws IOException;
    
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
