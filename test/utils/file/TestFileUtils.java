package utils.file;

import static org.fest.assertions.Assertions.assertThat;

import java.nio.file.Paths;

import models.PathIndexModel;

import org.junit.Before;
import org.junit.Test;

public class TestFileUtils {
    
    private static final String RESOURCES_DIR = "app/assets/test";
    
    private IFileUtils fileUtils;
    
    @Before
    public void before() {
        this.fileUtils = new FileUtils();
    }
    
    @Test
    public void testGetContent() throws FileUtilsException {
        String expected = fileUtils.getContent(
                Paths.get(".", RESOURCES_DIR, "README.textile"));
        
        assertThat(expected).isNotNull().isNotEmpty();
        assertThat(expected).contains(
                "Elasticsearch is a distributed RESTful search engine built for the cloud.");
    }
    
    @Test
    public void testParse() throws FileUtilsException {
        PathIndexModel expected = fileUtils.parse(
                Paths.get(".", RESOURCES_DIR, "README.textile"));
        
        assertThat(expected).isNotNull();
        assertThat(expected.getContent()).contains(
                "Elasticsearch is a distributed RESTful search engine built for the cloud.");
    }
    
}
