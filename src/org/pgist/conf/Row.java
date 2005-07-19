package org.pgist.conf;

import java.util.List;
import java.util.ArrayList;


/**
 * 
 * @author kenny
 *
 */
public class Row extends Tag {

    
    private String highlight;
    private List alterColors = new ArrayList(5); 
    
    
    public Row() {
        this.name = "tr";
    }
    

    public void addColor(String color) {
        alterColors.add(color);
    }
    
    
    public String getColor(int index) {
        return (String) alterColors.get(index);
    }
    
    
    public int alterSize() {
        return alterColors.size();
    }


    public String getHighlight() {
        return highlight;
    }


    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }
    
    
}
