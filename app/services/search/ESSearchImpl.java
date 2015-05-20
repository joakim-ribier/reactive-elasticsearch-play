package services.search;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import models.HitModel;
import models.HitModel.Builder;
import models.exceptions.ESDocumentNotFound;
import models.search.PathModel;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import services.ESConstantService;
import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import utils.eslasticsearch.IESServerEmbedded;
import utils.xcontent.IXContentHelper;
import utils.xcontent.XContentBuilderHelper;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ESSearchImpl implements ESSearchService {
    
    private final IESServerEmbedded esServerEmbedded;
    private final ConfigurationService configurationService;
    
    private final String indexName;
    private final String typeName;
    private final IXContentHelper xContentHelper;
    
    @Inject
    private ESSearchImpl(ConfigurationService configurationService,
            IESServerEmbedded iesServerEmbedded, ESConstantService esConstantService,
            IXContentHelper xContentHelper) {
        
        this.configurationService = configurationService;
        this.esServerEmbedded = iesServerEmbedded;
        this.xContentHelper = xContentHelper;
        
        this.indexName = configurationService.get(esConstantService.getIndexName());
        this.typeName = configurationService.get(esConstantService.getTypeName());
    }
    
    @Override
    public List<HitModel> searchByQuery(String value) {
        SearchResponse searchResponse = esServerEmbedded.getClient()
                .prepareSearch(indexName)
                .setTypes(typeName)
                .setQuery(
                        QueryBuilders.multiMatchQuery(
                                value,
                                XContentBuilderHelper.CONTENT_FIELD,
                                XContentBuilderHelper.FILENAME_FIELD))
                .execute().actionGet();
        
        List<HitModel> hitModels = Lists.newArrayList();
        searchResponse.getHits().forEach((hit) -> {
            hitModels.add(toHitModel(hit));
        });
        return hitModels;
    }
    
    @Override
    public PathModel searchFileById(final String id)
            throws ESDocumentNotFound, ConfigurationServiceException {
        
        QueryBuilder query = QueryBuilders.termQuery("_id", id);
        SearchResponse searchResponse = esServerEmbedded.getClient()
                .prepareSearch(indexName)
                .setTypes(typeName).setQuery(query).execute().actionGet();
        
        if (searchResponse.getHits().getTotalHits() == 0) {
            throw new ESDocumentNotFound("The document '" + id + "' not found.");
        }
        
        Optional<String> filename = xContentHelper.findValueToString(
                searchResponse.getHits().getAt(0).getSource(),
                XContentBuilderHelper.FILENAME_FIELD);
        
        Optional<Path> path = findFileSearchHits(searchResponse.getHits());
        
        return new PathModel(filename, path);
    }
    
    private Optional<Path> findFileSearchHits(final SearchHits hits)
            throws ConfigurationServiceException  {
        
        final SearchHit hit = hits.getAt(0);
        Optional<String> value = xContentHelper.findValueToString(
                hit.getSource(), XContentBuilderHelper.REAL_FILENAME_FIELD);
        
        if (!value.isPresent()) {
            return Optional.empty();
        }
        
        Path pathAppDataDir = configurationService.getPathAppDataDir();
        Path path = Paths.get(pathAppDataDir.toFile().getAbsolutePath(), value.get());
        if (Files.isRegularFile(path)) {
            return Optional.of(path);
        }
        
        return Optional.empty();
    }
    
    private HitModel toHitModel(final SearchHit hit) {
        Map<String, Object> source = hit.getSource();
        
        Builder builder = new HitModel.Builder();
        builder.withId(hit.getId());
        builder.withFileName(xContentHelper.findValueToString(
                source, XContentBuilderHelper.FILENAME_FIELD));
        
        builder.withDate(xContentHelper.findValueToString(
                source, XContentBuilderHelper.DATE_FIELD));
        
        builder.withSize(xContentHelper.findValueToLong(
                source, XContentBuilderHelper.CONTENT_LENGTH_FIELD));
        
        return builder.build();
    }
    
}
