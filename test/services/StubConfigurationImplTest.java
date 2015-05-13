package services;

import java.nio.file.Path;
import java.nio.file.Paths;

import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;

import com.google.inject.Singleton;

@Singleton
public class StubConfigurationImplTest implements ConfigurationService {
    
    private String algorithm = "SHA-1";

    @Override
    public String get(String key) {
        switch (key) {
        case "application.security.login":
            return "admin";
        case "application.security.file.pwd":
            return "test.pwd";
        case ESConstantImpl.ES_PATH_DATA:
            return "";
        case ESConstantImpl.ES_CLUSTER_NAME:
            return "junit-cluster";
        case ESConstantImpl.ES_INDEX_NAME:
            return "junit-index";
        case ESConstantImpl.ES_TYPE_NAME:
            return "junit-type";
        default:
            return key;
        }
    }
    
    @Override
    public String getHostName() {
        return null;
    }
    
    @Override
    public String getTmpDir() {
        return null;
    }
    
    @Override
    public String getTmpZipFilePathName() {
        return null;
    }
    
    @Override
    public Path getPathDirToIndexFiles() throws ConfigurationServiceException {
        return Paths.get(".", "app", "assets/test");
    }
    
    @Override
    public Path getAuthPasswordPath() throws ConfigurationServiceException {
        return null;
    }
    
    @Override
    public String getAuthLogin() throws ConfigurationServiceException {
        return "admin";
    }
    
    @Override
    public String getAlgorithm() {
        return algorithm;
    }
    
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public Path getPathAppDataDir() throws ConfigurationServiceException {
        return Paths.get(".", "conf", "test");
    }
    
}
