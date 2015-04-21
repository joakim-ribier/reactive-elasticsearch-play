package models;

import java.util.List;

import com.google.common.base.Objects;

public class ResultExceptionModel {
    
    private final String key;
    private final List<String> params;
    
    public ResultExceptionModel(String key, List<String> params) {
        this.key = key;
        this.params = params;
    }
    
    public String getKey() {
        return key;
    }
    
    public List<String> getParams() {
        return params;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(key, params);
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof ResultExceptionModel) {
            ResultExceptionModel that = (ResultExceptionModel) object;
            return Objects.equal(this.key, that.key)
                    && Objects.equal(this.params, that.params);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("key", key)
                .add("params", params).toString();
    }
    
}
