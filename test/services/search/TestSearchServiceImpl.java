package services.search;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.fest.assertions.Assertions.assertThat;
import guice.GuiceTestRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import models.HitModel;
import models.exceptions.ESDocumentNotFound;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import services.ESConstantService;
import services.StubConfigurationImplTest;
import services.configuration.ConfigurationServiceException;
import utils.eslasticsearch.IESServerEmbedded;
import utils.xcontent.XContentBuilderHelper;

import com.google.common.primitives.Ints;
import com.google.inject.Inject;

@RunWith(GuiceTestRunner.class)
public class TestSearchServiceImpl {
    
    private static final long SIZE_DEFAULT = 24356l;
    private static final String FILE_NAME_DEFAULT = "test.pdf";
    
    @Inject
    ESSearchService esSearchService;
    @Inject
    IESServerEmbedded esServerEmbedded;
    @Inject
    ESConstantService esConstantService;
    @Inject
    StubConfigurationImplTest configurationService;

    private String index;
    private String type;
    
    private IndexResponse indexResponse;
    
    @Before
    public void init() throws ElasticsearchException, IOException {
        this.index = configurationService.get(esConstantService.getIndexName());
        this.type = configurationService.get(esConstantService.getTypeName());
        
        this.indexResponse = index(FILE_NAME_DEFAULT, SIZE_DEFAULT, "/home/junit/test.pdf");
    }
    
    @After
    public void after() throws InterruptedException {
        esServerEmbedded.getClient()
            .admin().indices().prepareDelete(index)
            .execute().actionGet();
        
        esServerEmbedded.stop();
    }
    
    private IndexResponse index(String fileName, long size, String path) throws IOException {
        return index(
                buildSource(fileName, new Date(), size, path));
    }
    
    private IndexResponse index(XContentBuilder source) throws IOException {
        return esServerEmbedded.getClient()
                .prepareIndex(index, type)
                .setRefresh(true)
                .setSource(source)
                .execute().actionGet();
    }
    
    private XContentBuilder buildSource(String fileName, Date date, long size,
            String filePath) throws IOException {
        
        return jsonBuilder().startObject()
                .startObject(XContentBuilderHelper.FILE_FIELD)
                    .field(XContentBuilderHelper.CONTENT_FIELD, "Content of the file")
                    .field(XContentBuilderHelper.FILENAME_FIELD, fileName)
                    .field(XContentBuilderHelper.DATE_FIELD, date.toString())
                    .startObject(XContentBuilderHelper.METADATA_FIELD)
                        .field(XContentBuilderHelper.CONTENT_TYPE_FIELD, "application/pdf")
                        .field(XContentBuilderHelper.CONTENT_LENGTH_FIELD, size)
                        .field(XContentBuilderHelper.REAL_FILENAME_FIELD, filePath + "_real")
                    .endObject()
                .endObject();
    }
    
    @Test
    public void testFindByIdWithNotExistsFile() throws ESDocumentNotFound, ConfigurationServiceException {
       String id = indexResponse.getId();
       Optional<Path> file = esSearchService.searchFileById(id);
       
       assertThat(file.isPresent()).isFalse(); 
    }
    
    @Test(expected = ESDocumentNotFound.class)
    public void testFindByIdThrowESDocumentNotFound() throws ESDocumentNotFound, ConfigurationServiceException {
        esSearchService.searchFileById("bad id");
    }
    
    @Test
    public void testSearchByQuery() {
        List<HitModel> searchByQuery = esSearchService.searchByQuery(FILE_NAME_DEFAULT);
        
        assertThat(searchByQuery).hasSize(1);
        assertThat(searchByQuery.get(0).getFileName()).isEqualTo(FILE_NAME_DEFAULT);
        assertThat(searchByQuery.get(0).getSize()).isEqualTo(Ints.checkedCast(SIZE_DEFAULT));
    }
    
}
