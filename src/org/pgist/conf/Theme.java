package org.pgist.conf;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author kenny
 *
 */
public class Theme {
    
    
    private String name;
    private Map    tags = new HashMap();
    
    
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    public Map getTags() {
        return tags;
    }
    
    
    public Tag getTag(String name) {
        return (Tag) tags.get(name);
    }
    
    
    public void addTag(Tag tag) {
        tags.put(tag.getName(), tag);
    }


}//class Theme
