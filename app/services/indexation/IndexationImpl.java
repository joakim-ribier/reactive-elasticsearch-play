package services.indexation;

import java.nio.file.Path;

import utils.file.FileUtilsException;
import utils.file.IFileUtils;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IndexationImpl implements IndexationService {
    
    private final IFileUtils iFileUtils;
    
    @Inject
    protected IndexationImpl(IFileUtils iFileUtils) {
        this.iFileUtils = iFileUtils;
    }

    @Override
    public void indexAllFilesInADirectory(Path path)
            throws IndexationServiceException {
        
        Preconditions.checkNotNull(path, "The 'path' parameter is required.");
        try {
            iFileUtils.findRecursivelyAllFilesInADirectory(path);
        } catch (FileUtilsException e) {
            throw new IndexationServiceException(e.getMessage(), e);
        }
    }
    
}
