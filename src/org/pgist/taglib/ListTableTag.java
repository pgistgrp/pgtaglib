package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

import org.pgist.util.PageSetting;

/**
 * <p>DataRepeaterTag is the tag handler class for a <code>UIData</code>
 * component associated with a <code>RepeaterRenderer</code>.</p>
 */

public class ListTableTag extends UIComponentTag {


    // -------------------------------------------------------------- Attributes


    private String value = null;
    private String var = null;
    private String width = null;
    private String rowSize = null;
    private String pageSize = null;
    private String actionBinding = null;
    private String pageSetting = null;


    public void setValue(String value) {
        this.value = value;
    }


    public void setVar(String var) {
        this.var = var;
    }


    public void setWidth(String width) {
        this.width = width;
    }


    public void setActionBinding(String actionBinding) {
        this.actionBinding = actionBinding;
    }


    public void setPageSetting(String pageSetting) {
        this.pageSetting = pageSetting;
    }


    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }


    public void setRowSize(String rowSize) {
        this.rowSize = rowSize;
    }


    public String getComponentType() {
        return ("javax.faces.Data");
    }


    public String getRendererType() {
        return ("ListTable");
    }


    // -------------------------------------------------- UIComponentTag Methods


    public void release() {
        super.release();
        value = null;
        var = null;
        width = null;
        rowSize = null;
        pageSize = null;
        pageSetting = null;
        actionBinding = null;
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
                //vb.getValue(context);
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

        if (rowSize != null) {
            if (isValueReference(rowSize)) {
                ValueBinding vb = context.getApplication().createValueBinding(rowSize);
                component.setValueBinding("rowSize", vb);
            } else {
                component.getAttributes().put("rowSize", rowSize);
            }
        }

        if (pageSize != null) {
            if (isValueReference(pageSize)) {
                ValueBinding vb = context.getApplication().createValueBinding(pageSize);
                component.setValueBinding("pageSize", vb);
            } else {
                component.getAttributes().put("pageSize", pageSize);
            }
        }

        if (pageSetting != null) {
            if (isValueReference(pageSetting)) {
                ValueBinding vb = getFacesContext().getApplication().createValueBinding(pageSetting);
                component.setValueBinding("pageSetting", vb);
                
                PageSetting setting = (PageSetting)vb.getValue(context);

                if (rowSize==null || "".equals(rowSize)) {
                    rowSize = (String) getComponentInstance().getAttributes().get("rowSize");
                    if (rowSize==null || "".equals(rowSize)) rowSize = "10";
                }
                int m = Integer.parseInt(rowSize);
                if (m<1) m = 10;
                setting.setRowOfPage(m);

                if (pageSize==null || "".equals(pageSize)) {
                    pageSize = (String) getComponentInstance().getAttributes().get("pageSize");
                    if (pageSize==null || "".equals(pageSize)) pageSize = "10";
                }
                m = Integer.parseInt(pageSize);
                if (m<1) m = 10;
                setting.setPageOfScreen(m);
            } else {
                component.getAttributes().put("pageSetting", pageSetting);
            }
        }
        
        if (actionBinding != null) {
            component.getAttributes().put("actionBinding", actionBinding);
        }

    }//setProperties()


}//class ListTableTag
