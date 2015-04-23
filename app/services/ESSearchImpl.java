package services;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import models.HitModel;
import models.exceptions.ESDocumentFieldNotFound;
import models.exceptions.ESDocumentNotFound;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import services.configuration.ConfigurationService;
import utils.eslasticsearch.IESServerEmbedded;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ESSearchImpl implements ESSearchService {

    private IESServerEmbedded esServerEmbedded;
    
    private final String indexName;
    private final String typeName;

    @Inject
    private ESSearchImpl(ConfigurationService configurationService,
            IESServerEmbedded iesServerEmbedded,
            ESConstantService esConstantService) {

        this.esServerEmbedded = iesServerEmbedded;
        
        this.indexName = configurationService.get(
                esConstantService.getIndexName());
        
        this.typeName = configurationService.get(
                esConstantService.getTypeName());
    }

    @Override
    public List<HitModel> searchByQuery(String value) {
        SearchResponse searchResponse = esServerEmbedded.getClient()
                .prepareSearch(indexName)
                .setTypes(typeName)
                .setQuery(
                        QueryBuilders
                                .multiMatchQuery(value, "_all", "filename"))
                .execute().actionGet();

        List<HitModel> hitModels = Lists.newArrayList();
        searchResponse.getHits().forEach((hit) -> {
            hitModels.add(toHitModel(hit));

        });
        return hitModels;
    }

    @Override
    public Optional<File> findFileById(final String id) throws ESDocumentNotFound, ESDocumentFieldNotFound {
        QueryBuilder query = QueryBuilders.termQuery("_id", id);
        SearchResponse searchResponse = esServerEmbedded.getClient()
                .prepareSearch(indexName)
                .setTypes(typeName).setQuery(query).execute().actionGet();

        if (searchResponse.getHits().getTotalHits() == 0) {
            throw new ESDocumentNotFound("The document '" + id + "' not found.");
        }
        return getFileFromFirstHits(searchResponse.getHits());
    }

    @SuppressWarnings("unchecked")
    private Optional<File> getFileFromFirstHits(final SearchHits hits) throws ESDocumentFieldNotFound  {
        final SearchHit hit = hits.getAt(0);
        Map<String, Object> source = hit.getSource();
        Map<String, Object> path = (Map<String, Object>) source.get("");

        String pathname = (String) path.get("");
        if (Strings.isNullOrEmpty(pathname)) {
            throw new ESDocumentFieldNotFound(
                    "The file of document '" + hit.getId() + "' is not found.");
            
        }
        return getFileOrOptional(pathname);
    }
    
    private Optional<File> getFileOrOptional(String pathname) {
        File file = new File(pathname);
        if (file.exists()) {
            return Optional.of(file);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private HitModel toHitModel(final SearchHit hit) {
        Map<String, Object> source = hit.getSource();
        Map<String, Object> file = (Map<String, Object>) source.get("file");
        Map<String, Object> metadata = (Map<String, Object>) file.get("metadata");

        return new HitModel(hit.getId(), (String) file.get("filename"),
                (String) file.get("date"),
                (Integer) metadata.get("content-length"));
    }
}
