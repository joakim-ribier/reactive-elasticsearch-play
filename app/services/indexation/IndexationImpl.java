package services.indexation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import models.PathIndexModel;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import utils.eslasticsearch.IESServerEmbedded;
import utils.file.FileUtilsException;
import utils.file.IFileUtils;
import utils.xcontent.IXContentBuilderHelper;
import utils.xcontent.XContentHelperException;

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
    private final ConfigurationService configurationService;
    
    @Inject
    protected IndexationImpl(IFileUtils iFileUtils, IXContentBuilderHelper ixContentBuilderHelper,
            IESServerEmbedded iesServerEmbedded, ConfigurationService configurationService) {
        
        this.iFileUtils = iFileUtils;
        this.ixContentBuilderHelper = ixContentBuilderHelper;
        this.iesServerEmbedded = iesServerEmbedded;
        this.configurationService = configurationService;
    }
    
    @Override
    public void indexingAllFilesInADirectory(Path path) 
            throws IndexationServiceException, ConfigurationServiceException {
        
        Preconditions.checkNotNull(path, "The 'path' parameter is required.");
        try {
            List<Path> files = iFileUtils.findRecursivelyAllFilesInADirectory(path);
            for (Path file: files) {
                try {
                    indexing(file, null);
                } catch (Exception e) {
                    LOG.error("The file {} cannot can not be indexed.",
                            file.toFile().getName());
                    // next ->
                }
            }
        } catch (FileUtilsException e) {
            throw new IndexationServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    public void indexing(Path target, String tags)
            throws IndexationServiceException, ConfigurationServiceException {
        
        Preconditions.checkNotNull(target, "The 'path' parameter is required.");
        Preconditions.checkArgument(Files.isRegularFile(target), "The 'path' parameter is not a regular file.");
        try {
            PathIndexModel pathIndexModel = iFileUtils.parse(target);
            pathIndexModel.setTags(tags);
            
            Path targetPath = iFileUtils.move(
                    pathIndexModel, configurationService.getPathAppDataDir());
            
            XContentBuilder xContentBuilder =
                    ixContentBuilderHelper.buildXContentBuilder(pathIndexModel, targetPath);
            
            IndexRequestBuilder buildIndexRequestBuilder = iesServerEmbedded.buildIndexRequestBuilder(xContentBuilder);
            iesServerEmbedded.bulk(Lists.newArrayList(buildIndexRequestBuilder));
        } catch (XContentHelperException | FileUtilsException e) {
            throw new IndexationServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    public int getNumberOfFilesWaitingToBeIndexed()
            throws ConfigurationServiceException, IndexationServiceException {
        
        try {
            Path path = configurationService.getPathDirToIndexFiles();
            return iFileUtils.getNumberOfFiles(path);
        } catch (FileUtilsException e) {
            throw new IndexationServiceException(e.getMessage(), e);
        }
    }
    
}
