package org.pgist.renderkit;

import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;


public class ToolbarRenderer extends BaseRenderer {

    
    public ToolbarRenderer() {
    }

    
    public boolean getRendersChildren() {
        return true;
    }

    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if(context == null || component == null)
            throw new NullPointerException(Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        
        if(!component.isRendered()) return;
        
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("table", component);
        String id = component.getId();
        if (id!=null && !id.startsWith("_id")) {
            writer.writeAttribute("id", component.getClientId(context), "id");
        }
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        String styleClass = (String)component.getAttributes().get("styleClass");
        if(styleClass != null) writer.writeAttribute("class", styleClass, "styleClass");
        //Util.renderPassThruAttributes(writer, component);
        writer.writeText("\n", null);
    }//encodeBegin()
    

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        if(context == null || component == null)
            throw new NullPointerException(Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        if(!component.isRendered()) return;
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("tbody", component);
        writer.writeText("\n", null);
        writer.startElement("tr", component);
        
        for(Iterator kids = component.getChildren().iterator(); kids.hasNext(); ) {
            UIComponent kid = (UIComponent)kids.next();
            if(kid.isRendered()) {
                writer.startElement("td", component);
                writer.writeAttribute("style", "padding-left:5px;", null);
                writer.writeText("[", null);
                encodeRecursive(context, kid);
                writer.writeText("]", null);
                writer.endElement("td");
                writer.writeText("\n", null);
            }
        }//for kids

        writer.endElement("tr");
        writer.endElement("tbody");
        writer.writeText("\n", null);
    }//encodeChildren()

    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if(context == null || component == null)
            throw new NullPointerException(Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        if(!component.isRendered()) return;
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("table");
        writer.writeText("\n", null);
    }//encodeEnd()

    
}//class ToolbarRenderer

