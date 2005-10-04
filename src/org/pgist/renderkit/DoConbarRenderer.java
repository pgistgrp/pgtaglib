package org.pgist.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.component.UIAction;
import org.pgist.model.Node;
import org.pgist.model.Tree;


/**
 * Renderer for Conbar tag
 * @author kenny
 *
 */
public class DoConbarRenderer extends BaseRenderer {


    public void decode(FacesContext context, UIComponent component) throws NullPointerException {
        String prefix = (String) component.getAttributes().get("_PREFIX");
        Map requestParameterMap = context.getExternalContext().getRequestParameterValuesMap();
        String treeId = ((String[]) requestParameterMap.get(prefix+"_treeId"))[0];
        String nodeId = ((String[]) requestParameterMap.get(prefix+"_nodeId"))[0];
        if (treeId!=null && !"".equals(treeId) && nodeId!=null && !"".equals(nodeId)) {
            UIAction compt = (UIAction) component;
            compt.getParams().put("treeId", treeId);
            compt.getParams().put("nodeId", nodeId);
            ActionEvent event = new ActionEvent(component);
            component.queueEvent(event);
            System.out.println("---> 0000000");
        }
    }//decode()
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
        
        String prefix = (String) component.getAttributes().get("_PREFIX");
        String formId = getMyForm(context, component).getClientId(context);
        
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
                nodes.add(0, theNode);
                theNode = theNode.getParent();
            }//while
            
            for (int i=0, n=nodes.size(); i<n; i++) {
                Node one = (Node) nodes.get(i);
                writer.startElement("tr", null);
                writer.startElement("td", null);
                writer.writeAttribute("width", "100%", null);
                Object content = BeanUtils.getNestedProperty(one, "content.contentAsObject");
                if (content instanceof String) {
                    String s = (String) content;
                    if (s.length()>50) s = s.substring(0, 47)+"...";
                    
                    writeLink(writer, s, one, prefix, formId);
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

                writeLink(writer, s, node, prefix, formId);
            }
            writer.endElement("td");
            writer.endElement("tr");

            writer.endElement("table");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeBegin()


    private void writeLink(ResponseWriter writer, String text, Node node, String prefix, String formId) throws Exception {
        writer.startElement("a", null);
        writer.writeAttribute("href", "#", null);
        writer.writeAttribute("onClick",
            "document.forms['" + formId+"']['"+prefix+"_nodeId'].value="+node.getId()+";"
          + "document.forms['" + formId + "'].submit();", null);
        writer.writeText("►", null);
        writer.writeText(text, null);
        writer.endElement("a");
    }//writeLink()
    
    
    protected UIForm getMyForm(FacesContext context, UIComponent component) {
        UIComponent parent;
        for(parent = component.getParent(); parent != null; parent = parent.getParent()) {
            if(parent instanceof UIForm) break;
        }

        return (UIForm)parent;
    }//getMyForm()
    
    
}//class DoConbarRenderer
