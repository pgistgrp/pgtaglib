package org.pgist.renderkit;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.renderkit.html_basic.HtmlBasicInputRenderer;
import com.sun.faces.util.Util;


public class OptionalInputRenderer extends HtmlBasicInputRenderer {


     public OptionalInputRenderer() {
     }
     
     
     public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
         if(context == null || component == null)
             throw new NullPointerException("Error @ OptionalInputRenderer.encodeBegin");
         else
             return;
     }//encodeBegin()
     
     
     public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
         if(context == null || component == null)
             throw new NullPointerException("Error @ OptionalInputRenderer.encodeChildren");
         else
             return;
     }//encodeChildren()
     
     
     protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
         boolean secret = "true".equals(component.getAttributes().get("secret"));
         
         ResponseWriter writer = context.getResponseWriter();
         Util.doAssert(writer != null);
         String styleClass = null;
         String redisplay = "" + component.getAttributes().get("redisplay");
         if(redisplay == null || !redisplay.equals("true")) currentValue = "";
         
         writer.startElement("table", component);
         writer.startElement("tr", component);
         writer.startElement("td", component);
         
         writer.startElement("input", component);
         writer.writeAttribute("type", "checkbox", "type");
         writer.writeAttribute("name", component.getClientId(context)+"_selector", "clientId");
         writer.writeAttribute("onClick", getOnClick(context, component), null);
         writer.endElement("input");
         
         writer.endElement("td");
         writer.startElement("td", component);
         
         writer.startElement("input", component);
         writeIdAttributeIfNecessary(context, writer, component);
         if (secret) {
             writer.writeAttribute("type", "password", "type");
             writer.writeAttribute("disabled", "true", "disabled");
         } else {
             writer.writeAttribute("type", "text", "type");
         }
         writer.writeAttribute("name", component.getClientId(context), "clientId");
         if(currentValue != null) writer.writeAttribute("value", currentValue, "value");
         Util.renderPassThruAttributes(writer, component);
         Util.renderBooleanPassThruAttributes(writer, component);
         if(null != (styleClass = (String)component.getAttributes().get("styleClass")))
             writer.writeAttribute("class", styleClass, "styleClass");
         writer.endElement("input");

         writer.endElement("td");
         writer.endElement("tr");
         writer.endElement("table");
     }//getEndTextToRender()
     

     protected UIForm getMyForm(FacesContext context, UIComponent component) {
         UIComponent parent;
         for(parent = component.getParent(); parent != null; parent = parent.getParent())
             if(parent instanceof UIForm)
                 break;

         return (UIForm)parent;
     }


     public String getOnClick(FacesContext context, UIComponent component) {
         return "document.forms['" + getMyForm(context, component).getClientId(context) + "']['"
                  + component.getClientId(context) + "'].disabled=!"
                  + "document.forms['" + getMyForm(context, component).getClientId(context) + "']"
                  + "['" + component.getClientId(context)+"_selector'].checked";
     }
     
     
}//class OptionalInputRenderer
