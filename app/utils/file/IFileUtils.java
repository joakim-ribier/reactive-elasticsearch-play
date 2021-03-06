package utils.file;

import java.nio.file.Path;
import java.util.List;

import models.PathIndexModel;

public interface IFileUtils {
    
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
    
    /**
     * Gets content and metadata of path parameter.
     * 
     * @param path file object.
     * @return PathIndexModel object.
     * @throws FileUtilsException
     */
    PathIndexModel parse(Path path) throws FileUtilsException;
    
    Path move(PathIndexModel source, Path target) throws FileUtilsException;
    
    Path move(Path source, Path target) throws FileUtilsException;
    
    /**
     * Gets the number of files in the path parameter.
     * 
     * @param path java.nio.file.Path.
     * @return int number.
     * @throws FileUtilsException
     */
    int getNumberOfFiles(Path path) throws FileUtilsException;
    
    /**
     * Gets the copy of the source path parameter.
     *  
     * @param source path java.nio.file.Path.
     * @param filename string value.
     * @return path object.
     * 
     * @throws FileUtilsException
     */
    Path copy(Path source, String filename) throws FileUtilsException;
    
}
