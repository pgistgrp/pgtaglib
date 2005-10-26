package org.pgist.component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.model.INode;
import org.pgist.model.ITree;


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
    protected String tone;
    protected int depth;
    protected String clientId;
    protected String formClientId;
    protected int toneId;
    protected ITree tree;
    protected String[] colors = {
        "#FFD8E8",
        "#D8E8FF",
        "#E8D8FF",
        "#D8FFFF",
        "#E8FFE8",
        "#D8D8FF",
        "#FFE8E8",
        "#FFFFD8"
    };
    protected int nextColor = 0;
    protected int currentDepth = 0;
    protected INode currentNode = null;

    
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
        tone = (String) getAttributes().get("tone");
        depth = Integer.parseInt((String) getAttributes().get("depth"));
        clientId = getClientId(context);
        formClientId = getFormClientId(context);

        Map requestParameterMap = (Map) context.getExternalContext().getRequestParameterMap();
        String treeId = (String) requestParameterMap.get(clientId + "_treeId");
        if (treeId==null || "".equals(treeId)) {
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            tree = (ITree) request.getAttribute("thread");
        } else {
            Long id = new Long(treeId);
            String binding = (String) getAttributes().get("readBinding");
            MethodBinding mb = context.getApplication().createMethodBinding(binding, new Class[] { ActionEvent.class, Long.class });
            tree = (ITree) mb.invoke(context, new Object[] { new ActionEvent(this), id });
        }
        if (tree==null) throw new IOException("Can't find conversation thread!");
        
        String nodeIdStr = (String) requestParameterMap.get(clientId + "_nodeId");
        if (nodeIdStr==null || "".equals(nodeIdStr)) nodeIdStr = ""+tree.getRoot().getId();
        Long nodeId = new Long(nodeIdStr);
        currentNode = tree.findNode(nodeId);
        
        String toneStr = (String) requestParameterMap.get(clientId + "_toneId");
        if (toneStr==null || "".equals(toneStr)) toneStr = "0";
        toneId = Integer.parseInt(toneStr);
        
        String replyStr = (String) requestParameterMap.get(clientId + "_reply");
        if ( (toneId==1 || toneId==2 || toneId==3) && replyStr!=null && !"".equals(replyStr) ) {
            String binding = (String) getAttributes().get("writeBinding");
            MethodBinding mb = context.getApplication().createMethodBinding(binding, new Class[] { ActionEvent.class, Long.class, Integer.class, String.class });
            currentNode = (INode) mb.invoke(context, new Object[] { new ActionEvent(this), nodeId, new Integer(toneId), replyStr });
            toneId = 0;
        }
        
        return;
    }//encodeBegin()
    
    
    public void encodeEnd(FacesContext context) throws IOException {
        
        if (title==null || content==null || "".equals(title) || "".equals(content)) {
            throw new IOException("property 'title' and 'contnet' must be specified.");
        }

        ResponseWriter writer = context.getResponseWriter();
        
        try {
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "70%", null);
            
            //tree's title
            writer.startElement("span", null);
            writer.writeAttribute("style", "", null);
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            
            writer.writeAttribute("onClick", getOnClick(tree.getId(), null, 0), null);
            writer.writeText(BeanUtils.getProperty(tree, title), null);
            writer.endElement("a");
            writer.writeText(" —", null);
            writer.writeText(BeanUtils.getNestedProperty(tree.getRoot(), username), null);
            writer.writeText(" ("+tree.getNodesCount()+" messages)", null);
            writer.endElement("span");
            writer.writeText("\n", null);
            
            encodeFoldingNodeStart(writer, currentNode);
            encodeFoldingNodeEnd(writer, currentNode);
            
            writer.endElement("td");
            writer.startElement("td", null);
            writer.writeAttribute("height", "100%", null);

            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "5", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("height", "60%", null);

            //encode Conbar
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "2", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            encodeConbar(writer, currentNode);
            writer.endElement("table");
            
            writer.endElement("td");
            writer.endElement("tr");
            writer.startElement("tr", null);
            writer.startElement("td", null);

            //encode Focus
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "5", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            encodeFocus(writer, currentNode);
            writer.endElement("table");

            writer.endElement("td");
            writer.endElement("tr");
            writer.endElement("table");

            writer.endElement("td");
            writer.endElement("tr");
            writer.endElement("table");

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
            writer.writeAttribute("value", currentNode.getId(), null);
            writer.endElement("input");
            writer.writeText("\n", null);
            //----------
            writer.startElement("input", null);
            writer.writeAttribute("type", "hidden", null);
            writer.writeAttribute("name", clientId+"_toneId", null);
            writer.writeAttribute("value", ""+toneId, null);
            writer.endElement("input");
            writer.writeText("\n", null);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeEnd()
    

    private void encodeFocus(ResponseWriter writer, INode node) throws Exception {
        writer.startElement("tr", null);
        writer.startElement("td", null);
        writer.writeAttribute("class", "focus", null);
        writer.startElement("div", null);
        writer.writeText(BeanUtils.getNestedProperty(node, username), null);
        writer.writeText(":", null);
        writer.startElement("br", null);
        writer.endElement("br");
        writer.writeText(BeanUtils.getNestedProperty(node, content), null);
        writer.endElement("div");
        writer.endElement("td");
        writer.endElement("tr");

        if (toneId==0) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "submit", null);
            writer.writeAttribute("onClick", getOnClick(tree.getId(), node.getId(), 1), null);
            writer.writeAttribute("value", ".", null);
            writer.endElement("input");
            writer.startElement("input", null);
            writer.writeAttribute("type", "submit", null);
            writer.writeAttribute("onClick", getOnClick(tree.getId(), node.getId(), 2), null);
            writer.writeAttribute("value", "?", null);
            writer.endElement("input");
            writer.startElement("input", null);
            writer.writeAttribute("type", "submit", null);
            writer.writeAttribute("onClick", getOnClick(tree.getId(), node.getId(), 3), null);
            writer.writeAttribute("value", "!", null);
            writer.endElement("input");
            writer.endElement("td");
            writer.endElement("tr");
        } else {
            String[] punctuations = new String[] {".", "?", "!"};
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("class", "focus", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("value", punctuations[toneId-1], null);
            writer.endElement("input");
            writer.startElement("br", null);
            writer.endElement("br");
            writer.startElement("textarea", null);
            writer.writeAttribute("name", clientId+"_reply", null);
            writer.endElement("textarea");
            writer.startElement("br", null);
            writer.endElement("br");
            writer.startElement("span", null);
            writer.writeAttribute("style", "float: right;", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "submit", null);
            writer.writeAttribute("value", "Submit", null);
            writer.writeAttribute("onClick", getOnClick(tree.getId(), node.getId(), toneId), null);
            writer.endElement("input");
            writer.endElement("span");
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//encodeFocus()


    private void encodeConbar(ResponseWriter writer, INode node) throws Exception {
        INode parent = node.getParent();
        if (parent!=null) {
            encodeConbar(writer, parent);
        }
        
        writer.startElement("tr", null);
        writer.startElement("td", null);
        if (node==currentNode) {
            writer.writeAttribute("class", "conbar1", null);
        } else {
            writer.writeAttribute("class", "conbar", null);
        }
        writer.writeText("▉ ", null);
        writer.startElement("a", null);
        writer.writeAttribute("onClick", getOnClick(tree.getId(), node.getId(), 0), null);
        writer.writeText(getShortContent(node), null);
        writer.endElement("a");
        writer.endElement("td");
        writer.endElement("tr");
    }//encodeConbar()


    /**
     * Recursively encode a node
     * @param writer
     * @param node
     * @param isTreeMapRoot
     * @param empty
     */
    private void encodeTreeMapNode(ResponseWriter writer, INode node, boolean isTreeMapRoot, boolean empty) throws Exception {
        currentDepth++;
        Set children = node.getChildren();

        if (children.size()>0) {
            writer.startElement("table", null);
            if (isTreeMapRoot) {
                writer.writeAttribute("cellpadding", "0", null);
                writer.writeAttribute("cellspacing", "0", null);
                writer.writeAttribute("width", "100%", null);
            } else {
                writer.writeAttribute("cellpadding", "0", null);
                writer.writeAttribute("cellspacing", "0", null);
                writer.writeAttribute("width", "100%", null);
            }
            writer.startElement("tr", null);
            writer.writeAttribute("class", "subtree", null);
            writer.writeAttribute("valign", "baseline", null);
            writer.startElement("td", null);
            writer.writeAttribute("bgcolor", getColor(), null);
            if (children.size()>1) {
                writer.writeAttribute("colspan", ""+children.size(), null);
            }
            writer.writeAttribute("style", "border-bottom:solid 1px #8CACBB;", null);
        } else {
            if (currentDepth>depth) {
                writer.writeAttribute("class", "empty reply", null);
            }
        }
        
        if (currentDepth>depth) {
            writer.startElement("a", null);
            writer.writeAttribute("class", "clickable empty", null);
            writer.writeAttribute("onClick", getOnClick(tree.getId(), node.getId(), 0), null);
            writer.startElement("span", null);
            writer.write("&nbsp;");
            writer.endElement("span");
            writer.endElement("a");
        } else {
            if (children.size()==0) {
                writer.writeAttribute("bgcolor", getColor(), null);
            }
            writer.startElement("span", null);
            writer.writeAttribute("class", "clickable", null);
            writer.writeAttribute("onClick", getOnClick(tree.getId(), node.getId(), 0), null);
            
            writer.writeText(BeanUtils.getNestedProperty(node, content), null);
            writer.startElement("span", null);
            writer.writeAttribute("class", "author", null);
            writer.writeText(" —", null);
            writer.writeText(BeanUtils.getNestedProperty(node, username), null);
            writer.endElement("span");
            writer.endElement("span");
        }
        
        if (children.size()>0) {
            writer.endElement("td");
            writer.endElement("tr");
            writer.startElement("tr", null);
            writer.writeAttribute("class", "subtree", null);
            writer.writeAttribute("valign", "baseline", null);
            
            for (Iterator iter = children.iterator(); iter.hasNext(); ) {
                INode one = (INode) iter.next();

                writer.startElement("td", null);
                writer.writeAttribute("bgcolor", getColor(), null);
                if (iter.hasNext()) {
                    writer.writeAttribute("style", "border-right:solid 1px #8CACBB;", null);
                }

                encodeTreeMapNode(writer, one, false, false);

                writer.endElement("td");
            }//for iter

            writer.endElement("tr");
        }
        
        if (children.size()>0) {
            writer.endElement("table");
        }
        
        currentDepth--;
    }//encodeTreeMapNode()
    
    
    private String getColor() {
        String color = colors[nextColor];
        nextColor++;
        if (nextColor==colors.length) nextColor = 0;
        return color;
    }//getColor()

    
    /**
     * Recursively encode the start of a folding node
     * @param writer
     * @param node
     * @throws Exception
     */
    public void encodeFoldingNodeStart(ResponseWriter writer, INode node) throws Exception {
        INode parent = node.getParent();
        if (parent!=null) {
            encodeFoldingNodeStart(writer, parent); //recursive
        }
        
        if (node==currentNode) {
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            writer.startElement("tr", null);
            
            writer.startElement("td", null);
            writer.writeAttribute("class", "arrow", null);
            writer.writeText("►", null);
            writer.endElement("td");

            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);

            encodeTreeMapNode(writer, currentNode, true, false);

            writer.endElement("td");
            writer.endElement("tr");
            writer.startElement("table", null);
            
            return;
        } else {
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            writer.startElement("tr", null);
            if (parent!=null) {
                writer.startElement("td", null);
                writer.writeAttribute("class", "arrow", null);
                writer.writeText("►", null);
                writer.endElement("td");
            }
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            writer.writeAttribute("class", "context", null);
            
            writer.startElement("a", null);
            writer.writeAttribute("onClick", getOnClick(tree.getId(), node.getId(), 0), null);
            writer.writeText(getShortContent(node), null);
            writer.endElement("a");
            writer.startElement("span", null);
            writer.writeAttribute("class", "author", null);
            writer.writeText(" —", null);
            writer.writeText(BeanUtils.getNestedProperty(node, username), null);
            writer.endElement("span");
            writer.endElement("td");
            writer.endElement("tr");
            
            writer.startElement("tr", null);
            if (parent!=null) {
                writer.startElement("td", null);
                writer.writeAttribute("class", "arrow", null);
                writer.write("&nbsp;");
                writer.endElement("td");
            }
            writer.startElement("td", null);
        }
        
    }//encodeFoldingNodeStart()
    
    
    private void encodeFoldingNodeEnd(ResponseWriter writer, INode node) throws Exception {
        INode parent = node.getParent();
        if (parent!=null) {
            encodeFoldingNodeEnd(writer, parent);
        }
        
        writer.endElement("td");
        writer.endElement("tr");
        writer.endElement("table");
    }//encodeFoldingNodeEnd()


    public String getOnClick(Long treeId, Long nodeId, int toneId) {
        String theTreeId = "";
        if (treeId!=null) theTreeId = treeId.toString();
        String theNodeId = "";
        if (nodeId!=null) theNodeId = nodeId.toString();

        StringBuffer sb = new StringBuffer();
        sb.append("document.forms[");
        sb.append("'").append(formClientId).append("'");
        sb.append("]['");
        sb.append(clientId).append("_treeId");
        sb.append("'].value='");
        sb.append(theTreeId);
        sb.append("';");
        sb.append("document.forms[");
        sb.append("'").append(formClientId).append("'");
        sb.append("]['");
        sb.append(clientId).append("_nodeId");
        sb.append("'].value='");
        sb.append(theNodeId);
        sb.append("';");
        sb.append("document.forms[");
        sb.append("'").append(formClientId).append("'");
        sb.append("]['");
        sb.append(clientId).append("_toneId");
        sb.append("'].value='");
        sb.append(toneId);
        sb.append("';");
        sb.append("document.forms[");
        sb.append("'");
        sb.append(formClientId);
        sb.append("'");
        sb.append("].submit()");
        
        return sb.toString();
    }//getOnClick()
    
    
    public String getShortContent(INode node) throws Exception {
        String s = BeanUtils.getNestedProperty(node, content);
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
