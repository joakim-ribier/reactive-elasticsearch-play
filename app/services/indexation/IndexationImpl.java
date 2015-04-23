package services.indexation;

import java.nio.file.Path;
import java.util.List;

import models.PathIndexModel;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.eslasticsearch.IESServerEmbedded;
import utils.file.FileUtilsException;
import utils.file.IFileUtils;
import utils.xcontentbuilder.IXContentBuilderHelper;
import utils.xcontentbuilder.XContentHelperException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IndexationImpl implements IndexationService {
    
    private static final Logger LOG = LoggerFactory
            .getLogger(IndexationService.class);
    
    private final IFileUtils iFileUtils;
    private final IXContentBuilderHelper ixContentBuilderHelper;
    private final IESServerEmbedded iesServerEmbedded;
    
    @Inject
    protected IndexationImpl(IFileUtils iFileUtils, IXContentBuilderHelper ixContentBuilderHelper,
            IESServerEmbedded iesServerEmbedded) {
        
        this.iFileUtils = iFileUtils;
        this.ixContentBuilderHelper = ixContentBuilderHelper;
        this.iesServerEmbedded = iesServerEmbedded;
    }
    
    @Override
    public void indexAllFilesInADirectory(Path path) throws IndexationServiceException {
        Preconditions.checkNotNull(path, "The 'path' parameter is required.");
        try {
            List<Path> files = iFileUtils.findRecursivelyAllFilesInADirectory(path);
            for (Path item: files) {
                try {
                    PathIndexModel pathIndexModel = iFileUtils.parse(item);
                    XContentBuilder xContentBuilder =
                            ixContentBuilderHelper.buildXContentBuilder(pathIndexModel);
                    
                    IndexRequestBuilder buildIndexRequestBuilder = iesServerEmbedded.buildIndexRequestBuilder(xContentBuilder);
                    iesServerEmbedded.bulk(Lists.newArrayList(buildIndexRequestBuilder));
                    
                } catch (FileUtilsException | XContentHelperException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        } catch (FileUtilsException e) {
            throw new IndexationServiceException(e.getMessage(), e);
        }
    }
    
}