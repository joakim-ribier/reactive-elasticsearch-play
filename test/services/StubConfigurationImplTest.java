package services;

import com.google.inject.Singleton;

@Singleton
public class StubConfigurationImplTest implements ConfigurationService {

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
}
