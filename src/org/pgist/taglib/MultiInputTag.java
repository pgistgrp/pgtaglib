package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;


/**
 * Tag for Multiple Input
 * @author kenny
 *
 */
public class MultiInputTag extends UIComponentTag {

    
    private String styleClass;
    private String style;
    private String value;
    private String initValue;

    
    public MultiInputTag() {
    }


    public void setValue(String value) {
        this.value = value;
    }


    public void setStyle(String style) {
        this.style = style;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }


    public String getRendererType() {
        return null;
    }

    public String getComponentType() {
        return "org.pgist.faces.MultiInput";
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (value != null) {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(value);
            component.setValueBinding("value", vb);
        }
        if (initValue != null) {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(initValue);
            component.setValueBinding("initValue", vb);
        }
        if (style != null) component.getAttributes().put("style", style);
        if (styleClass != null) component.getAttributes().put("styleClass", styleClass);
    }//setProperties()
    

    public int doStartTag() throws JspException {
        int rc = 0;
        try {
            rc = super.doStartTag();
        } catch (JspException e) {
            throw e;
        } catch (Throwable t) {
            throw new JspException(t);
        }
        return rc;
    }
    

    public int doEndTag() throws JspException {
        int rc = 0;
        try {
            rc = super.doEndTag();
        } catch (JspException e) {
            throw e;
        } catch (Throwable t) {
            throw new JspException(t);
        }
        return rc;
    }
    

    public String getDebugString() {
        String result = "id: " + getId() + " class: " + getClass().getName();
        return result;
    }


}//class MultiInputTag
