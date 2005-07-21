package org.pgist.taglib;

import org.pgist.component.ScrollerComponent;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.webapp.UIComponentTag;

/**
 * <p> ScrollerTag is the tag handler class for <code>ScrollerComponent.
 * 
 * @author kenny
 *
 */
public class ScrollerTag extends UIComponentTag {

    
    protected String value = null;
    protected String actionListener = null;
    protected String width = null;
    protected String theme = null;

    
    public void setValue(String value) {
        this.value = value;
    }

    
    public String getComponentType() {
        return ("Scroller");
    }

    
    public String getRendererType() {
        return (null);
    }

    
    /**
     * method reference to handle an action event generated as a result of
     * clicking on a link that points a particular page in the result-set.
     */
    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }

    
    public void setWidth(String width) {
        this.width = width;
    }

    
    public void setTheme(String theme) {
        this.theme = theme;
    }

    
    public void release() {
        super.release();
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding vb =
                    getFacesContext().getApplication().
                    createValueBinding(value);
                component.setValueBinding("value", vb);
            } else {
                ((UIData) component).setValue(value);
            }
        }

        if (actionListener != null) {
            if (isValueReference(actionListener)) {
                Class args[] = { ActionEvent.class };
                MethodBinding mb = FacesContext.getCurrentInstance()
                        .getApplication().createMethodBinding(actionListener, args);
                ((ScrollerComponent) component).setActionListener(mb);
            } else {
                Object params[] = { actionListener };
                throw new javax.faces.FacesException();
            }
        }

        if (width != null) {
            if (isValueReference(width)) {
                ValueBinding vb = context.getApplication().createValueBinding(width);
                component.setValueBinding("width", vb);
            } else {
                component.getAttributes().put("width", width);
            }
        }

        if (theme != null) {
            if (isValueReference(theme)) {
                ValueBinding vb = context.getApplication().createValueBinding(theme);
                component.setValueBinding("theme", vb);
            } else {
                component.getAttributes().put("theme", theme);
            }
        }
        
    }//setProperties()
    
    
}
