package utils.xcontent;

import java.util.Map;
import java.util.Optional;


public interface IXContentHelper {
    
    Optional<String> findValueToString(Map<String, Object> map, String key);
    
    Optional<Integer> findValueToLong(Map<String, Object> map, String key);
    
}
