package services.configuration;

import java.nio.file.Path;

public interface ConfigurationService {

    String get(String key);

    String getHostName();

    String getTmpDir();

    String getTmpZipFilePathName();

    /**
     * Gets the file directory to index files.
     * 
     * @return file java.io object.
     * @throws ConfigurationServiceException
     */
    Path getPathDirToIndexFiles() throws ConfigurationServiceException;

    Path getAuthPasswordPath() throws ConfigurationServiceException;

    String getAuthLogin() throws ConfigurationServiceException;

    String getAlgorithm();

    Path getPathAppDataDir() throws ConfigurationServiceException;

    String getI18nDirectory();
    
}
