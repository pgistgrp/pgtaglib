package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * <p>
 * ScrollerTag is the tag handler class for <code>ScrollerComponent.
 * 
 * @author kenny
 *
 */
public class ScrollerTag extends UIComponentTag {

    protected String width = null;

    protected String align = null;

    protected String infoType = null;

    protected String showPageGo = null;

    protected String showRowsOfPage = null;

    protected String styleClass = null;

    
    public String getComponentType() {
        return ("Scroller");
    }
    

    public String getRendererType() {
        return (null);
    }
    

    public void setWidth(String width) {
        this.width = width;
    }
    

    public void setAlign(String align) {
        this.align = align;
    }


    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }
    

    public void setShowPageGo(String showPageGo) {
        this.showPageGo = showPageGo;
    }

    public void setShowRowsOfPage(String showRowsOfPage) {
        this.showRowsOfPage = showRowsOfPage;
    }
    

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void release() {
        super.release();
        showPageGo = "";
        showRowsOfPage = "";
        styleClass = "";
    }

    
    protected void setProperties(UIComponent component) {
        System.out.println("!!! @ ScrollerTag.setProperties");
        
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (width != null) {
            if (isValueReference(width)) {
                ValueBinding vb = context.getApplication().createValueBinding(width);
                component.setValueBinding("width", vb);
            } else {
                component.getAttributes().put("width", width);
            }
        }

        if (align != null) {
            if (isValueReference(align)) {
                ValueBinding vb = context.getApplication().createValueBinding(align);
                component.setValueBinding("align", vb);
            } else {
                component.getAttributes().put("align", align);
            }
        }

        if (infoType != null) {
            if (isValueReference(infoType)) {
                ValueBinding vb = context.getApplication().createValueBinding(infoType);
                component.setValueBinding("infoType", vb);
            } else {
                component.getAttributes().put("infoType", infoType);
            }
        }

        if (showPageGo != null) {
            if (isValueReference(showPageGo)) {
                ValueBinding vb = context.getApplication().createValueBinding(showPageGo);
                component.setValueBinding("showPageGo", vb);
            } else {
                component.getAttributes().put("showPageGo", showPageGo);
            }
        }

        if (showRowsOfPage != null) {
            if (isValueReference(showRowsOfPage)) {
                ValueBinding vb = context.getApplication().createValueBinding(showRowsOfPage);
                component.setValueBinding("showRowsOfPage", vb);
            } else {
                component.getAttributes().put("showRowsOfPage", showRowsOfPage);
            }
        }

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = context.getApplication().createValueBinding(styleClass);
                component.setValueBinding("styleClass", vb);
            } else {
                component.getAttributes().put("styleClass", styleClass);
            }
        }

    }//setProperties()
    

}//class ScrollerTag
