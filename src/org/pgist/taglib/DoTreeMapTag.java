package org.pgist.taglib;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.webapp.UIComponentTag;

import org.pgist.component.UIAction;

import com.sun.faces.util.Util;

/**
 * Tag for Discourse TreeMap.
 * @author kenny
 *
 */
public class DoTreeMapTag extends UIComponentTag {

    
    protected String styleClass = null;
    protected String depth = null;
    protected String actionListener = null;

    
    public String getComponentType() {
        return "org.pgist.faces.UIAction";
    }
    

    public String getRendererType() {
        return "org.pgist.faces.doTreeMap";
    }
    

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void setDepth(String depth) {
        this.depth = depth;
    }


    public void setactionListener(String actionListener) {
        this.actionListener = actionListener;
    }


    public void release() {
        super.release();
        styleClass = "";
        depth = "";
        actionListener = "";
    }
    

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIAction cmpt = (UIAction) component;
        
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

        if (actionListener != null) {
            if(UIComponentTag.isValueReference(actionListener)) {
                Class args[] = {
                    ActionEvent.class
                };
                MethodBinding vb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(actionListener, args);
                cmpt.setActionListener(vb);
            } else {
                Object params[] = {
                    actionListener
                };
                throw new FacesException(Util.getExceptionMessageString("com.sun.faces.INVALID_EXPRESSION", params));
            }
        }

    }//setProperties()

}//class DoTreeMapTag
