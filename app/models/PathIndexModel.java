package models;

import java.nio.file.Path;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.BodyContentHandler;
import org.elasticsearch.common.base.MoreObjects;

public class PathIndexModel {
    
    private final Path path;
    private final Metadata metadata;
    private final BodyContentHandler bodyContentHandler;
    
    public PathIndexModel(Path path, BodyContentHandler bodyContentHandler,
            Metadata metadata) {
        
        this.path = path;
        this.bodyContentHandler = bodyContentHandler;
        this.metadata = metadata;
    }
    
    public Metadata getMetadata() {
        return metadata;
    }
    
    public BodyContentHandler getBodyContentHandler() {
        return bodyContentHandler;
    }
    
    public String getContent() {
        if (bodyContentHandler != null) {
            return bodyContentHandler.toString();
        }
        return "";
    }
    
    public Path getPath() {
        return path;
    }
    
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("file.name", path.toFile().getName()).toString();
    }
    
}
