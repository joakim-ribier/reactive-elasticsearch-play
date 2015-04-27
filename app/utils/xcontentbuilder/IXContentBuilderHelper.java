package utils.xcontentbuilder;

import java.nio.file.Path;

import models.PathIndexModel;

import org.elasticsearch.common.xcontent.XContentBuilder;


public interface IXContentBuilderHelper {

    XContentBuilder buildXContentBuilder(PathIndexModel pathIndexModel, Path targetPath)
            throws XContentHelperException;
    
}
