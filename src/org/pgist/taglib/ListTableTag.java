package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * <p>DataRepeaterTag is the tag handler class for a <code>UIData</code>
 * component associated with a <code>RepeaterRenderer</code>.</p>
 */

public class ListTableTag extends UIComponentTag {


    // -------------------------------------------------------------- Attributes


    private String value = null;


    public void setValue(String value) {
        this.value = value;
    }


    private String var = null;


    public void setVar(String var) {
        this.var = var;
    }


    private String width = null;

    
    public void setWidth(String width) {
        this.width = width;
    }


    // -------------------------------------------------- UIComponentTag Methods


    public String getComponentType() {
        return ("javax.faces.Data");
    }


    public String getRendererType() {
        return ("ListTable");
    }


    public void release() {
        super.release();
        value = null;
        var = null;
    }


    protected void setProperties(UIComponent component) {

        super.setProperties(component);

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

        if (var != null) {
            if (isValueReference(var)) {
                ValueBinding vb =
                    getFacesContext().getApplication().
                    createValueBinding(var);
                component.setValueBinding("var", vb);
            } else {
                ((UIData) component).setVar(var);
            }
        }

        if (width != null) {
            if (isValueReference(width)) {
                ValueBinding vb =
                    getFacesContext().getApplication().
                    createValueBinding(width);
                component.setValueBinding("width", vb);
            } else {
                component.getAttributes().put("width", width);
            }
        }

    }//setProperties()


}
