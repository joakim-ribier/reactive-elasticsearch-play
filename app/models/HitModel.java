package models;

import com.google.common.base.Objects;

public class HitModel {

    private final String id;
    private final String fileName;
    private final String indexingDate;
    private final Integer size;

    public HitModel(String id, String fileName, String indexingDate,
            Integer size) {
        
        this.id = id;
        this.fileName = fileName;
        this.indexingDate = indexingDate;
        this.size = size;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(id, fileName, indexingDate, size);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof HitModel) {
            HitModel that = (HitModel) object;
            return Objects.equal(this.id, that.id)
                    && Objects.equal(this.fileName, that.fileName)
                    && Objects.equal(this.indexingDate, that.indexingDate)
                    && Objects.equal(this.size, that.size);
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
