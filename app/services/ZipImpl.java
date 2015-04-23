package services;

import java.io.File;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.configuration.ConfigurationService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ZipImpl implements ZipService {

    private static final Logger LOG = LoggerFactory.getLogger(ZipService.class);
    
    private final ConfigurationService configurationService;
    
    @Inject
    private ZipImpl(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
    
    @Override
    public boolean create(List<File> files) {
        String fileZipPathName = configurationService.getTmpZipFilePathName();
        return create(fileZipPathName, files);
    }
    
    @Override
    public boolean create(String fileZipPathName, List<File> files) {
        try {
            deleteFileIfExists(fileZipPathName);
            
            ZipFile zipFile = new ZipFile(fileZipPathName);
            ZipParameters zipParameters = buildParameters();
            
            files.forEach(file -> {
                try {
                    zipFile.addFile(file, zipParameters);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            });
            
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

    private ZipParameters buildParameters() {
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles(false);
        return parameters;
    }

    private void deleteFileIfExists(String filePathName) {
        File file = new File(filePathName);
        if (file.isFile()) {
            file.delete();
        }
    }
}
