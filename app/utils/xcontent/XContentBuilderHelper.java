package utils.xcontent;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import models.PathIndexModel;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class XContentBuilderHelper implements IXContentBuilderHelper {
    
    private static final Logger LOG = LoggerFactory
            .getLogger(IXContentBuilderHelper.class);
    
    public static final String FILE_FIELD = "file";
    public static final String CONTENT_FIELD = "content";
    public static final String FILENAME_FIELD = "filename";
    public static final String DATE_FIELD = "date";
    public static final String TAGS_FIELD = "tags";
    public static final String METADATA_FIELD = "metadata";
    public static final String CONTENT_TYPE_FIELD = "content-type";
    public static final String CONTENT_LENGTH_FIELD = "content-length";
    public static final String REAL_FILENAME_FIELD = "real-filename";
    
    @Override
    public XContentBuilder buildXContentBuilder(PathIndexModel pathIndexModel, Path targetPath) throws XContentHelperException {
        try {
            XContentBuilder xContentBuilder = jsonBuilder().startObject();
            
            String content = pathIndexModel.getContent();
            String contentType = pathIndexModel.getMetadata().get("Content-Type");
            
            File targetFile = targetPath.toFile();
            
            xContentBuilder
                .startObject(FILE_FIELD)
                    .field(CONTENT_FIELD, content)
                    .field(FILENAME_FIELD, pathIndexModel.getPath().toFile().getName())
                    .field(DATE_FIELD, new Date().toString());
            
            // add tags
            xContentBuilder.startArray(TAGS_FIELD);
            for (String tag: pathIndexModel.getTags()) {
                xContentBuilder.value(tag);
            }
            xContentBuilder.endArray();
            
            xContentBuilder.startObject(METADATA_FIELD)
                    .field(CONTENT_TYPE_FIELD, contentType)
                    .field(CONTENT_LENGTH_FIELD, targetFile.length())
                    .field(REAL_FILENAME_FIELD, targetFile.getName())
                .endObject()
            .endObject();
            
            return xContentBuilder.endObject();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new XContentHelperException(e.getMessage(), e);
        }
    }
    
}
