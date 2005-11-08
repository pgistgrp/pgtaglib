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
 * Tag for Discourse Conbar.
 * @author kenny
 *
 */
public class DoConbarTag extends UIComponentTag {

    
    protected String styleClass = null;
    protected String actionListener = null;
    protected String logo = null;

    
    public String getComponentType() {
        return "org.pgist.faces.UIAction";
    }
    

    public String getRendererType() {
        return "org.pgist.faces.doConbar";
    }
    

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }


    public void setLogo(String logo) {
        this.logo = logo;
    }


    public void release() {
        super.release();
        styleClass = "";
        actionListener = "";
        logo = "";
    }
    

    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();
        UIAction cmpt = (UIAction) component;

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = context.getApplication().createValueBinding(styleClass);
                component.setValueBinding("styleClass", vb);
            } else {
                component.getAttributes().put("styleClass", styleClass);
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

        if (logo != null) {
            if (isValueReference(logo)) {
                ValueBinding vb = context.getApplication().createValueBinding(logo);
                component.setValueBinding("logo", vb);
            } else {
                component.getAttributes().put("logo", logo);
            }
        }
    }//setProperties()
    

}//class DoConbarTag
