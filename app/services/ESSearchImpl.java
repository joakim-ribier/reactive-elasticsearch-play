package services;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import models.HitModel;
import models.exceptions.ESDocumentNotFound;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import utils.eslasticsearch.IESServerEmbedded;

import com.beust.jcommander.internal.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ESSearchImpl implements ESSearchService {

    private final Client client;
    private final String indexName;
    private final String typeName;
    
    private final String fileField;
    private final String fileNameField;
    private final String indexingDateField;
    private final String sizeField;
    private final String pathField;
    private final String realField;

    @Inject
    private ESSearchImpl(ConfigurationService configurationService, 
            IESServerEmbedded iesServerEmbedded, ESConstantService esConstantService) {
        
        this.client = iesServerEmbedded.getClient();
        this.indexName = configurationService.get(esConstantService.getIndexName());
        this.typeName = configurationService.get(esConstantService.getTypeName());
        
        this.fileField = esConstantService.getRootFileField();
        this.fileNameField = esConstantService.getFileNameField();
        this.indexingDateField = esConstantService.getIndexingDateField();
        this.sizeField = esConstantService.getSizeField();
        this.pathField = esConstantService.getPathField();
        this.realField = esConstantService.getRealField();
    }

    @Override
    public List<HitModel> searchByQuery(String value) {
        SearchResponse searchResponse = client.prepareSearch(indexName)
                .setTypes(typeName)
                .setQuery(
                        QueryBuilders.multiMatchQuery(value, "_all", "filename"))
                .execute().actionGet();

        List<HitModel> hitModels = Lists.newArrayList();
        searchResponse.getHits().forEach((hit) -> {
            hitModels.add(toHitModel(hit));

        });
        return hitModels;
    }

    @Override
    public Optional<File> findFileById(final String id) throws ESDocumentNotFound {
        QueryBuilder query = QueryBuilders.termQuery("_id", id);
        SearchResponse searchResponse = client
                .prepareSearch(indexName)
                .setTypes(typeName)
                .setQuery(query)
                .execute().actionGet();
        
        if (searchResponse.getHits().getTotalHits() == 0) {
            throw new ESDocumentNotFound("The document '" + id + "' not found.");
        }
        return getFileFromFirstHits(searchResponse.getHits());
    }    
    
    @SuppressWarnings("unchecked")
    private Optional<File> getFileFromFirstHits(final SearchHits hits) {
        final SearchHit hit = hits.getAt(0);
        Map<String, Object> source = hit.getSource();
        Map<String, Object> path = (Map<String, Object>) source.get(pathField);
        
        File file = new File((String) path.get(realField));
        if(file.exists()) {
            return Optional.of(file);
        }        
        return Optional.empty();
    }
    
    
    @SuppressWarnings("unchecked")
    private HitModel toHitModel(final SearchHit hit) {
        Map<String, Object> source = hit.getSource();
        Map<String, Object> file = (Map<String, Object>) source.get(fileField);

        return new HitModel(
                hit.getId(),(String) file.get(fileNameField),
                (String) file.get(indexingDateField), (Integer) file.get(sizeField));
    }
}
