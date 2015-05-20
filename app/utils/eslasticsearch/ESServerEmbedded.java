package utils.eslasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.ESConstantService;
import services.configuration.ConfigurationService;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ESServerEmbedded implements IESServerEmbedded {
    
    private static final Logger LOG = LoggerFactory.getLogger(IESServerEmbedded.class);
    
    private final ConfigurationService configurationService;
    private final ESConstantService esConstantService;
    
    private final String clusterName;
    private final String dataPath;
    private final String index;
    private final String type;
    
    private Client client = null;
    private Node node;
    
    @Inject
    private ESServerEmbedded(ConfigurationService configurationService,
            ESConstantService esConstantService) {
        
        this.configurationService = configurationService;
        this.esConstantService = esConstantService;
        
        this.clusterName = getCluster();
        this.dataPath = getDataPath();
        this.index = getIndex();
        this.type = getType();
    }
    
    private void init() {
        LOG.info("Server embedded starting...");
        LOG.info("Cluster name [{}].", clusterName);
        LOG.info("Data path [{}].", dataPath);
        
        Builder settingsBuilder = ImmutableSettings.settingsBuilder();
        if (!Strings.isNullOrEmpty(dataPath)) {
            settingsBuilder.put("path.data", dataPath);
        }
        
        this.node = nodeBuilder().clusterName(clusterName).client(false)
                .settings(settingsBuilder.build()).node();
        
        this.client = node.client();
    }
    
    @Override
    public IndexRequestBuilder buildIndexRequestBuilder(XContentBuilder xContentBuilder) {
        IndexRequestBuilder indexRequestBuilder = client.prepareIndex(index, type);
        return indexRequestBuilder.setSource(xContentBuilder);
    }
    
    @Override
    public boolean bulk(List<IndexRequestBuilder> indexRequestBuilders) {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for (IndexRequestBuilder indexRequestBuilder: indexRequestBuilders) {
            bulkRequestBuilder.add(indexRequestBuilder);
        }
        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        return !bulkResponse.hasFailures();
    }
    
    @Override
    public void stop() {
        if (node != null) {
            LOG.info("Server embedded stopping...");
            node.close();
            this.client = null;
        }
    }
    
    @Override
    public Client getClient() {
        if (client == null) {
            init();
        }
        return client;
    }
    
    private String getType() {
        return configurationService.get(
                esConstantService.getTypeName());
    }
    
    private String getIndex() {
        return configurationService.get(
                esConstantService.getIndexName());
    }
    
    private String getDataPath() {
        return configurationService.get(
                esConstantService.getPathData());
    }
    
    private String getCluster() {
        return configurationService.get(
                esConstantService.getClusterName());
    }
    
}
