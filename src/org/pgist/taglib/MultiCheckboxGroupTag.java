package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import com.sun.faces.util.Util;

public class MultiCheckboxGroupTag extends UIComponentTag {

    
    public MultiCheckboxGroupTag() {
    }
    

    private String first;

    private String rows;

    private String value;

    private String _var;

    private String bgcolor;

    private String border;

    private String cellpadding;

    private String cellspacing;

    private String columnClasses;

    private String dir;

    private String footerClass;

    private String frame;

    private String headerClass;

    private String lang;

    private String onclick;

    private String ondblclick;

    private String onkeydown;

    private String onkeypress;

    private String onkeyup;

    private String onmousedown;

    private String onmousemove;

    private String onmouseout;

    private String onmouseover;

    private String onmouseup;

    private String rowClasses;

    private String rules;

    private String style;

    private String styleClass;

    private String summary;

    private String title;

    private String width;

    private String columns;

    
    public void setFirst(String first) {
        this.first = first;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setVar(String _var) {
        this._var = _var;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public void setColumnClasses(String columnClasses) {
        this.columnClasses = columnClasses;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setFooterClass(String footerClass) {
        this.footerClass = footerClass;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public void setHeaderClass(String headerClass) {
        this.headerClass = headerClass;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public void setRowClasses(String rowClasses) {
        this.rowClasses = rowClasses;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getRendererType() {
        return "org.pgist.faces.MultiCheckboxGroup";
    }

    public String getComponentType() {
        return "javax.faces.Data";
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIData data = null;
        try {
            data = (UIData) component;
        } catch (ClassCastException cce) {
            throw new IllegalStateException(
                    "Component "
                            + component.toString()
                            + " not expected type.  Expected: UIData.  Perhaps you're missing a tag?");
        }
        if (first != null)
            if (UIComponentTag.isValueReference(first)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(first);
                data.setValueBinding("first", vb);
            } else {
                int _first = (new Integer(first)).intValue();
                data.setFirst(_first);
            }
        if (rows != null)
            if (UIComponentTag.isValueReference(rows)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(rows);
                data.setValueBinding("rows", vb);
            } else {
                int _rows = (new Integer(rows)).intValue();
                data.setRows(_rows);
            }
        if (value != null)
            if (UIComponentTag.isValueReference(value)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(value);
                data.setValueBinding("value", vb);
            } else {
                data.setValue(value);
            }
        data.setVar(_var);
        if (bgcolor != null)
            if (UIComponentTag.isValueReference(bgcolor)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(bgcolor);
                data.setValueBinding("bgcolor", vb);
            } else {
                data.getAttributes().put("bgcolor", bgcolor);
            }
        if (border != null)
            if (UIComponentTag.isValueReference(border)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(border);
                data.setValueBinding("border", vb);
            } else {
                int _border = (new Integer(border)).intValue();
                if (_border != 0x80000000)
                    data.getAttributes().put("border", new Integer(_border));
            }
        if (cellpadding != null)
            if (UIComponentTag.isValueReference(cellpadding)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(cellpadding);
                data.setValueBinding("cellpadding", vb);
            } else {
                data.getAttributes().put("cellpadding", cellpadding);
            }
        if (cellspacing != null)
            if (UIComponentTag.isValueReference(cellspacing)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(cellspacing);
                data.setValueBinding("cellspacing", vb);
            } else {
                data.getAttributes().put("cellspacing", cellspacing);
            }
        if (columnClasses != null)
            if (UIComponentTag.isValueReference(columnClasses)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(columnClasses);
                data.setValueBinding("columnClasses", vb);
            } else {
                data.getAttributes().put("columnClasses", columnClasses);
            }
        if (dir != null)
            if (UIComponentTag.isValueReference(dir)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(dir);
                data.setValueBinding("dir", vb);
            } else {
                data.getAttributes().put("dir", dir);
            }
        if (footerClass != null)
            if (UIComponentTag.isValueReference(footerClass)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(footerClass);
                data.setValueBinding("footerClass", vb);
            } else {
                data.getAttributes().put("footerClass", footerClass);
            }
        if (frame != null)
            if (UIComponentTag.isValueReference(frame)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(frame);
                data.setValueBinding("frame", vb);
            } else {
                data.getAttributes().put("frame", frame);
            }
        if (headerClass != null)
            if (UIComponentTag.isValueReference(headerClass)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(headerClass);
                data.setValueBinding("headerClass", vb);
            } else {
                data.getAttributes().put("headerClass", headerClass);
            }
        if (lang != null)
            if (UIComponentTag.isValueReference(lang)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(lang);
                data.setValueBinding("lang", vb);
            } else {
                data.getAttributes().put("lang", lang);
            }
        if (onclick != null)
            if (UIComponentTag.isValueReference(onclick)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onclick);
                data.setValueBinding("onclick", vb);
            } else {
                data.getAttributes().put("onclick", onclick);
            }
        if (ondblclick != null)
            if (UIComponentTag.isValueReference(ondblclick)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(ondblclick);
                data.setValueBinding("ondblclick", vb);
            } else {
                data.getAttributes().put("ondblclick", ondblclick);
            }
        if (onkeydown != null)
            if (UIComponentTag.isValueReference(onkeydown)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onkeydown);
                data.setValueBinding("onkeydown", vb);
            } else {
                data.getAttributes().put("onkeydown", onkeydown);
            }
        if (onkeypress != null)
            if (UIComponentTag.isValueReference(onkeypress)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onkeypress);
                data.setValueBinding("onkeypress", vb);
            } else {
                data.getAttributes().put("onkeypress", onkeypress);
            }
        if (onkeyup != null)
            if (UIComponentTag.isValueReference(onkeyup)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onkeyup);
                data.setValueBinding("onkeyup", vb);
            } else {
                data.getAttributes().put("onkeyup", onkeyup);
            }
        if (onmousedown != null)
            if (UIComponentTag.isValueReference(onmousedown)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmousedown);
                data.setValueBinding("onmousedown", vb);
            } else {
                data.getAttributes().put("onmousedown", onmousedown);
            }
        if (onmousemove != null)
            if (UIComponentTag.isValueReference(onmousemove)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmousemove);
                data.setValueBinding("onmousemove", vb);
            } else {
                data.getAttributes().put("onmousemove", onmousemove);
            }
        if (onmouseout != null)
            if (UIComponentTag.isValueReference(onmouseout)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmouseout);
                data.setValueBinding("onmouseout", vb);
            } else {
                data.getAttributes().put("onmouseout", onmouseout);
            }
        if (onmouseover != null)
            if (UIComponentTag.isValueReference(onmouseover)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmouseover);
                data.setValueBinding("onmouseover", vb);
            } else {
                data.getAttributes().put("onmouseover", onmouseover);
            }
        if (onmouseup != null)
            if (UIComponentTag.isValueReference(onmouseup)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmouseup);
                data.setValueBinding("onmouseup", vb);
            } else {
                data.getAttributes().put("onmouseup", onmouseup);
            }
        if (rowClasses != null)
            if (UIComponentTag.isValueReference(rowClasses)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(rowClasses);
                data.setValueBinding("rowClasses", vb);
            } else {
                data.getAttributes().put("rowClasses", rowClasses);
            }
        if (rules != null)
            if (UIComponentTag.isValueReference(rules)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(rules);
                data.setValueBinding("rules", vb);
            } else {
                data.getAttributes().put("rules", rules);
            }
        if (style != null)
            if (UIComponentTag.isValueReference(style)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(style);
                data.setValueBinding("style", vb);
            } else {
                data.getAttributes().put("style", style);
            }
        if (styleClass != null)
            if (UIComponentTag.isValueReference(styleClass)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(styleClass);
                data.setValueBinding("styleClass", vb);
            } else {
                data.getAttributes().put("styleClass", styleClass);
            }
        if (summary != null)
            if (UIComponentTag.isValueReference(summary)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(summary);
                data.setValueBinding("summary", vb);
            } else {
                data.getAttributes().put("summary", summary);
            }
        if (title != null)
            if (UIComponentTag.isValueReference(title)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(title);
                data.setValueBinding("title", vb);
            } else {
                data.getAttributes().put("title", title);
            }
        if (width != null)
            if (UIComponentTag.isValueReference(width)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(width);
                data.setValueBinding("width", vb);
            } else {
                data.getAttributes().put("width", width);
            }
        if (columns != null)
            if (UIComponentTag.isValueReference(columns)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(columns);
                data.setValueBinding("columns", vb);
            } else {
                data.getAttributes().put("columns", columns);
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


}//class MultiCheckboxGroupTag
