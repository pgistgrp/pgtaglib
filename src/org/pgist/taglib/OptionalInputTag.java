package org.pgist.taglib;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import com.sun.faces.util.Util;


/**
 * 
 * @author kenny
 *
 */
public class OptionalInputTag extends UIComponentTag {

    
    private String secret;
    private String converter;
    private String immediate;
    private String required;
    private String validator;
    private String value;
    private String valueChangeListener;
    private String accesskey;
    private String alt;
    private String dir;
    private String disabled;
    private String lang;
    private String maxlength;
    private String onblur;
    private String onchange;
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
    private String onselect;
    private String readonly;
    private String redisplay;
    private String size;
    private String style;
    private String styleClass;
    private String tabindex;
    private String title;

    
    public OptionalInputTag() {
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
    

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValueChangeListener(String valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
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

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public void setRedisplay(String redisplay) {
        this.redisplay = redisplay;
    }

    public void setSize(String size) {
        this.size = size;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRendererType() {
        return "org.pgist.faces.OptionalInput";
    }

    public String getComponentType() {
        return "org.pgist.faces.OptionalInput";
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIInput input = null;
        try {
            input = (UIInput) component;
        } catch (ClassCastException cce) {
            throw new IllegalStateException(
                "Component "+ component.toString()
              + " not expected type.  Expected: UIInput.  Perhaps you're missing a tag?");
        }
        if (secret != null) {
            component.getAttributes().put("secret", secret);
        }
        if (converter != null)
            if (UIComponentTag.isValueReference(converter)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(converter);
                input.setValueBinding("converter", vb);
            } else {
                javax.faces.convert.Converter _converter = FacesContext
                    .getCurrentInstance().getApplication().createConverter(converter);
                input.setConverter(_converter);
            }
        if (immediate != null)
            if (UIComponentTag.isValueReference(immediate)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(immediate);
                input.setValueBinding("immediate", vb);
            } else {
                boolean _immediate = (new Boolean(immediate)).booleanValue();
                input.setImmediate(_immediate);
            }
        if (required != null)
            if (UIComponentTag.isValueReference(required)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(required);
                input.setValueBinding("required", vb);
            } else {
                boolean _required = (new Boolean(required)).booleanValue();
                input.setRequired(_required);
            }
        if (validator != null)
            if (UIComponentTag.isValueReference(validator)) {
                Class args[] = { javax.faces.context.FacesContext.class,
                        javax.faces.component.UIComponent.class,
                        java.lang.Object.class };
                javax.faces.el.MethodBinding vb = FacesContext
                        .getCurrentInstance().getApplication()
                        .createMethodBinding(validator, args);
                input.setValidator(vb);
            } else {
                Object params[] = { validator };
                throw new FacesException(Util.getExceptionMessageString(
                        "com.sun.faces.INVALID_EXPRESSION", params));
            }
        if (value != null)
            if (UIComponentTag.isValueReference(value)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(value);
                input.setValueBinding("value", vb);
            } else {
                input.setValue(value);
            }
        if (valueChangeListener != null)
            if (UIComponentTag.isValueReference(valueChangeListener)) {
                Class args[] = { javax.faces.event.ValueChangeEvent.class };
                javax.faces.el.MethodBinding vb = FacesContext
                        .getCurrentInstance().getApplication()
                        .createMethodBinding(valueChangeListener, args);
                input.setValueChangeListener(vb);
            } else {
                Object params[] = { valueChangeListener };
                throw new FacesException(Util.getExceptionMessageString(
                        "com.sun.faces.INVALID_EXPRESSION", params));
            }
        if (accesskey != null)
            if (UIComponentTag.isValueReference(accesskey)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(accesskey);
                input.setValueBinding("accesskey", vb);
            } else {
                input.getAttributes().put("accesskey", accesskey);
            }
        if (alt != null)
            if (UIComponentTag.isValueReference(alt)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(alt);
                input.setValueBinding("alt", vb);
            } else {
                input.getAttributes().put("alt", alt);
            }
        if (dir != null)
            if (UIComponentTag.isValueReference(dir)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(dir);
                input.setValueBinding("dir", vb);
            } else {
                input.getAttributes().put("dir", dir);
            }
        if (disabled != null)
            if (UIComponentTag.isValueReference(disabled)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(disabled);
                input.setValueBinding("disabled", vb);
            } else {
                boolean _disabled = (new Boolean(disabled)).booleanValue();
                input.getAttributes().put("disabled",
                        _disabled ? ((Object) (Boolean.TRUE)) : ((Object) (Boolean.FALSE)));
            }
        if (lang != null)
            if (UIComponentTag.isValueReference(lang)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(lang);
                input.setValueBinding("lang", vb);
            } else {
                input.getAttributes().put("lang", lang);
            }
        if (maxlength != null)
            if (UIComponentTag.isValueReference(maxlength)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(maxlength);
                input.setValueBinding("maxlength", vb);
            } else {
                int _maxlength = (new Integer(maxlength)).intValue();
                if (_maxlength != 0x80000000)
                    input.getAttributes().put("maxlength",new Integer(_maxlength));
            }
        if (onblur != null)
            if (UIComponentTag.isValueReference(onblur)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onblur);
                input.setValueBinding("onblur", vb);
            } else {
                input.getAttributes().put("onblur", onblur);
            }
        if (onchange != null)
            if (UIComponentTag.isValueReference(onchange)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onchange);
                input.setValueBinding("onchange", vb);
            } else {
                input.getAttributes().put("onchange", onchange);
            }
        if (onclick != null)
            if (UIComponentTag.isValueReference(onclick)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onclick);
                input.setValueBinding("onclick", vb);
            } else {
                input.getAttributes().put("onclick", onclick);
            }
        if (ondblclick != null)
            if (UIComponentTag.isValueReference(ondblclick)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(ondblclick);
                input.setValueBinding("ondblclick", vb);
            } else {
                input.getAttributes().put("ondblclick", ondblclick);
            }
        if (onfocus != null)
            if (UIComponentTag.isValueReference(onfocus)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onfocus);
                input.setValueBinding("onfocus", vb);
            } else {
                input.getAttributes().put("onfocus", onfocus);
            }
        if (onkeydown != null)
            if (UIComponentTag.isValueReference(onkeydown)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onkeydown);
                input.setValueBinding("onkeydown", vb);
            } else {
                input.getAttributes().put("onkeydown", onkeydown);
            }
        if (onkeypress != null)
            if (UIComponentTag.isValueReference(onkeypress)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onkeypress);
                input.setValueBinding("onkeypress", vb);
            } else {
                input.getAttributes().put("onkeypress", onkeypress);
            }
        if (onkeyup != null)
            if (UIComponentTag.isValueReference(onkeyup)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onkeyup);
                input.setValueBinding("onkeyup", vb);
            } else {
                input.getAttributes().put("onkeyup", onkeyup);
            }
        if (onmousedown != null)
            if (UIComponentTag.isValueReference(onmousedown)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onmousedown);
                input.setValueBinding("onmousedown", vb);
            } else {
                input.getAttributes().put("onmousedown", onmousedown);
            }
        if (onmousemove != null)
            if (UIComponentTag.isValueReference(onmousemove)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onmousemove);
                input.setValueBinding("onmousemove", vb);
            } else {
                input.getAttributes().put("onmousemove", onmousemove);
            }
        if (onmouseout != null)
            if (UIComponentTag.isValueReference(onmouseout)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onmouseout);
                input.setValueBinding("onmouseout", vb);
            } else {
                input.getAttributes().put("onmouseout", onmouseout);
            }
        if (onmouseover != null)
            if (UIComponentTag.isValueReference(onmouseover)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onmouseover);
                input.setValueBinding("onmouseover", vb);
            } else {
                input.getAttributes().put("onmouseover", onmouseover);
            }
        if (onmouseup != null)
            if (UIComponentTag.isValueReference(onmouseup)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onmouseup);
                input.setValueBinding("onmouseup", vb);
            } else {
                input.getAttributes().put("onmouseup", onmouseup);
            }
        if (onselect != null)
            if (UIComponentTag.isValueReference(onselect)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(onselect);
                input.setValueBinding("onselect", vb);
            } else {
                input.getAttributes().put("onselect", onselect);
            }
        if (readonly != null)
            if (UIComponentTag.isValueReference(readonly)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(readonly);
                input.setValueBinding("readonly", vb);
            } else {
                boolean _readonly = (new Boolean(readonly)).booleanValue();
                input.getAttributes().put(
                        "readonly",
                        _readonly ? ((Object) (Boolean.TRUE))
                                : ((Object) (Boolean.FALSE)));
            }
        if (redisplay != null)
            if (UIComponentTag.isValueReference(redisplay)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(redisplay);
                input.setValueBinding("redisplay", vb);
            } else {
                boolean _redisplay = (new Boolean(redisplay)).booleanValue();
                input.getAttributes().put(
                        "redisplay",
                        _redisplay ? ((Object) (Boolean.TRUE))
                                : ((Object) (Boolean.FALSE)));
            }
        if (size != null)
            if (UIComponentTag.isValueReference(size)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(size);
                input.setValueBinding("size", vb);
            } else {
                int _size = (new Integer(size)).intValue();
                if (_size != 0x80000000)
                    input.getAttributes().put("size", new Integer(_size));
            }
        if (style != null)
            if (UIComponentTag.isValueReference(style)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(style);
                input.setValueBinding("style", vb);
            } else {
                input.getAttributes().put("style", style);
            }
        if (styleClass != null)
            if (UIComponentTag.isValueReference(styleClass)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(styleClass);
                input.setValueBinding("styleClass", vb);
            } else {
                input.getAttributes().put("styleClass", styleClass);
            }
        if (tabindex != null)
            if (UIComponentTag.isValueReference(tabindex)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(tabindex);
                input.setValueBinding("tabindex", vb);
            } else {
                input.getAttributes().put("tabindex", tabindex);
            }
        if (title != null)
            if (UIComponentTag.isValueReference(title)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(title);
                input.setValueBinding("title", vb);
            } else {
                input.getAttributes().put("title", title);
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


}//class OptionalInputTag
