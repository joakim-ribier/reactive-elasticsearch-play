package utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import models.PathIndexModel;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;

@Singleton
public class FileUtils implements IFileUtils {
    
    private static final Logger LOG = LoggerFactory
            .getLogger(IFileUtils.class);
    
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
    
    @Override
    public PathIndexModel parse(Path path) throws FileUtilsException {
        try {
            FileInputStream inputstream = new FileInputStream(path.toFile());
            ParseContext context = new ParseContext();
            BodyContentHandler bodyContentHandler = new BodyContentHandler(10 * 1024 * 1024);
            Metadata metadata = new Metadata();
            
            Parser parser = new AutoDetectParser();
            parser.parse(inputstream, bodyContentHandler, metadata, context);
            
            return new PathIndexModel(path, bodyContentHandler, metadata);
        } catch (IOException | SAXException | TikaException e) {
            LOG.error(e.getMessage(), e);
            throw new FileUtilsException(e.getMessage(), e);
        }
    }
    
    @Override
    public Path move(PathIndexModel source, Path targetFolder) throws FileUtilsException {
        Path path = Paths.get(targetFolder.toFile().getAbsolutePath(),
                buildFileName(source));
        
        return move(source.getPath(), path);
    }
    
    @Override
    public Path move(Path source, Path target) throws FileUtilsException {
        try {
            return Files.move(source, target);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new FileUtilsException(e.getMessage(), e);
        }
    }
    
    private String buildFileName(PathIndexModel source) {
        long millis = DateTime.now().getMillis();
        return String.valueOf(millis) + "." + source.getExtension();
    }
    
    @Override
    public int getNumberOfFiles(Path path) throws FileUtilsException {
        List<Path> paths = findRecursivelyAllFilesInADirectory(path);
        return paths.size();
    }
    
}
