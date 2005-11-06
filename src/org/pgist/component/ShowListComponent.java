package org.pgist.component;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.beanutils.BeanUtils;

public class ShowListComponent extends UIComponentBase {


    public ShowListComponent() {
        setRendererType(null);
    }
    

    public String getFamily() {
        return "org.pgist.faces.ShowList";
    }

    
    public boolean getRendersChildren() {
        return true;
    }


    public void encodeBegin(FacesContext context) throws IOException {
        return;
    }//encodeBegin()
    
    
    public void encodeEnd(FacesContext context) throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        
        Iterable value = (Iterable) getAttributes().get("value");
        String styleClass = (String) getAttributes().get("styleClass");
        String field = (String) getAttributes().get("field");

        try {
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            if (styleClass!=null) {
                writer.writeAttribute("class", styleClass, null);
            }
            
            for (Iterator iter=value.iterator(); iter.hasNext(); ) {
                Object one = iter.next();
                writer.endElement("tr");
                writer.startElement("td", null);
                
                String s = BeanUtils.getNestedProperty(one, field);
                if (s==null || "".equals(s.trim())) {
                    writer.write("&nbsp;");
                } else {
                    writer.writeText(s, null);
                }
                
                writer.endElement("td");
                writer.endElement("tr");
            }//for iter
            
            writer.endElement("table");
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeEnd()


}//class ShowListComponent
