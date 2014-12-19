package services;

public class ESConstantImplTest implements ESConstantService {

    @Override
    public String getClusterName() {
        return ESConstantImpl.ES_CLUSTER_NAME;
    }

    @Override
    public String getIndexName() {
        return ESConstantImpl.ES_INDEX_NAME;
    }

    @Override
    public String getTypeName() {
        return ESConstantImpl.ES_TYPE_NAME;
    }

    @Override
    public String getPathData() {
        // Set the path if you want to use a specific directory for your tests
        return ESConstantImpl.ES_PATH_DATA;
    }

    @Override
    public String getRootFileField() {
        return ESConstantImpl.FILE_FIELD;
    }

    @Override
    public String getFileNameField() {
        return ESConstantImpl.FILENAME_FIELD;
    }

    @Override
    public String getIndexingDateField() {
        return ESConstantImpl.INDEXING_DATE_FIELD;
    }

    @Override
    public String getSizeField() {
        return ESConstantImpl.FILESIZE_FIELD;
    }

    @Override
    public String getPathField() {
        return ESConstantImpl.PATH_FIELD;
    }

    @Override
    public String getRealField() {
        return ESConstantImpl.REAL_FIELD;
    }
}
