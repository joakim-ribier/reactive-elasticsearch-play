package services;

public interface ConfigurationService {

    String get(String key);

    String getHostName();

    String getTmpDir();

    String getTmpZipFilePathName();

}
