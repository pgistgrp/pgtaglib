package org.pgist.renderkit;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.pgist.model.Node;
import org.pgist.model.Tree;

public class DoLayoutRenderer extends BaseRenderer {


    public void decode(FacesContext context, UIComponent component) throws NullPointerException {
        
    }//decode()
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
        
        ValueBinding treeBinding = component.getValueBinding("tree");
        ValueBinding nodeBinding = component.getValueBinding("node");
        Tree tree = (Tree) treeBinding.getValue(context);
        Node node = (Node) nodeBinding.getValue(context);
        
        ResponseWriter writer = context.getResponseWriter();
        
        String clientId = component.getClientId(context);
        
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_treeId", null);
        writer.writeAttribute("value", tree.getId(), null);
        writer.endElement("input");
        
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_nodeId", null);
        writer.writeAttribute("value", node.getId(), null);
        writer.endElement("input");
        
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_punctuate", null);
        writer.writeAttribute("value", "0", null);
        writer.endElement("input");
        
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_contenttype", null);
        writer.writeAttribute("value", "-1", null);
        writer.endElement("input");
        
        //Render the outmost table
        writer.startElement("table", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("width", "100%", null);
        
        writer.startElement("tr", null);
        
        //left side
        writer.startElement("td", null);
        writer.writeAttribute("width", "70%", null);
        //left table
        writer.startElement("table", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("width", "100%", null);
        renderHeader(context, component, writer);
        renderView(context, component, writer);
        renderUpTree(context, component, writer, treeBinding, nodeBinding);
        renderTreeMap(context, component, writer, treeBinding, nodeBinding);
        renderDownTree(context, component, writer);
        renderFooter(context, component, writer);
        writer.endElement("table");
        writer.endElement("td");
        
        //right side
        writer.startElement("td", null);
        writer.writeAttribute("width", "30%", null);
        //right table
        writer.startElement("table", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("width", "100%", null);
        renderConbar(context, component, writer, treeBinding, nodeBinding);
        renderTarget(context, component, writer, treeBinding, nodeBinding);
        renderFocus(context, component, writer, treeBinding, nodeBinding);
        writer.endElement("table");
        writer.endElement("td");
        
        writer.endElement("tr");
        writer.endElement("table");
    }//encodeBegin()


    /**
     * Render the header part
     * @param context
     * @param component
     * @param writer
     * @throws IOException
     */
    private void renderHeader(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
        UIComponent header = component.getFacet("header");
        if (header != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            encodeRecursive(context, header);
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderHeader()


    /**
     * Render the view part
     * @param context
     * @param component
     * @param writer
     * @throws IOException
     */
    private void renderView(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
        UIComponent view = component.getFacet("view");
        if (view != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            encodeRecursive(context, view);
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderView()


    /**
     * Render the uptree part
     * @param context
     * @param component
     * @param writer
     * @param node
     * @param tree
     * @throws IOException
     */
    private void renderUpTree(FacesContext context, UIComponent component, ResponseWriter writer, ValueBinding tree, ValueBinding node) throws IOException {
        UIComponent uptree = component.getFacet("uptree");

        if (uptree != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            
            uptree.setValueBinding("tree", tree);
            uptree.setValueBinding("node", node);
            uptree.getAttributes().put("_PREFIX", component.getClientId(context));
            
            encodeRecursive(context, uptree);
            
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderUpTree()

    
    /**
     * Render the treemap part
     * @param context
     * @param component
     * @param writer
     * @throws IOException
     */
    private void renderTreeMap(FacesContext context, UIComponent component, ResponseWriter writer, ValueBinding tree, ValueBinding node) throws IOException {
        UIComponent treemap = component.getFacet("treemap");
        if (treemap != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);

            treemap.setValueBinding("tree", tree);
            treemap.setValueBinding("node", node);
            treemap.getAttributes().put("_PREFIX", component.getClientId(context));
            
            encodeRecursive(context, treemap);
            
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderTreeMap()

    
    /**
     * Render the downtree part
     * @param context
     * @param component
     * @param writer
     * @throws IOException
     */
    private void renderDownTree(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
        UIComponent topScroller = component.getFacet("downtree");
        if (topScroller != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            encodeRecursive(context, topScroller);
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderDownTree()

    
    /**
     * Render the footer part
     * @param context
     * @param component
     * @param writer
     * @throws IOException
     */
    private void renderFooter(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
        UIComponent footer = component.getFacet("footer");
        if (footer != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            encodeRecursive(context, footer);
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderFooter()

    
    /**
     * Render the conbar part
     * @param context
     * @param component
     * @param writer
     * @throws IOException
     */
    private void renderConbar(FacesContext context, UIComponent component, ResponseWriter writer, ValueBinding tree, ValueBinding node) throws IOException {
        UIComponent conbar = component.getFacet("conbar");
        if (conbar != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);

            conbar.setValueBinding("tree", tree);
            conbar.setValueBinding("node", node);
            conbar.getAttributes().put("_PREFIX", component.getClientId(context));
            
            encodeRecursive(context, conbar);

            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderConbar()

    
    /**
     * Render the target part
     * @param context
     * @param component
     * @param writer
     * @throws IOException
     */
    private void renderTarget(FacesContext context, UIComponent component, ResponseWriter writer, ValueBinding tree, ValueBinding node) throws IOException {
        UIComponent target = component.getFacet("target");
        if (target != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);

            target.setValueBinding("tree", tree);
            target.setValueBinding("node", node);
            target.getAttributes().put("_PREFIX", component.getClientId(context));

            encodeRecursive(context, target);
            
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderTarget()

    
    /**
     * Render the focus part
     * @param context
     * @param component
     * @param writer
     * @throws IOException
     */
    private void renderFocus(FacesContext context, UIComponent component, ResponseWriter writer, ValueBinding tree, ValueBinding node) throws IOException {
        UIComponent focus = component.getFacet("focus");
        if (focus != null) {
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);

            focus.setValueBinding("tree", tree);
            focus.setValueBinding("node", node);
            focus.getAttributes().put("_PREFIX", component.getClientId(context));

            encodeRecursive(context, focus);
            
            writer.endElement("td");
            writer.endElement("tr");
        }
    }//renderFocus()

    
    protected UIForm getMyForm(FacesContext context, UIComponent component) {
        UIComponent parent;
        for(parent = component.getParent(); parent != null; parent = parent.getParent()) {
            if(parent instanceof UIForm) break;
        }

        return (UIForm)parent;
    }//getMyForm()
    
    
}//class DoLayoutRenderer
