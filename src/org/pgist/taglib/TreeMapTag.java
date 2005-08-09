package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;


/**
 * Tag for tree map
 * @author kenny
 *
 */
public class TreeMapTag extends UIComponentTag {

    
    protected String styleClass = null;
    protected String depth = null;
    protected String actionBinding = null;

    
    public String getComponentType() {
        return ("org.pgist.faces.TreeMap");
    }


    public String getRendererType() {
        return (null);
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void setDepth(String depth) {
        this.depth = depth;
    }
    

    public void setactionBinding(String actionBinding) {
        this.actionBinding = actionBinding;
    }


    public void release() {
        super.release();
        styleClass = "";
        depth = "";
        actionBinding = "";
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

        if (depth != null) {
            if (isValueReference(depth)) {
                ValueBinding vb = context.getApplication().createValueBinding(depth);
                component.setValueBinding("depth", vb);
            } else {
                component.getAttributes().put("depth", depth);
            }
        }

        if (actionBinding != null) {
            if (actionBinding != null) {
                component.getAttributes().put("actionBinding", actionBinding);
            }
            /*
            if (isValueReference(actionBinding)) {
                ValueBinding vb = context.getApplication().createValueBinding(actionBinding);
                component.setValueBinding("actionBinding", vb);
            } else {
                component.getAttributes().put("actionBinding", actionBinding);
            }
            */
        }
    }//setProperties()
    

}//class TreeMapTag
