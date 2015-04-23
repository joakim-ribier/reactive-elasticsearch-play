package utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;


public class FileUtils implements IFileUtils {
    
    private static final Logger LOG = LoggerFactory
            .getLogger(IFileUtils.class);
    
    @Override
    public String getContent(String pathname) throws FileUtilsException {
        return getContent(Paths.get(pathname));
    }
    
    @Override
    public String getContent(Path path) throws FileUtilsException {
        return getContent(path.toFile());
    }
    
    private String getContent(File file) throws FileUtilsException {
        try {
            return com.google.common.io.Files.toString(file, Charsets.UTF_8);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new FileUtilsException(e.getMessage(), e);
        }
    }
    
    @Override
    public void write(Path path, String content) throws FileUtilsException {
        try {
            com.google.common.io.Files.write(content, path.toFile(), Charsets.UTF_8);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new FileUtilsException(e.getMessage(), e);
        }
    }
    
    @Override
    public List<Path> findRecursivelyAllFilesInADirectory(Path path)
            throws FileUtilsException {
        
        List<Path> files = Lists.newArrayList();
        try {
            Files.walk(path).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    files.add(filePath);
                }
            });
            return files;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new FileUtilsException(e.getMessage(), e);
        }
    }
    
}
