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
    
    @Override
    public XContentBuilder buildXContentBuilder(PathIndexModel pathIndexModel) throws XContentHelperException {
        try {
            XContentBuilder xContentBuilder = jsonBuilder().startObject();
            
            String content = pathIndexModel.getContent();
            String contentType = pathIndexModel.getMetadata().get("Content-Type");
            
            File file = pathIndexModel.getPath().toFile();
            xContentBuilder
                .startObject("file")
                    .field("content", content)
                    .field("filename", file.getName())
                    .field("date", new Date().toString())
                    .startObject("metadata")
                        .field("content-type", contentType)
                        .field("content-length", file.length())
                    .endObject()
                .endObject();
            
            return xContentBuilder.endObject();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new XContentHelperException(e.getMessage(), e);
        }
    }
    
}
