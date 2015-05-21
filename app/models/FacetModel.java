package models;

import java.io.Serializable;

public class FacetModel implements Serializable {
    
    private static final long serialVersionUID = -8678160660456859425L;
    
    private final String text;
    private final int count;
    private final int weight;
    
    public FacetModel(String text, int count, int weight) {
        this.text = text;
        this.count = count;
        this.weight = weight;
    }
    
    public String getText() {
        return text;
    }
    
    public int getCount() {
        return count;
    }
    
    public int getWeight() {
        return weight;
    }
    
}
