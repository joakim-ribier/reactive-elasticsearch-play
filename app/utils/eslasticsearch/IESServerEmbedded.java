package utils.eslasticsearch;

import org.elasticsearch.client.Client;

public interface IESServerEmbedded {

    void stop();

    Client getClient();
}
