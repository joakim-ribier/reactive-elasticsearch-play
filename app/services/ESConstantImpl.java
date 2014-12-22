package services;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Singleton;

@Singleton
public class ESConstantImpl implements ESConstantService {

    @VisibleForTesting
    public static final String ES_CLUSTER_NAME = "elasticsearch.cluster.name";
    @VisibleForTesting
    public static final String ES_INDEX_NAME = "elasticsearch.index.name";
    @VisibleForTesting
    public static final String ES_TYPE_NAME = "elasticsearch.type.name";
    @VisibleForTesting
    public static final String ES_PATH_DATA = "elasticsearch.path.data";

    private static final String FILE_FIELD = "file";
    private static final String FILENAME_FIELD = "filename";
    private static final String INDEXING_DATE_FIELD = "indexing_date";
    private static final String FILESIZE_FIELD = "filesize";
    private static final String PATH_FIELD = "path";
    private static final String REAL_FIELD = "real";

    @Override
    public String getRootFileField() {
        return FILE_FIELD;
    }

    @Override
    public String getFileNameField() {
        return FILENAME_FIELD;
    }

    @Override
    public String getIndexingDateField() {
        return INDEXING_DATE_FIELD;
    }

    @Override
    public String getSizeField() {
        return FILESIZE_FIELD;
    }

    @Override
    public String getPathField() {
        return PATH_FIELD;
    }

    @Override
    public String getRealField() {
        return REAL_FIELD;
    }

    @Override
    public String getClusterName() {
        return ES_CLUSTER_NAME;
    }

    @Override
    public String getIndexName() {
        return ES_INDEX_NAME;
    }

    @Override
    public String getTypeName() {
        return ES_TYPE_NAME;
    }

    @Override
    public String getPathData() {
        return ES_PATH_DATA;
    }
}
