package services;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.fest.assertions.Assertions.assertThat;
import guice.GuiceTestRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import models.HitModel;
import models.exceptions.ESDocumentFieldNotFound;
import models.exceptions.ESDocumentNotFound;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import utils.eslasticsearch.IESServerEmbedded;

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
                .startObject("file")
                    .field("content", "Content of the file")
                    .field("filename", fileName)
                    .field("date", date.toString())
                    .startObject("metadata")
                        .field("content-type", "application/pdf")
                        .field("content-length", size)
                    .endObject()
                .endObject();
    }
    
    @Ignore
    @Test
    public void testFindByIdWithNotExistsFile() throws ESDocumentNotFound, ESDocumentFieldNotFound {
       String id = indexResponse.getId();
       Optional<File> file = esSearchService.findFileById(id);
       
       assertThat(file.isPresent()).isFalse(); 
    }
    
    @Test(expected = ESDocumentNotFound.class)
    public void testFindByIdThrowESDocumentNotFound() throws ESDocumentNotFound, ESDocumentFieldNotFound {
        esSearchService.findFileById("bad id");
    }
    
    @Ignore
    @Test(expected = ESDocumentFieldNotFound.class)
    public void testFindByIdThrowESDocumentFieldNotFound()
            throws ESDocumentNotFound, ESDocumentFieldNotFound, IOException {
        
        XContentBuilder source = jsonBuilder().startObject()
            .startObject("file")
                .field("filename", "test.png")
            .endObject();
        
        IndexResponse index = index(source);
        esSearchService.findFileById(index.getId());
    }
    
    @Test
    public void testSearchByQuery() {
        List<HitModel> searchByQuery = esSearchService.searchByQuery(FILE_NAME_DEFAULT);
        
        assertThat(searchByQuery).hasSize(1);
        assertThat(searchByQuery.get(0).getFileName()).isEqualTo(FILE_NAME_DEFAULT);
        assertThat(searchByQuery.get(0).getSize()
                ).isEqualTo(Ints.checkedCast(SIZE_DEFAULT));
    }
    
}
