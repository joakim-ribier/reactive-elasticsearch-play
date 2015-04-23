package utils.xcontentbuilder;

import models.PathIndexModel;

import org.elasticsearch.common.xcontent.XContentBuilder;


public interface IXContentBuilderHelper {

    XContentBuilder buildXContentBuilder(PathIndexModel pathIndexModel)
            throws XContentHelperException;
    
}
