package utils.xcontentbuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.File;
import java.io.IOException;
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
    public static final String METADATA_FIELD = "metadata";
    public static final String CONTENT_TYPE_FIELD = "content-type";
    public static final String CONTENT_LENGTH_FIELD = "content-length";
    
    @Override
    public XContentBuilder buildXContentBuilder(PathIndexModel pathIndexModel) throws XContentHelperException {
        try {
            XContentBuilder xContentBuilder = jsonBuilder().startObject();
            
            String content = pathIndexModel.getContent();
            String contentType = pathIndexModel.getMetadata().get("Content-Type");
            
            File file = pathIndexModel.getPath().toFile();
            xContentBuilder
                .startObject(FILE_FIELD)
                    .field(CONTENT_FIELD, content)
                    .field(FILENAME_FIELD, file.getName())
                    .field(DATE_FIELD, new Date().toString())
                    .startObject(METADATA_FIELD)
                        .field(CONTENT_TYPE_FIELD, contentType)
                        .field(CONTENT_LENGTH_FIELD, file.length())
                    .endObject()
                .endObject();
            
            return xContentBuilder.endObject();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new XContentHelperException(e.getMessage(), e);
        }
    }
    
}
