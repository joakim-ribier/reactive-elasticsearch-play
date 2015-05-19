package services;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import models.search.PathModel;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.configuration.ConfigurationService;
import utils.file.FileUtilsException;
import utils.file.IFileUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ZipImpl implements ZipService {

    private static final Logger LOG = LoggerFactory.getLogger(ZipService.class);
    
    private final ConfigurationService configurationService;
    
    private final IFileUtils fileUtils;
    
    private final ZipParameters zipParameters;
    
    @Inject
    private ZipImpl(ConfigurationService configurationService, IFileUtils iFileUtils) {
        this.configurationService = configurationService;
        this.fileUtils = iFileUtils;
        
        this.zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        zipParameters.setEncryptFiles(false);
    }
    
    @Override
    public boolean create(List<PathModel> pathModels) {
        String fileZipPathName = configurationService.getTmpZipFilePathName();
        return create(fileZipPathName, pathModels);
    }
    
    @Override
    public boolean create(String fileZipPathName, List<PathModel> pathModels) {
        try {
            deleteFileIfExists(fileZipPathName);
            
            ZipFile zipFile = new ZipFile(fileZipPathName);
            pathModels.forEach(pathModel -> addFileToZip(zipFile, pathModel));
            if (!zipFile.isValidZipFile()) {
                LOG.error("An error occurred while the creation of zip file.");
                return false;
            }
            return true;
        } catch (ZipException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
    
    private void addFileToZip(ZipFile zipFile, PathModel pathModel) {
        try {
            Optional<Path> path = pathModel.getPath();
            if (!path.isPresent()) {
                return;
            }
            
            Optional<String> filename = pathModel.getFilename();
            File file = path.get().toFile();
            if (filename.isPresent()) {
                try {
                    Path copy = fileUtils.copy(path.get(), filename.get());
                    zipFile.addFile(copy.toFile(), zipParameters);
                } catch (FileUtilsException e) {
                    zipFile.addFile(file, zipParameters);
                }
            } else {
                zipFile.addFile(file, zipParameters);
            }
        } catch (ZipException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    
    private void deleteFileIfExists(String filePathName) {
        File file = new File(filePathName);
        if (file.isFile()) {
            file.delete();
        }
    }
}
