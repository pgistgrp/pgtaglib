package org.pgist.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.model.Node;
import org.pgist.model.Tree;


/**
 * Renderer for Conbar tag
 * @author kenny
 *
 */
public class DoConbarRenderer extends BaseRenderer {


    public void decode(FacesContext context, UIComponent component) throws NullPointerException {
        
    }//decode()
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
        
        Tree tree = (Tree) component.getValueBinding("tree").getValue(context);
        Node node = (Node) component.getValueBinding("node").getValue(context);
        if (node==null) node = tree.getRoot();
        
        ResponseWriter writer = context.getResponseWriter();
        
        try {
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "100%", null);
            
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            writer.writeText("●", null);
            writer.writeText(BeanUtils.getNestedProperty(tree, "title"), null);
            writer.endElement("td");
            writer.endElement("tr");

            List nodes = new ArrayList();
            Node theNode = node.getParent();
            while (theNode!=null) {
                Node parent = theNode.getParent();
                if (parent==null) break;
                nodes.add(theNode);
                theNode = parent;
            }//while
            
            for (int i=0, n=nodes.size(); i<n; i++) {
                Node one = (Node) nodes.get(i);
                writer.startElement("tr", null);
                writer.startElement("td", null);
                writer.writeAttribute("width", "100%", null);
                Object content = BeanUtils.getNestedProperty(one, "content");
                if (content instanceof String) {
                    String s = (String) content;
                    if (s.length()>50) s = s.substring(0, 47)+"...";
                    writer.writeText("►", null);
                    writer.writeText(s, null);
                }
                writer.endElement("td");
                writer.endElement("tr");
            }//for i
            
            writer.startElement("tr", null);
            writer.startElement("td", null);
            Object content = BeanUtils.getNestedProperty(node, "content.contentAsObject");
            if (content instanceof String) {
                String s = (String) content;
                if (s.length()>50) s = s.substring(0, 47)+"...";
                writer.writeText("►", null);
                writer.writeText(s, null);
            }
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
    
    
}//class DoConbarRenderer
