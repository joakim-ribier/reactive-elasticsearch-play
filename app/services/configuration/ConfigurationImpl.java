package services.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import play.Play;

import com.google.common.base.Strings;
import com.google.inject.Singleton;

@Singleton
public class ConfigurationImpl implements ConfigurationService {
    
    private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
    private static final String APPLICATION_HOST_KEY = "application.hostname";
    
    @Override
    public String getHostName() {
        return get(APPLICATION_HOST_KEY);
    }

    @Override
    public String get(String key) {
        return Play.application().configuration().getString(key);
    }
    
    @Override
    public String getTmpDir() {
        return System.getProperty(JAVA_IO_TMPDIR);
    }
    
    @Override
    public String getTmpZipFilePathName() {
        String key = "file.download.documents.zip.name";
        
        String tmpDir = getTmpDir();
        String zipName = get(key);
        return tmpDir + "/" + zipName + ".zip";
    }
    
    @Override
    public Path getPathDirToIndexFiles() throws ConfigurationServiceException {
        String key = "module.indexation.path.files";
        
        String pathname = get(key);
        Path path = Paths.get(pathname);
        if (Files.isDirectory(path)) {
            return path;
        }
        throw new ConfigurationServiceException(
                "The key '{}' is not a directory.", key);
    }
    
    @Override
    public Path getAuthPasswordPath() throws ConfigurationServiceException {
        String key = "module.authentication.password.file";
        
        String pathname = get(key);
        Path path = Paths.get(pathname);
        if (Files.isRegularFile(path)) {
            return path;
        }
        throw new ConfigurationServiceException(
                "The key '{}' is not a file.", key);
    }
    
    @Override
    public String getAuthLogin() throws ConfigurationServiceException {
        String key = "module.authentication.login";
        
        String value = get(key);
        if (!Strings.isNullOrEmpty(value)) {
            return value;
        }
        throw new ConfigurationServiceException(
                "The key '{}' is null or empty.", key);
    }
    
}
