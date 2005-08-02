package org.pgist.taglib;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentBodyTag;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import com.sun.faces.util.Util;


/**
 * Tag for component ToolButton
 * @author kenny
 *
 */
public class ToolButtonTag extends UIComponentBodyTag {

    
    private String action;

    private String actionListener;

    private String immediate;

    private String value;

    private String accesskey;

    private String charset;

    private String coords;

    private String dir;

    private String hreflang;

    private String lang;

    private String onblur;

    private String onclick;

    private String ondblclick;

    private String onfocus;

    private String onkeydown;

    private String onkeypress;

    private String onkeyup;

    private String onmousedown;

    private String onmousemove;

    private String onmouseout;

    private String onmouseover;

    private String onmouseup;

    private String rel;

    private String rev;

    private String shape;

    private String style;

    private String styleClass;

    private String tabindex;

    private String target;

    private String title;

    private String type;

    private String confirm;

    
    public ToolButtonTag() {
    }
    

    public void setAction(String action) {
        this.action = action;
    }

    
    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }

    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
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

    public void setRel(String rel) {
        this.rel = rel;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }


    public String getRendererType() {
        return "org.pgist.faces.ToolButton";
    }

    public String getComponentType() {
        return "javax.faces.Command";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UICommand command = null;
        try {
            command = (UICommand) component;
        } catch (ClassCastException cce) {
            throw new IllegalStateException(
                    "Component " + component.toString()
                  + " not expected type.  Expected: UICommand.  Perhaps you're missing a tag?");
        }
        if (action != null)
            if (UIComponentTag.isValueReference(action)) {
                javax.faces.el.MethodBinding vb = FacesContext
                        .getCurrentInstance().getApplication()
                        .createMethodBinding(action, null);
                command.setAction(vb);
            } else {
                javax.faces.el.MethodBinding vb = Util
                        .createConstantMethodBinding(action);
                command.setAction(vb);
            }
        if (actionListener != null)
            if (UIComponentTag.isValueReference(actionListener)) {
                Class args[] = { javax.faces.event.ActionEvent.class };
                javax.faces.el.MethodBinding vb = FacesContext
                        .getCurrentInstance().getApplication()
                        .createMethodBinding(actionListener, args);
                command.setActionListener(vb);
            } else {
                Object params[] = { actionListener };
                throw new FacesException(Util.getExceptionMessageString(
                        "com.sun.faces.INVALID_EXPRESSION", params));
            }
        if (immediate != null)
            if (UIComponentTag.isValueReference(immediate)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(immediate);
                command.setValueBinding("immediate", vb);
            } else {
                boolean _immediate = (new Boolean(immediate)).booleanValue();
                command.setImmediate(_immediate);
            }
        if (value != null)
            if (UIComponentTag.isValueReference(value)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(value);
                command.setValueBinding("value", vb);
            } else {
                command.setValue(value);
            }
        if (accesskey != null)
            if (UIComponentTag.isValueReference(accesskey)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(accesskey);
                command.setValueBinding("accesskey", vb);
            } else {
                command.getAttributes().put("accesskey", accesskey);
            }
        if (charset != null)
            if (UIComponentTag.isValueReference(charset)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(charset);
                command.setValueBinding("charset", vb);
            } else {
                command.getAttributes().put("charset", charset);
            }
        if (coords != null)
            if (UIComponentTag.isValueReference(coords)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(coords);
                command.setValueBinding("coords", vb);
            } else {
                command.getAttributes().put("coords", coords);
            }
        if (dir != null)
            if (UIComponentTag.isValueReference(dir)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(dir);
                command.setValueBinding("dir", vb);
            } else {
                command.getAttributes().put("dir", dir);
            }
        if (hreflang != null)
            if (UIComponentTag.isValueReference(hreflang)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(hreflang);
                command.setValueBinding("hreflang", vb);
            } else {
                command.getAttributes().put("hreflang", hreflang);
            }
        if (lang != null)
            if (UIComponentTag.isValueReference(lang)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(lang);
                command.setValueBinding("lang", vb);
            } else {
                command.getAttributes().put("lang", lang);
            }
        if (onblur != null)
            if (UIComponentTag.isValueReference(onblur)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onblur);
                command.setValueBinding("onblur", vb);
            } else {
                command.getAttributes().put("onblur", onblur);
            }
        if (onclick != null)
            if (UIComponentTag.isValueReference(onclick)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onclick);
                command.setValueBinding("onclick", vb);
            } else {
                command.getAttributes().put("onclick", onclick);
            }
        if (ondblclick != null)
            if (UIComponentTag.isValueReference(ondblclick)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(ondblclick);
                command.setValueBinding("ondblclick", vb);
            } else {
                command.getAttributes().put("ondblclick", ondblclick);
            }
        if (onfocus != null)
            if (UIComponentTag.isValueReference(onfocus)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onfocus);
                command.setValueBinding("onfocus", vb);
            } else {
                command.getAttributes().put("onfocus", onfocus);
            }
        if (onkeydown != null)
            if (UIComponentTag.isValueReference(onkeydown)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onkeydown);
                command.setValueBinding("onkeydown", vb);
            } else {
                command.getAttributes().put("onkeydown", onkeydown);
            }
        if (onkeypress != null)
            if (UIComponentTag.isValueReference(onkeypress)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onkeypress);
                command.setValueBinding("onkeypress", vb);
            } else {
                command.getAttributes().put("onkeypress", onkeypress);
            }
        if (onkeyup != null)
            if (UIComponentTag.isValueReference(onkeyup)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onkeyup);
                command.setValueBinding("onkeyup", vb);
            } else {
                command.getAttributes().put("onkeyup", onkeyup);
            }
        if (onmousedown != null)
            if (UIComponentTag.isValueReference(onmousedown)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmousedown);
                command.setValueBinding("onmousedown", vb);
            } else {
                command.getAttributes().put("onmousedown", onmousedown);
            }
        if (onmousemove != null)
            if (UIComponentTag.isValueReference(onmousemove)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmousemove);
                command.setValueBinding("onmousemove", vb);
            } else {
                command.getAttributes().put("onmousemove", onmousemove);
            }
        if (onmouseout != null)
            if (UIComponentTag.isValueReference(onmouseout)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmouseout);
                command.setValueBinding("onmouseout", vb);
            } else {
                command.getAttributes().put("onmouseout", onmouseout);
            }
        if (onmouseover != null)
            if (UIComponentTag.isValueReference(onmouseover)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmouseover);
                command.setValueBinding("onmouseover", vb);
            } else {
                command.getAttributes().put("onmouseover", onmouseover);
            }
        if (onmouseup != null)
            if (UIComponentTag.isValueReference(onmouseup)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(onmouseup);
                command.setValueBinding("onmouseup", vb);
            } else {
                command.getAttributes().put("onmouseup", onmouseup);
            }
        if (rel != null)
            if (UIComponentTag.isValueReference(rel)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(rel);
                command.setValueBinding("rel", vb);
            } else {
                command.getAttributes().put("rel", rel);
            }
        if (rev != null)
            if (UIComponentTag.isValueReference(rev)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(rev);
                command.setValueBinding("rev", vb);
            } else {
                command.getAttributes().put("rev", rev);
            }
        if (shape != null)
            if (UIComponentTag.isValueReference(shape)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(shape);
                command.setValueBinding("shape", vb);
            } else {
                command.getAttributes().put("shape", shape);
            }
        if (style != null)
            if (UIComponentTag.isValueReference(style)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(style);
                command.setValueBinding("style", vb);
            } else {
                command.getAttributes().put("style", style);
            }
        if (styleClass != null)
            if (UIComponentTag.isValueReference(styleClass)) {
                javax.faces.el.ValueBinding vb = Util
                        .getValueBinding(styleClass);
                command.setValueBinding("styleClass", vb);
            } else {
                command.getAttributes().put("styleClass", styleClass);
            }
        if (tabindex != null)
            if (UIComponentTag.isValueReference(tabindex)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(tabindex);
                command.setValueBinding("tabindex", vb);
            } else {
                command.getAttributes().put("tabindex", tabindex);
            }
        if (target != null)
            if (UIComponentTag.isValueReference(target)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(target);
                command.setValueBinding("target", vb);
            } else {
                command.getAttributes().put("target", target);
            }
        if (title != null)
            if (UIComponentTag.isValueReference(title)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(title);
                command.setValueBinding("title", vb);
            } else {
                command.getAttributes().put("title", title);
            }
        if (type != null)
            if (UIComponentTag.isValueReference(type)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(type);
                command.setValueBinding("type", vb);
            } else {
                command.getAttributes().put("type", type);
            }
        if (confirm != null)
            if (UIComponentTag.isValueReference(confirm)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(confirm);
                command.setValueBinding("confirm", vb);
            } else {
                command.getAttributes().put("confirm", confirm);
            }
    }

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
        String content = null;
        try {
            if (null != (bodyContent = getBodyContent())) {
                content = bodyContent.getString();
                getPreviousOut().write(content);
            }
        } catch (IOException iox) {
            throw new JspException(iox);
        }
        int rc = super.doEndTag();
        return rc;
    }


}//class ToolButtonTag
