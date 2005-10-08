package org.pgist.renderkit;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.component.UIAction;
import org.pgist.model.Node;
import org.pgist.model.Tree;


/**
 * Renderer for TreeMap tag
 * @author kenny
 *
 */
public class DoTreeMapRenderer extends BaseRenderer {


    public void decode(FacesContext context, UIComponent component) throws NullPointerException {
        String prefix = (String) component.getAttributes().get("_PREFIX");
        String paramName = getHiddenFieldName(context, component);
        String clientId = component.getClientId(context);
        
        Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
        String value = (String)requestParameterMap.get(paramName);
        if(value == null || value.equals("") || !clientId.equals(value)) return;
        
        String treeId = (String) requestParameterMap.get(prefix+"_treeId");
        String nodeId = (String) requestParameterMap.get(prefix+"_nodeId");
        if (treeId!=null && !"".equals(treeId) && nodeId!=null && !"".equals(nodeId)) {
            UIAction compt = (UIAction) component;
            compt.getParams().put("treeId", treeId);
            compt.getParams().put("nodeId", nodeId);
            ActionEvent event = new ActionEvent(component);
            component.queueEvent(event);
        }
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
        String formId = getMyForm(context, component).getClientId(context);
        String prefix = (String) component.getAttributes().get("_PREFIX");
        String paramName = getHiddenFieldName(context, component);
        String clientId = component.getClientId(context);
        
        //render current node
        writer.startElement("table", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("width", "100%", null);
        
        writer.startElement("tr", null);
        writer.startElement("td", null);
        writer.writeAttribute("width", "100%", null);
        writer.writeAttribute("valign", "top", null);
        
        int n = node.getChildren().size();
        if (n>1) {
            writer.writeAttribute("colspan", ""+n, null);
        }
        String tone = BeanUtils.getNestedProperty(node, "tone");
        if ("1".equals(tone)) {
            writer.startElement("input", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("value", ".", null);
            writer.writeAttribute("onClick",
                "document.forms['" + formId+"']['"+prefix+"_nodeId'].value="+node.getId()+";"
              + "document.forms['" + formId+"']['"+paramName+"'].value='"+clientId+"';"
              + "document.forms['" + formId + "'].submit();"
                , null);
            writer.endElement("input");
        } else if ("2".equals(tone)) {
            writer.startElement("input", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("value", "?", null);
            writer.endElement("input");
        } else if ("3".equals(tone)) {
            writer.startElement("input", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("value", "!", null);
            writer.endElement("input");
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


}//class DoTreeMapRenderer
