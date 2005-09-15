package org.pgist.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.util.Util;


public class FilterRenderer extends BaseRenderer {

    
    public FilterRenderer() {
    }
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        String style = (String) component.getAttributes().get("style");
        String styleClass = (String) component.getAttributes().get("styleClass");
        
        writer.startElement("div", null);
        writer.writeAttribute("class", "filterButton", null);
        
        writer.startElement("a", null);
        writer.writeAttribute("id", component.getClientId(context)+"_button", null);
        writer.writeAttribute("href", "#", null);
        
        StringBuffer sb = new StringBuffer();
        sb.append("var button=document.getElementById('")
          .append(component.getClientId(context)).append("_button")
          .append("');");
        sb.append("var filter=document.getElementById('")
        .append(component.getClientId(context)).append("_filter")
        .append("');");
        sb.append("centerX2Y(filter, button);");
        sb.append("toggleDisplay(filter, 'block', 'none');");
        sb.append("return false;");

        writer.writeAttribute("onClick", sb.toString(), null);
        writer.writeText("Filter", null);
        writer.endElement("a");
        
        writer.endElement("div");
        
        writer.startElement("div", null);
        writer.writeAttribute("id", component.getClientId(context)+"_filter", null);
        if (style!=null && !"".equals(style)) {
            writer.writeAttribute("style", style, null);
        }
        writer.writeAttribute("class", styleClass, null);
    }//encodeBegin()
    
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div");
    }//encodeEnd()
    
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        if(context == null || component == null)
            throw new NullPointerException(Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        if(!component.isRendered()) return;
        
        for(Iterator kids = component.getChildren().iterator(); kids.hasNext(); ) {
            UIComponent kid = (UIComponent)kids.next();
            if(kid.isRendered()) {
                encodeRecursive(context, kid);
            }
        }//for kids
    }//encodeChildren()


}//class FilterRenderer
