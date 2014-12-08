package models;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Objects;

public class ResultOkModel {

    private final String status;
    private final String message;
    
    public ResultOkModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }

    public JsonNode toJson() {
        return Json.toJson(this);        
    }
    
    @Override
    public int hashCode(){
    	return Objects.hashCode(status, message);
    }
    
    @Override
    public boolean equals(Object object){
    	if (object instanceof ResultOkModel) {
    		ResultOkModel that = (ResultOkModel) object;
    		return Objects.equal(this.status, that.status)
    			&& Objects.equal(this.message, that.message);
    	}
    	return false;
    }

    @Override
    public String toString() {
    	return Objects.toStringHelper(this)
    		.add("status", status)
    		.add("message", message)
    		.toString();
    }
}
