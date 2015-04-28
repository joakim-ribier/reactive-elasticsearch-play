package utils.xcontent;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.inject.Singleton;

@Singleton
public class XContentHelper implements IXContentHelper {
    
    @Override
    public Optional<String> findValueToString(Map<String, Object> source, String key) {
        Object find = find(key, source);
        if (find == null) {
            return Optional.empty();
        }
        return Optional.of((String)find);
    }
    
    @Override
    public Optional<Integer> findValueToLong(Map<String, Object> map, String key) {
        Object find = find(key, map);
        if (find == null) {
            return Optional.empty();
        }
        return Optional.of((Integer)find);
    }
    
    @SuppressWarnings("unchecked")
    private Object find(String key, Map<String, Object> source) {
        for (Entry<String, Object> entry : source.entrySet()) {
            if (entry.getValue() instanceof Map) {
                Object value = find(key, (Map<String, Object>) entry.getValue());
                if (value != null) {
                    return value;
                }
            } else {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
    
}
