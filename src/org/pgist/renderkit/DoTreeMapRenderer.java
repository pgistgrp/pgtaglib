package org.pgist.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.model.Node;
import org.pgist.model.Tree;


/**
 * Renderer for TreeMap tag
 * @author kenny
 *
 */
public class DoTreeMapRenderer extends BaseRenderer {


    public void decode(FacesContext context, UIComponent component) throws NullPointerException {
        
    }//decode()
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
        
        Tree tree = (Tree) component.getValueBinding("tree").getValue(context);
        Node node = (Node) component.getValueBinding("node").getValue(context);
        if (node==null) node = tree.getRoot();
        
        int depth = 5;
        String depthStr = (String) component.getAttributes().get("depth");
        try {
            depth = Integer.parseInt(depthStr);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        try {
            encodeNode(context, component, node, depth);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeBegin()
    
    
    /**
     * Recursively render the node and its children
     * @param context
     * @param component
     * @throws IOException
     */
    private void encodeNode(FacesContext context, UIComponent component, Node node, int depth) throws Exception {
        ResponseWriter writer = context.getResponseWriter();
        
        //render current node
        writer.startElement("table", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("width", "100%", null);
        
        writer.startElement("tr", null);
        writer.startElement("td", null);
        writer.writeAttribute("width", "100%", null);
        
        int n = node.getChildren().size();
        if (n>1) {
            writer.writeAttribute("colspan", ""+n, null);
        }
        Object content = BeanUtils.getNestedProperty(node, "content.contentAsObject");
        if (content instanceof String) {
            String s = (String) content;
            writer.writeText(s, null);
        } else {
            writer.writeText("Non-String Contents", null);
        }
        writer.endElement("td");
        writer.endElement("tr");
        
        //render children
        if (depth>0 && node.getChildren().size()>0) {
            writer.startElement("tr", null);

            for (Iterator iter=node.getChildren().iterator(); iter.hasNext(); ) {
                writer.startElement("td", null);
                
                Node child = (Node) iter.next();
                encodeNode(context, component, child, depth-1);
                
                writer.endElement("td");
            }//for iter
            
            writer.endElement("tr");
        }
        
        writer.endElement("table");
    }//encodeNode()


    protected UIForm getMyForm(FacesContext context, UIComponent component) {
        UIComponent parent;
        for(parent = component.getParent(); parent != null; parent = parent.getParent()) {
            if(parent instanceof UIForm) break;
        }

        return (UIForm)parent;
    }//getMyForm()
    
    
}//class DoTreeMapRenderer
