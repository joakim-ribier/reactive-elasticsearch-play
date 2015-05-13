package utils.json;

import java.util.List;
import java.util.Map;

import models.ResultExceptionModel;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class JsonHelper {
    
    public static ObjectNode toObjectNode(Map<String, String> map) {
        ObjectNode objectNode = Json.newObject();
        map.forEach((key, value) -> {
            objectNode.put(key, value);
        });
        return objectNode;
    }
    
    public static JsonNode toResultExceptionModel(String key, List<String> params) {
        return Json.toJson(new ResultExceptionModel(key, params));
    }
    
    public static JsonNode toResultExceptionModel(String key) {
        return Json.toJson(new ResultExceptionModel(key, Lists.newArrayList()));
    }
    
    public static ObjectNode toObjectNode(String key, int value) {
        return toObjectNode(
                ImmutableMap.of(key, String.valueOf(value)));
    }
    
}
