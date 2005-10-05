package org.pgist.renderkit;

import java.io.IOException;

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
        
        ResponseWriter writer = context.getResponseWriter();
        
        try {
            writer.startElement("div", null);
            writer.writeAttribute("style", "width:100%;height:100px;", null);
            writer.writeText(BeanUtils.getNestedProperty(node, "content.contentAsObject"), null);
            writer.endElement("div");
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
