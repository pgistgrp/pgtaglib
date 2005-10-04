package org.pgist.renderkit;

import java.io.IOException;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.model.Node;
import org.pgist.model.Tree;


/**
 * Renderer for Target tag
 * @author kenny
 *
 */
public class DoTargetRenderer extends BaseRenderer {


    public void decode(FacesContext context, UIComponent component) throws NullPointerException {
        
    }//decode()
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
        
        Tree tree = (Tree) component.getValueBinding("tree").getValue(context);
        Node node = (Node) component.getValueBinding("node").getValue(context);
        if (node==null) node = tree.getRoot();
        
        String clientId = component.getClientId(context);
        String formId = getMyForm(context, component).getClientId(context);
        String prefix = (String) component.getAttributes().get("_PREFIX");
        String varPrefix = clientId.replace(':', '_');
        
        ResponseWriter writer = context.getResponseWriter();
        
        ViewHandler handler = context.getApplication().getViewHandler();
        String dot = context.getExternalContext().encodeResourceURL(handler.getResourceURL(context, "/images/dot.png"));
        String dot1 = context.getExternalContext().encodeResourceURL(handler.getResourceURL(context, "/images/dot1.png"));
        String question = context.getExternalContext().encodeResourceURL(handler.getResourceURL(context, "/images/question.png"));
        String question1 = context.getExternalContext().encodeResourceURL(handler.getResourceURL(context, "/images/question1.png"));
        String exclam = context.getExternalContext().encodeResourceURL(handler.getResourceURL(context, "/images/exclam.png"));
        String exclam1 = context.getExternalContext().encodeResourceURL(handler.getResourceURL(context, "/images/exclam1.png"));
        
        try {
            writer.write("<script language=\"JavaScript\">\n");
            
            writer.write("function "+varPrefix+"_clickPunctuate(n) {\n");
            writer.write("m = document.forms['" + formId+"']['"+prefix+"_punctuate'].value;\n");
            writer.write("if (m==0) { $('"+clientId+"_dot').src='"+dot+"'; }");
            writer.write("else if (m==1) { $('"+clientId+"_question').src='"+question+"'; }");
            writer.write("else if (m==2) { $('"+clientId+"_exclam').src='"+exclam+"'; }");
            writer.write("if (n==0) { $('"+clientId+"_dot').src='"+dot1+"'; }");
            writer.write("else if (n==1) { $('"+clientId+"_question').src='"+question1+"'; }");
            writer.write("else if (n==2) { $('"+clientId+"_exclam').src='"+exclam1+"'; }");
            writer.write("document.forms['" + formId+"']['"+prefix+"_punctuate'].value=n;\n");
            writer.write("}\n");
            
            writer.write("</script>\n");
            
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "100%", null);
            
            writer.startElement("tr", null);
            writer.writeAttribute("id", clientId+"_text", null);
            writer.writeAttribute("style", clientId+"display:none;", null);
            writer.startElement("td", null);
            Object content = BeanUtils.getNestedProperty(node, "content.contentAsObject");
            if (content instanceof String) {
                String s = (String) content;
                writer.writeText(s, null);
            }
            writer.endElement("td");
            writer.endElement("tr");
            
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "100%", null);

            writer.startElement("tr", null);
            writer.startElement("td", null);

            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_dot", null);
            writer.writeAttribute("src", dot1, null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickPunctuate(0);", null);
            writer.endElement("img");
            
            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_question", null);
            writer.writeAttribute("src", question, null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickPunctuate(1);", null);
            writer.endElement("img");
            
            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_exclam", null);
            writer.writeAttribute("src", exclam, null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickPunctuate(2);", null);
            writer.endElement("img");
            
            writer.endElement("td");
            writer.endElement("tr");
            
            writer.startElement("tr", null);
            writer.startElement("td", null);
            
            //content types
            //TODO
            
            writer.endElement("td");
            writer.endElement("tr");
            
            writer.endElement("table");

            writer.endElement("td");
            writer.endElement("tr");

            writer.endElement("table");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeBegin()


    protected UIForm getMyForm(FacesContext context, UIComponent component) {
        UIComponent parent;
        for(parent = component.getParent(); parent != null; parent = parent.getParent()) {
            if(parent instanceof UIForm) break;
        }

        return (UIForm)parent;
    }//getMyForm()
    
    
}//class DoTargetRenderer
