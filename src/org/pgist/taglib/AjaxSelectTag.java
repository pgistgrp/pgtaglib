package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import com.sun.faces.util.Util;

public class AjaxSelectTag extends UIComponentTag {

    
    private String style;
    private String styleClass;
    private String key;
    private String label;
    private String value;
    private String initValue;
    private String imagePath;
    private String ajax;

    
    public AjaxSelectTag() {
    }


    public void setStyle(String style) {
        this.style = style;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public void setAjax(String ajax) {
        this.ajax = ajax;
    }


    public String getRendererType() {
        return null;
    }
    

    public String getComponentType() {
        return "org.pgist.faces.AjaxSelect";
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (style != null) {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(style);
            component.setValueBinding("style", vb);
        }
        if (styleClass != null) component.getAttributes().put("styleClass", styleClass);
        if (key != null) component.getAttributes().put("key", key);
        if (label != null) component.getAttributes().put("label", label);
        if (value != null) {
            javax.faces.el.ValueBinding vb = Util.getValueBinding(value);
            component.setValueBinding("value", vb);
        }
        if (initValue != null) {
            javax.faces.el.ValueBinding vb = Util.getValueBinding(initValue);
            component.setValueBinding("initValue", vb);
        }
        if (imagePath != null) component.getAttributes().put("imagePath", imagePath);
        if (ajax != null) component.getAttributes().put("ajax", ajax);
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
    
    
}//class AjaxSelectTag
