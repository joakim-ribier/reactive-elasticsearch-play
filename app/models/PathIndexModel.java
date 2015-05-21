package models;

import java.nio.file.Path;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.BodyContentHandler;
import org.elasticsearch.common.base.MoreObjects;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class PathIndexModel {
    
    private final Path path;
    private final Metadata metadata;
    private final BodyContentHandler bodyContentHandler;
    private final List<String> tags;
    
    public PathIndexModel(Path path, BodyContentHandler bodyContentHandler,
            Metadata metadata) {
        
        this.path = path;
        this.bodyContentHandler = bodyContentHandler;
        this.metadata = metadata;
        this.tags = Lists.newArrayList();
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
    
    public String getExtension() {
        String contentType = metadata.get("Content-Type");
        switch (contentType) {
        case "application/pdf":
            return "pdf";
        default:
            return "";
        }
    }
    
    public Path getPath() {
        return path;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(String value) {
        if (!Strings.isNullOrEmpty(value)) {
            Iterable<String> iterable = Splitter.on(" ").split(value);
            iterable.forEach(item -> tags.add(item));
        }
    }
    
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("file.name", path.toFile().getName()).toString();
    }
    
}
