package utils.eslasticsearch;

import org.elasticsearch.client.Client;


public interface IESServerEmbedded {

    void init();

    void stop();

    Client getClient();
}
