package utils;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import models.PathIndexModel;

import org.apache.tika.metadata.Metadata;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentParser;
import org.junit.Test;

import utils.xcontentbuilder.XContentBuilderHelper;
import utils.xcontentbuilder.XContentHelperException;

public class TestXContentBuilderHelper {
    
    @Test
    @SuppressWarnings("unchecked")
    public void testBuildXContentBuilder() throws XContentHelperException, IOException {
        PathIndexModel pathIndexModel = mock(PathIndexModel.class);
        Metadata metadata = mock(Metadata.class);
        
        Path path = mock(Path.class);
        File file = mock(File.class);
        when(path.toFile()).thenReturn(file);
        
        Path targetPath = mock(Path.class);
        File targetFile = mock(File.class);
        when(targetPath.toFile()).thenReturn(targetFile);
        when(targetFile.getName()).thenReturn("1234567.pdf");
        when(targetFile.length()).thenReturn(12l);
        
        when(pathIndexModel.getContent()).thenReturn("The String content.");
        when(pathIndexModel.getMetadata()).thenReturn(metadata);
        when(pathIndexModel.getPath()).thenReturn(path);
        
        when(file.getName()).thenReturn("The Name of the file.");
        
        when(metadata.get("Content-Type")).thenReturn("pdf./application");
        
        XContentBuilderHelper xContentBuilderHelper = new XContentBuilderHelper();
        XContentBuilder expected = xContentBuilderHelper.buildXContentBuilder(pathIndexModel, targetPath);
        
        XContentParser parser = XContentHelper.createParser(expected.bytes());
        Map<String, Object> mapOrdered = parser.mapOrdered();
        
        Map<String, Object> mapFile = (Map<String, Object>) mapOrdered.get(XContentBuilderHelper.FILE_FIELD);
        Map<String, Object> mapMetadata = (Map<String, Object>) mapFile.get(XContentBuilderHelper.METADATA_FIELD);
        
        assertThat(expected).isNotNull();
        
        assertThat(mapFile.get(XContentBuilderHelper.CONTENT_FIELD)).isEqualTo("The String content.");
        assertThat(mapFile.get(XContentBuilderHelper.FILENAME_FIELD)).isEqualTo("The Name of the file.");
        assertThat(mapFile.get(XContentBuilderHelper.DATE_FIELD)).isNotNull();
        
        assertThat(mapMetadata.get(XContentBuilderHelper.CONTENT_TYPE_FIELD)).isEqualTo("pdf./application");
        assertThat(mapMetadata.get(XContentBuilderHelper.CONTENT_LENGTH_FIELD)).isEqualTo(12);
        assertThat(mapMetadata.get(XContentBuilderHelper.REAL_FILENAME_FIELD)).isEqualTo("1234567.pdf");
    }
    
}
