package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import com.sun.faces.util.Util;


/**
 * MultiSelectTag Tag
 * @author kenny
 *
 */
public class MultiSelectTag extends UIComponentTag {

    private String styleClass;
    private String value;
    private String columns;
    private String key;
    private String label;
    private String universalSet;
    private String subSet;

    
    public MultiSelectTag() {
    }


    public void setValue(String value) {
        this.value = value;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public void setColumns(String columns) {
        this.columns = columns;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public void setSubSet(String subSet) {
        this.subSet = subSet;
    }


    public void setUniversalSet(String universalSet) {
        this.universalSet = universalSet;
    }


    public String getRendererType() {
        return null;
    }

    public String getComponentType() {
        return "org.pgist.faces.MultiSelect";
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (value != null) {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(value);
            component.setValueBinding("value", vb);
        }
        if (styleClass != null) component.getAttributes().put("styleClass", styleClass);
        if (columns != null) component.getAttributes().put("columns", columns);
        if (key != null) component.getAttributes().put("key", key);
        if (label != null) component.getAttributes().put("label", label);
        if (universalSet != null) {
            javax.faces.el.ValueBinding vb = Util.getValueBinding(universalSet);
            component.setValueBinding("universalSet", vb);
        }
        if (subSet != null) {
            javax.faces.el.ValueBinding vb = Util.getValueBinding(subSet);
            component.setValueBinding("subSet", vb);
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
    

}//class MultiSelectTag
