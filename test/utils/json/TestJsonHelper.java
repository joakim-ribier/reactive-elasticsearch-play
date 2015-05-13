package utils.json;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import utils.xcontent.XContentHelperException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class TestJsonHelper {
    
    @Test
    public void testToObjectNode() throws XContentHelperException, IOException {
        ObjectNode expected = JsonHelper.toObjectNode("my-key", 1);
        assertThat(expected.toString()).isEqualTo("{\"my-key\":\"1\"}");
    }
    
    @Test
    public void testToObjectNodeWithMapParameter() throws XContentHelperException, IOException {
        ObjectNode expected = JsonHelper.toObjectNode(
                ImmutableMap.of(
                        "my-key-1", "my-value-1",
                        "my-key-2", "my-value-2"));
        
        assertThat(expected.toString())
            .contains("\"my-key-1\":\"my-value-1\"")
            .contains("\"my-key-2\":\"my-value-2\"");
    }
    
    @Test
    public void testToResultExceptionModel() throws XContentHelperException, IOException {
        JsonNode expected = JsonHelper.toResultExceptionModel("my-key");
        assertThat(expected.toString())
            .isEqualTo("{\"key\":\"my-key\",\"params\":[]}");
    }
    
    @Test
    public void testToResultExceptionModelWithParameters() throws XContentHelperException, IOException {
        JsonNode expected = JsonHelper.toResultExceptionModel("my-key", Lists.newArrayList("param1", "param2"));
        assertThat(expected.toString())
            .isEqualTo("{\"key\":\"my-key\",\"params\":[\"param1\",\"param2\"]}");
    }
    
}
