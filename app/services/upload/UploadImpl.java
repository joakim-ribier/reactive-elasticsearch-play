package services.upload;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import utils.file.FileUtilsException;
import utils.file.IFileUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UploadImpl implements UploadService {
    
    private final ConfigurationService configurationService;
    
    private final IFileUtils iFileUtils;
    
    @Inject
    private UploadImpl(ConfigurationService configurationService, IFileUtils iFileUtils) {
        this.configurationService = configurationService;
        this.iFileUtils = iFileUtils;
    }
    
    @Override
    public boolean upload(File file, String filename)
            throws FileUtilsException, ConfigurationServiceException {
        
        Path path = Paths.get(file.getAbsolutePath());
        Path target = iFileUtils.move(
                path,
                Paths.get(configurationService.getPathDirToIndexFiles().toFile().getAbsolutePath(), filename));
        
        return Files.isRegularFile(target);
    }
    
}
