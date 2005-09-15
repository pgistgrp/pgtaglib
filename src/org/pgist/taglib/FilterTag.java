package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import com.sun.faces.util.Util;

/**
 * 
 * @author kenny
 *
 */
public class FilterTag extends UIComponentTag {

    
    private String style;
    private String styleClass;

    
    public FilterTag() {
    }

    
    public void setStyle(String style) {
        this.style = style;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    
    public String getRendererType() {
        return "org.pgist.faces.Filter";
    }

    
    public String getComponentType() {
        return "org.pgist.faces.Filter";
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (styleClass != null) {
            if (UIComponentTag.isValueReference(styleClass)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(styleClass);
                component.setValueBinding("styleClass", vb);
            } else {
                component.getAttributes().put("styleClass", styleClass);
            }
        }
        if (style != null) {
            if (UIComponentTag.isValueReference(style)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(style);
                component.setValueBinding("style", vb);
            } else {
                component.getAttributes().put("style", style);
            }
        }
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


}//class FilterTag
