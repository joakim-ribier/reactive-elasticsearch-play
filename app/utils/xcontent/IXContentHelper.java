package utils.xcontent;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface IXContentHelper {
    
    Optional<String> findValueToString(Map<String, Object> map, String key);
    
    Optional<Integer> findValueToLong(Map<String, Object> map, String key);
    
    /**
     * Gets string array values by the key parameter.
     * 
     * @param source map object.
     * @param key string object.
     * @return string array.
     */
    Optional<List<String>> findValueToList(Map<String, Object> source, String key);
    
}
