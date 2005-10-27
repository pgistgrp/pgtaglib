package org.pgist.renderkit;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.pgist.model.IFile;
import org.pgist.model.INode;
import org.pgist.model.ITree;


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
        
        String prefix = (String) component.getAttributes().get("_PREFIX");

        ITree tree = (ITree) component.getValueBinding("tree").getValue(context);
        INode node = (INode) component.getValueBinding("node").getValue(context);
        if (node==null) node = tree.getRoot();
        
        ResponseWriter writer = context.getResponseWriter();
        
        try {
            writer.startElement("div", null);
            writer.writeAttribute("style", "width:100%;height:100px;", null);
            
            String cttType = BeanUtils.getNestedProperty(node, "content.type");
            Object obj = PropertyUtils.getNestedProperty(node, "content.contentAsObject");
            if ("0".equals(cttType)) {//text
                writer.writeText(obj, null);
            } else if ("1".equals(cttType)) {//image
                IFile file = (IFile) obj;
                writer.startElement("a", null);
                writer.writeAttribute("href", "#", null);
                writer.writeAttribute("onClick", prefix.replace(":", "_")+"_showImage('"+file.getId()+"');", null);
                writer.writeText("[image]", null);
                writer.endElement("a");
            } else if ("2".equals(cttType)) {//link
                writer.startElement("a", null);
                writer.writeAttribute("href", "#", null);
                String link = context.getExternalContext().encodeResourceURL(
                    context.getApplication().getViewHandler().getResourceURL(context, obj.toString())
                );
                writer.writeAttribute("onClick", prefix.replace(":", "_")+"_showLink('"+link+"');", null);
                writer.writeText(obj.toString(), null);
                writer.endElement("a");
            } else if ("3".equals(cttType)) {//pdf
                IFile file = (IFile) obj;
                writer.startElement("a", null);
                writer.writeAttribute("href", "#", null);
                writer.writeAttribute("onClick", prefix.replace(":", "_")+"_showImage('"+file.getId()+"');", null);
                writer.writeText("[pdf file]", null);
                writer.endElement("a");
            }
            
            writer.endElement("div");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeBegin()


}//class DoTargetRenderer
