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
import org.pgist.model.IContent;
import org.pgist.model.IImage;
import org.pgist.model.ILink;
import org.pgist.model.INode;
import org.pgist.model.IPdf;
import org.pgist.model.IText;
import org.pgist.model.ITree;
import org.pgist.util.Utils;

public class DoUpTreeRenderer extends BaseRenderer {


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
        
        String prefix = (String) component.getAttributes().get("_PREFIX");
        String paramName = getHiddenFieldName(context, component);
        String formId = getMyForm(context, component).getClientId(context);
        String clientId = component.getClientId(context);
        String varPrefix = clientId.replace(':', '_');
        
        ITree tree = (ITree) component.getValueBinding("tree").getValue(context);
        INode node = (INode) component.getValueBinding("node").getValue(context);
        if (node==null) node = tree.getRoot();
        
        ResponseWriter writer = context.getResponseWriter();
        
        try {
            writer.write("<script language=\"JavaScript\">\n");
            writer.write("function "+varPrefix+"_submitSelection(n) {\n");
            writer.write("document.forms['" + formId+"']['"+prefix+"_nodeId'].value=n;");
            writer.write("document.forms['" + formId+"']['"+paramName+"'].value='"+clientId+"';\n");
            writer.write("document.forms['" + formId+"'].submit();\n");
            writer.write("}\n");
            writer.write("</script>\n");

            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "100%", null);
            
            //Render the root node
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("class", "discourse", null);
            writer.writeAttribute("width", "100%", null);
            writer.writeText("● Discourse Object : ", null);
            writer.writeText(BeanUtils.getNestedProperty(tree, "title"), null);
            writer.writeText("  --- ", null);
            writer.writeText(tree.getRoot().getOwner().getLoginname(), null);
            writer.endElement("td");
            writer.endElement("tr");
            
            //Render the UpTree nodes
            List nodes = new ArrayList();
            if (node!=tree.getRoot()) {
                INode theNode = node; //node.getParent();
                while (theNode!=null) {
                    INode parent = theNode.getParent();
                    if (parent==null) break;
                    theNode = parent;
                    nodes.add(0, theNode);
                }//while
            }
            
            for (int i=0, n=nodes.size(); i<n; i++) {
                INode one = (INode) nodes.get(i);
                writer.startElement("tr", null);
                writer.startElement("td", null);
                writer.writeAttribute("width", "100%", null);
                writer.writeAttribute("style", "font-size:"
                        + Utils.getFontSize(1.0F - 0.1F * (n-i-1))
                        + "; background-color:"
                        + Utils.getGrayColor(1.0F - 0.1F * (n-i-1)), null);
                writer.startElement("a", null);
                writer.writeAttribute("href", "#", null);
                writer.writeAttribute("onClick", varPrefix+"_submitSelection("+one.getId()+");", null);
                writer.startElement("span", null);
                writer.writeAttribute("class", "uptreeLink1", null);
                String tone = BeanUtils.getNestedProperty(one, "tone");
                if ("1".equals(tone)) {
                    writer.writeText(".", null);
                } else if ("2".equals(tone)) {
                    writer.writeText("?", null);
                } else if ("3".equals(tone)) {
                    writer.writeText("!", null);
                }
                writer.endElement("span");
                
                IContent content = one.getContent();
                String s = "";
                if (content instanceof IImage) {
                    s = "Image:";
                } else if (content instanceof IText) {
                    IText is = (IText) content;
                    s = is.getText();
                    if (s.length()>50) s = s.substring(0, 47)+"...";
                } else if (content instanceof ILink) {
                    ILink link = (ILink) content;
                    s = link.getLink();
                    if (s.length()>50) s = s.substring(0, 47)+"...";
                } else if (content instanceof IPdf) {
                    s = "PDF file: ";
                }
                writer.startElement("span", null);
                writer.writeAttribute("class", "uptreeLink2", null);
                writer.writeText(s, null);
                writer.endElement("span");

                writer.endElement("a");
                writer.writeText("  --- ", null);
                writer.writeText(one.getOwner().getLoginname(), null);
                writer.endElement("td");
                writer.endElement("tr");
            }//for i
            
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
    
    
}//class DoUpTreeRenderer
