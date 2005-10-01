package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;


/**
 * Tag for Discourse Layout.
 * @author kenny
 *
 */
public class DoLayoutTag extends UIComponentTag {

    
    protected String styleClass = null;
    protected String tree = null;
    protected String node = null;
    protected String key = null;
    protected String label = null;

    
    public String getComponentType() {
        return "org.pgist.faces.Discourse";
    }


    public String getRendererType() {
        return "org.pgist.faces.doLayout";
    }


    public void setNode(String node) {
        this.node = node;
    }


    public void setTree(String tree) {
        this.tree = tree;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void release() {
        super.release();
        styleClass = "";
        tree = "";
        node = "";
        key = "";
        label = "";
    }

    
    protected void setProperties(UIComponent component) {
        
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = context.getApplication().createValueBinding(styleClass);
                component.setValueBinding("styleClass", vb);
            } else {
                component.getAttributes().put("styleClass", styleClass);
            }
        }

        if (tree != null) {
            if (isValueReference(tree)) {
                ValueBinding vb = context.getApplication().createValueBinding(tree);
                component.setValueBinding("tree", vb);
            } else {
                component.getAttributes().put("tree", tree);
            }
        }
        
        if (node != null) {
            if (isValueReference(node)) {
                ValueBinding vb = context.getApplication().createValueBinding(node);
                component.setValueBinding("node", vb);
            } else {
                component.getAttributes().put("node", node);
            }
        }
        
        if (key != null) {
            if (isValueReference(key)) {
                ValueBinding vb = context.getApplication().createValueBinding(key);
                component.setValueBinding("key", vb);
            } else {
                component.getAttributes().put("key", key);
            }
        }

        if (label != null) {
            if (isValueReference(label)) {
                ValueBinding vb = context.getApplication().createValueBinding(label);
                component.setValueBinding("label", vb);
            } else {
                component.getAttributes().put("label", label);
            }
        }
        
    }//setProperties()
    

}//class DoLayoutTag
