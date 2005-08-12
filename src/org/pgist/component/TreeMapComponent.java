package org.pgist.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.model.Node;
import org.pgist.model.Tree;


/**
 * TreeMap Component
 * @author kenny
 *
 */
public class TreeMapComponent extends UIComponentBase {

    
    public static final String FORM_NUMBER_ATTR = "com.sun.faces.FormNumber";
    protected String title;
    protected String content;
    protected String username;
    protected int depth;
    protected String clientId;
    protected String formClientId;
    protected Tree tree;

    
    public TreeMapComponent() {
        setRendererType(null);
    }
    

    public String getFamily() {
        return "org.pgist.faces.TreeMap";
    }
    

    public boolean getRendersChildren() {
        return true;
    }

    
    public void encodeBegin(FacesContext context) throws IOException {
        title = (String) getAttributes().get("title");
        content = (String) getAttributes().get("content");
        username = (String) getAttributes().get("username");
        depth = Integer.parseInt((String) getAttributes().get("depth"));
        clientId = getClientId(context);
        formClientId = getFormClientId(context);
        
        return;
    }//encodeBegin()
    
    
    public void encodeEnd(FacesContext context) throws IOException {
        Map requestParameterMap = (Map) context.getExternalContext().getRequestParameterMap();
        String treeId = (String) requestParameterMap.get(clientId + "_treeId");
        if (treeId==null || "".equals(treeId)) {
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            treeId = request.getParameter("treeId");
        }
        Long id = new Long(treeId);
        System.out.println("---> treeId: "+id);
        
        if (title==null || content==null || "".equals(title) || "".equals(content)) {
            throw new IOException("property 'title' and 'contnet' must be specified.");
        }

        String binding = (String) getAttributes().get("actionBinding");
        MethodBinding mb = context.getApplication().createMethodBinding(binding, new Class[] { ActionEvent.class, Long.class });
        tree = (Tree) mb.invoke(context, new Object[] { new ActionEvent(this), id });
        
        ResponseWriter writer = context.getResponseWriter();
        
        if (tree==null) {
            writer.writeText("Can't find conversation thread "+id, null);
            return;
        }
        
        try {
            String nodeIdStr = (String) requestParameterMap.get(clientId + "_nodeId");
            if (nodeIdStr==null || "".equals(nodeIdStr)) nodeIdStr = ""+tree.getRoot().getId();
            Long nodeId = new Long(nodeIdStr);
            System.out.println("---> nodeId: "+nodeId);

            //tree's title
            String s = BeanUtils.getProperty(tree, title);
            writer.startElement("span", null);
            writer.writeAttribute("style", "", null);
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            StringBuffer sb = new StringBuffer();
            sb.append("document.forms[");
            sb.append("'").append(formClientId).append("'");
            sb.append("]['");
            sb.append(clientId).append("_treeId");
            sb.append("'].value='");
            sb.append(tree.getId());
            sb.append("';");
            sb.append("document.forms[");
            sb.append("'").append(formClientId).append("'");
            sb.append("]['");
            sb.append(clientId).append("_nodeId");
            sb.append("'].value='';");
            sb.append("document.forms[");
            sb.append("'");
            sb.append(formClientId);
            sb.append("'");
            sb.append("].submit()");
            
            writer.writeAttribute("onClick", sb.toString(), null);
            writer.writeText(s, null);
            writer.endElement("a");
            writer.writeText(" —", null);
            writer.writeText(BeanUtils.getNestedProperty(tree.getRoot(), username), null);
            writer.writeText(" ("+tree.getNodesCount()+" messages)", null);
            writer.endElement("span");
            writer.writeText("\n", null);
            
            //folding tree
            List folding = new ArrayList();
            Node currentNode = tree.findNode(nodeId);
            if (tree.getRoot().getId().longValue()!=nodeId.longValue()) {
                Node temp = currentNode;
                while (temp.getParent()!=null) {
                    temp = temp.getParent();
                    folding.add(temp);
                }//while
                
                for (int i=folding.size()-1; i>=0; i--) {
                    temp = (Node) folding.get(i);
                    encodeFoldingNode(writer, temp, tree.getRoot()==temp);
                }//for
            }
            
            //tree map
            /*
            Stack stack = new Stack();
            stack.push(currentNode);
            while (!stack.empty()) {
                Node node = (Node) stack.pop();
                if (node.getDepth()-currentNode.getDepth()>depth) {
                    encodeTreeMapNode(writer, node, node==currentNode, true);
                } else {
                    encodeTreeMapNode(writer, node, node==currentNode, false);
                }
                
                Set kids = node.getChildren();
                for (Iterator iter = kids.iterator(); iter.hasNext(); ) {
                    stack.push(iter.next());
                }//for iter
            }//while
            */
            encodeNode(writer, currentNode, true, false);

            //hidden field
            writer.startElement("input", null);
            writer.writeAttribute("type", "hidden", null);
            writer.writeAttribute("name", clientId+"_treeId", null);
            writer.writeAttribute("value", tree.getId(), null);
            writer.endElement("input");
            writer.writeText("\n", null);
            //----------
            writer.startElement("input", null);
            writer.writeAttribute("type", "hidden", null);
            writer.writeAttribute("name", clientId+"_nodeId", null);
            writer.writeAttribute("value", nodeId, null);
            writer.endElement("input");
            writer.writeText("\n", null);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeEnd()
    
    
    /**
     * Recursively encode a node
     * @param writer
     * @param node
     * @param isTreeMapRoot
     * @param empty
     */
    private void encodeNode(ResponseWriter writer, Node node, boolean isTreeMapRoot, boolean empty) throws Exception {
        Set children = node.getChildren();

        if (children.size()>0) {
            writer.startElement("table", null);
            if (isTreeMapRoot) {
                //writer.writeAttribute("frame", "box", null);
                writer.writeAttribute("cellpadding", "2", null);
                //writer.writeAttribute("rules", "rows", null);
                //writer.writeAttribute("style", "border-collapse:collapse; border:solid 1px #8CACBB;", null);
                //writer.writeAttribute("border", "1", null);
                writer.writeAttribute("cellspacing", "0", null);
                writer.writeAttribute("width", "100%", null);
            } else {
                //writer.writeAttribute("frame", "box", null);
                //writer.writeAttribute("rules", "groups", null);
            }
            writer.startElement("tr", null);
            writer.startElement("td", null);
            if (children.size()>1) {
                writer.writeAttribute("colspan", ""+children.size(), null);
            }
            writer.writeAttribute("style", "border-bottom:solid 1px #8CACBB;", null);
        }
        writer.startElement("span", null);
        
        writer.startElement("a", null);
        writer.writeAttribute("href", "#", null);
        StringBuffer sb = new StringBuffer();
        sb.append("document.forms[");
        sb.append("'").append(formClientId).append("'");
        sb.append("]['");
        sb.append(clientId).append("_treeId");
        sb.append("'].value='");
        sb.append(tree.getId());
        sb.append("';");
        sb.append("document.forms[");
        sb.append("'").append(formClientId).append("'");
        sb.append("]['");
        sb.append(clientId).append("_nodeId");
        sb.append("'].value='");
        sb.append(node.getId());
        sb.append("';");
        sb.append("document.forms[");
        sb.append("'");
        sb.append(formClientId);
        sb.append("'");
        sb.append("].submit()");
        writer.writeAttribute("onClick", sb.toString(), null);
        
        if (empty) {
            writer.writeText(" ", null);
        } else {
            writer.writeText(BeanUtils.getNestedProperty(node, content), null);
        }
        writer.endElement("a");
        writer.endElement("span");
        
        writer.startElement("span", null);
        writer.writeAttribute("class", "author", null);
        writer.writeText(" —", null);
        writer.writeText(BeanUtils.getNestedProperty(node, username), null);
        writer.endElement("span");
        if (children.size()>0) {
            writer.endElement("td");
            writer.endElement("tr");
        }
        
        if (children.size()>0) {
            writer.startElement("tr", null);
            
            for (Iterator iter = children.iterator(); iter.hasNext(); ) {
                Node one = (Node) iter.next();

                writer.startElement("td", null);
                if (iter.hasNext()) {
                    writer.writeAttribute("style", "border-right:solid 1px #8CACBB;", null);
                }

                encodeNode(writer, one, false, false);

                writer.endElement("td");
            }//for iter

            writer.endElement("tr");
        }
        
        if (children.size()>0) {
            writer.endElement("table");
        }
    }//encodeNode()
    
    private void encodeTreeMapNode(ResponseWriter writer, Node node, boolean isTreeMapRoot, boolean empty) throws Exception {
        writer.startElement("table", null);
        writer.writeAttribute("frame", "box", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("rules", "all", null);
        writer.writeAttribute("style", "border-collapse:collapse; border:solid 1px #8CACBB;", null);
        writer.writeAttribute("border", "1", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("width", "100%", null);
        writer.startElement("tr", null);
        writer.startElement("td", null);
        writer.startElement("span", null);
        writer.startElement("a", null);
        writer.writeAttribute("href", "#", null);
        if (empty) {
            writer.writeText(" ", null);
        } else {
            writer.writeText(BeanUtils.getNestedProperty(node, content), null);
        }
        writer.endElement("a");
        writer.endElement("span");
        writer.startElement("span", null);
        writer.writeAttribute("class", "author", null);
        writer.writeText(" —", null);
        writer.writeText(BeanUtils.getNestedProperty(node, username), null);
        writer.endElement("span");
        writer.endElement("td");
        writer.endElement("tr");
        writer.startElement("tr", null);
        writer.startElement("td", null);
    }


    public void encodeFoldingNode(ResponseWriter writer, Node node, boolean isRoot) throws Exception {
        writer.startElement("table", null);
        writer.startElement("tr", null);
        if (!isRoot) {
            writer.startElement("td", null);
            writer.writeText("►", null);
            writer.endElement("td");
        }
        writer.startElement("td", null);
        writer.startElement("a", null);
        writer.writeAttribute("href", "#", null);
        writer.writeText(getShortContent(node), null);
        writer.endElement("a");
        writer.writeText(" —", null);
        writer.writeText(BeanUtils.getNestedProperty(node, username), null);
        writer.endElement("td");
        writer.endElement("tr");
        writer.startElement("tr", null);
        writer.startElement("td", null);
        writer.endElement("td");
        writer.startElement("td", null);
    }//encodeNode()
    
    
    public String getShortContent(Node node) throws Exception {
        String s = BeanUtils.getNestedProperty(node, username);
        if (s.length()>40) {
            s = s.substring(0, 37)+"...";
        }
        return s;
    }
    

    protected int getFormNumber(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestMap();
        int numForms = 0;
        Integer formsInt = null;
        // find out the current number of forms in the page.
        if (null != (formsInt = (Integer) requestMap.get(FORM_NUMBER_ATTR))) {
            numForms = formsInt.intValue();
            //     since the form index in the document starts from 0.
            numForms--;
        }
        return numForms;
    }//getFormNumber()
    

    private String getFormClientId(FacesContext context) {
        UIComponent parent;
        for(parent = getParent(); parent != null; parent = parent.getParent())
            if(parent instanceof UIForm) break;

        UIForm uiform = (UIForm)parent;
        return uiform.getClientId(context);
    }


}//class TreeMapComponent
