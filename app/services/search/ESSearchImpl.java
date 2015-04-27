package services.search;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import models.HitModel;
import models.exceptions.ESDocumentNotFound;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import services.ESConstantService;
import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import utils.eslasticsearch.IESServerEmbedded;
import utils.xcontentbuilder.XContentBuilderHelper;

import com.beust.jcommander.internal.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ESSearchImpl implements ESSearchService {
    
    private final IESServerEmbedded esServerEmbedded;
    private final ConfigurationService configurationService;
    
    private final String indexName;
    private final String typeName;
    
    @Inject
    private ESSearchImpl(ConfigurationService configurationService,
            IESServerEmbedded iesServerEmbedded, ESConstantService esConstantService) {
        
        this.configurationService = configurationService;
        this.esServerEmbedded = iesServerEmbedded;
        this.indexName = configurationService.get(esConstantService.getIndexName());
        this.typeName = configurationService.get(esConstantService.getTypeName());
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
    public Optional<Path> findFileById(final String id) throws ESDocumentNotFound, ConfigurationServiceException {
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
    private Optional<Path> getFileFromFirstHits(final SearchHits hits) throws ConfigurationServiceException  {
        final SearchHit hit = hits.getAt(0);
        Map<String, Object> source = hit.getSource();
        Map<String, Object> file = (Map<String, Object>) source.get(XContentBuilderHelper.FILE_FIELD);
        Map<String, Object> metadata = (Map<String, Object>) file.get(XContentBuilderHelper.METADATA_FIELD);
        String filename = (String) metadata.get(XContentBuilderHelper.REAL_FILENAME_FIELD);
        
        Path pathAppDataDir = configurationService.getPathAppDataDir();
        Path path = Paths.get(pathAppDataDir.toFile().getAbsolutePath(), filename);
        if (Files.isRegularFile(path)) {
            return Optional.of(path);
        }
        return Optional.empty();
    }
    
    @SuppressWarnings("unchecked")
    private HitModel toHitModel(final SearchHit hit) {
        Map<String, Object> source = hit.getSource();
        Map<String, Object> file = (Map<String, Object>) source.get(XContentBuilderHelper.FILE_FIELD);
        Map<String, Object> metadata = (Map<String, Object>) file.get(XContentBuilderHelper.METADATA_FIELD);

        return new HitModel(hit.getId(), (String) file.get(XContentBuilderHelper.FILENAME_FIELD),
                (String) file.get(XContentBuilderHelper.DATE_FIELD),
                (Integer) metadata.get(XContentBuilderHelper.CONTENT_LENGTH_FIELD));
    }
}
