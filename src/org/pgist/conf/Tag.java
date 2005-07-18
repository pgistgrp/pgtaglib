package org.pgist.conf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 
 * @author kenny
 *
 */
public class Tag {

    
    protected String name;
    protected Map    properties = new HashMap();
    
    
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    public void addProperty(String name, String value) {
        properties.put(name, value);
    }
    
    
    public String getProperty(String name) {
        return (String) properties.get(name);
    }
    
    
    public String compose() {
        StringBuffer sb = new StringBuffer(name);
        
        for (Iterator iter=properties.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
        }//for iter
        
        return sb.toString();
    }


    public Map getProperties() {
        return properties;
    }
    
    
}
