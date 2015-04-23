package utils.eslasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
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

    private static final Logger LOG = LoggerFactory
            .getLogger(IESServerEmbedded.class);

    private final String clusterName;
    private final String dataPath;

    private Client client = null;
    private Node node;

    @Inject
    private ESServerEmbedded(ConfigurationService configurationService,
            ESConstantService elasticsearchConstantService) {

        this.clusterName = configurationService.get(
                elasticsearchConstantService.getClusterName());
        
        this.dataPath = configurationService.get(
                elasticsearchConstantService.getPathData());
    }

    private void init() {
        LOG.info("Elasticsearch server embedded started.");
        LOG.info("Cluster name [{}] for embedded [ES].", clusterName);
        LOG.info("Data path [{}] for embedded [ES].", dataPath);

        Builder settingsBuilder = ImmutableSettings.settingsBuilder();
        if (!Strings.isNullOrEmpty(dataPath)) {
            settingsBuilder.put("path.data", dataPath);
        }
        settingsBuilder.put("number_of_shards", 1);
        settingsBuilder.put("index.numberOfReplicas", 1);

        this.node = nodeBuilder().clusterName(clusterName).client(false)
                .settings(settingsBuilder.build()).node();

        this.client = node.client();
    }

    @Override
    public void stop() {
        LOG.info("Elasticsearch server embedded stoped.");
        if (node != null) {
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
}
