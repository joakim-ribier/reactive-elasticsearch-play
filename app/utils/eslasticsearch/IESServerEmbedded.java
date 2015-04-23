package utils.eslasticsearch;

import java.util.List;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

public interface IESServerEmbedded {

    void stop();

    Client getClient();

    boolean bulk(List<IndexRequestBuilder> indexRequestBuilders);

    IndexRequestBuilder buildIndexRequestBuilder(XContentBuilder xContentBuilder);
}
