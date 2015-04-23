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
