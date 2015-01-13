package services;

import play.Play;

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
        String tmpDir = getTmpDir();
        String zipName = get("file.download.documents.zip.name");
        return tmpDir + "/" + zipName + ".zip";
    }
}
