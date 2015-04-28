package models;

import java.util.Optional;

import com.google.common.base.Objects;

public class HitModel {

    private final String id;
    private final String fileName;
    private final String indexingDate;
    private final Integer size;
    private final boolean selected;
    
    public static class Builder {
        
        private String id;
        private String fileName;
        private String indexingDate;
        private Integer size;
        
        public Builder withId(String value) {
            this.id = value;
            return this;
        }
        public Builder withFileName(Optional<String> value) {
            if (value.isPresent()) {
                this.fileName = value.get();
            } else {
                this.fileName = "unknow";
            }
            return this;
        }
        
        public Builder withDate(Optional<String> value) {
            if (value.isPresent()) {
                this.indexingDate = value.get();
            } else {
                this.indexingDate = "unknow";
            }
            return this;
        }
        
        public Builder withSize(Optional<Integer> value) {
            if (value.isPresent()) {
                this.size = value.get();
            } else {
                this.size = -1;
            }
            return this;
        }
        
        public HitModel build() {
            return new HitModel(id, fileName, indexingDate, size);
        }
        
    }
    
    
    private HitModel(String id, String fileName, String indexingDate,
            Integer size) {
        
        this.id = id;
        this.fileName = fileName;
        this.indexingDate = indexingDate;
        this.size = size;
        this.selected = false;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getIndexingDate() {
        return indexingDate;
    }

    public Integer getSize() {
        return size;
    }

    public boolean isSelected() {
        return selected;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id, fileName, indexingDate, size, selected);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof HitModel) {
            HitModel that = (HitModel) object;
            return Objects.equal(this.id, that.id)
                    && Objects.equal(this.fileName, that.fileName)
                    && Objects.equal(this.indexingDate, that.indexingDate)
                    && Objects.equal(this.size, that.size)
                    && Objects.equal(this.selected, that.selected);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id)
                .add("fileName", fileName).add("indexingDate", indexingDate)
                .add("size", size).toString();
    }
}
