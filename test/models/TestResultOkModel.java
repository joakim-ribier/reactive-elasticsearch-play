package models;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class TestResultOkModel {

    @Test
    public void testEquals() {
        ResultOkModel resultOkModel01 = build("OK", "return OK");
        ResultOkModel resultOkModel02 = build("OK", "return OK");
        
        assertThat(resultOkModel01).isEqualTo(resultOkModel02);
        assertThat(resultOkModel01.toString()).isEqualTo(resultOkModel02.toString());
    }
    
    @Test
    public void testIsNotEqualsByStatus() {
        ResultOkModel resultOkModel01 = build("OK", "return OK");
        ResultOkModel resultOkModel02 = build("NotFOUND", "return OK");
        
        assertThat(resultOkModel01).isNotEqualTo(resultOkModel02);
        assertThat(resultOkModel01.getStatus()).isNotEqualTo(resultOkModel02.getStatus());
    }
    
    @Test
    public void testIsNotEqualsByMessage() {
        ResultOkModel resultOkModel01 = build("OK", "return OK");
        ResultOkModel resultOkModel02 = build("OK", "return not found");
        
        assertThat(resultOkModel01).isNotEqualTo(resultOkModel02);
        assertThat(resultOkModel01.getMessage())
            .isNotEqualTo(resultOkModel02.getMessage());
    }
    
    @Test
    public void testToJson() {
        ResultOkModel resultOkModel01 = build("OK", "return OK");
        JsonNode json = resultOkModel01.toJson();
        
        assertThat(json).isNotNull();
        assertThat(json.get("status").asText()).isEqualTo("OK");
        assertThat(json.get("message").asText()).isEqualTo("return OK");
    }
    
    @Test
    public void testEqualsWithADifferentObject() {
        ResultOkModel resultOkModel01 = build("OK", "return OK");
        
        assertThat(resultOkModel01).isNotEqualTo("Test with a different object");
    }
    
    private ResultOkModel build(String status, String message) {
        return new ResultOkModel(status, message);        
    }
}
