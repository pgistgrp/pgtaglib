package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;


/**
 * Tag for ShowList
 * @author kenny
 *
 */
public class ShowListTag extends UIComponentTag {

    
    private String styleClass;
    private String value;
    private String field;

    
    public ShowListTag() {
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public void setField(String field) {
        this.field = field;
    }


    public String getRendererType() {
        return null;
    }

    public String getComponentType() {
        return "org.pgist.faces.ShowList";
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (value != null) {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(value);
            component.setValueBinding("value", vb);
        }
        if (styleClass != null) component.getAttributes().put("styleClass", styleClass);
        if (field != null) component.getAttributes().put("field", field);
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
    

}//class ShowListTag

