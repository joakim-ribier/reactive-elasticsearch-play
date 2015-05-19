package models.search;

import java.nio.file.Path;
import java.util.Optional;

public class PathModel {
    
    private final Optional<Path> path;
    private final Optional<String> filename;
    
    public PathModel(Optional<String> filename, Optional<Path> path) {
        this.filename = filename;
        this.path = path;
    }
    
    public Optional<Path> getPath() {
        return path;
    }
    
    public Optional<String> getFilename() {
        return filename;
    }
    
}
