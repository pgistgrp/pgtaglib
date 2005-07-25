package org.pgist.taglib;

import org.pgist.component.ScrollerComponent;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.webapp.UIComponentTag;

/**
 * <p>
 * ScrollerTag is the tag handler class for <code>ScrollerComponent.
 * 
 * @author kenny
 *
 */
public class ScrollerTag extends UIComponentTag {

    protected String pageSetting = null;

    protected String actionListener = null;

    protected String width = null;

    protected String infoType = null;

    protected String showPageGo = null;

    protected String showRowsOfPage = null;

    protected String showPageNumber = null;

    
    public void setPageSetting(String pageSetting) {
        this.pageSetting = pageSetting;
    }
    

    public String getComponentType() {
        return ("Scroller");
    }
    

    public String getRendererType() {
        return (null);
    }
    

    /**
     * method reference to handle an action event generated as a result of
     * clicking on a link that points a particular page in the result-set.
     */
    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }

    public void setWidth(String width) {
        this.width = width;
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
    

    public void setShowPageNumber(String showPageNumber) {
        this.showPageNumber = showPageNumber;
    }
    

    public void release() {
        super.release();
    }

    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (pageSetting != null) {
            if (isValueReference(pageSetting)) {
                ValueBinding vb = getFacesContext().getApplication()
                        .createValueBinding(pageSetting);
                component.setValueBinding("value", vb);
            } else {
                ((UIData) component).setValue(pageSetting);
            }
        }

        if (actionListener != null) {
            if (isValueReference(actionListener)) {
                Class args[] = { ActionEvent.class };
                MethodBinding mb = FacesContext.getCurrentInstance()
                        .getApplication().createMethodBinding(actionListener,
                                args);
                ((ScrollerComponent) component).setActionListener(mb);
            } else {
                Object params[] = { actionListener };
                throw new javax.faces.FacesException();
            }
        }

        if (width != null) {
            if (isValueReference(width)) {
                ValueBinding vb = context.getApplication().createValueBinding(
                        width);
                component.setValueBinding("width", vb);
            } else {
                component.getAttributes().put("width", width);
            }
        }

        if (infoType != null) {
            if (isValueReference(infoType)) {
                ValueBinding vb = context.getApplication().createValueBinding(
                        infoType);
                component.setValueBinding("infoType", vb);
            } else {
                component.getAttributes().put("infoType", infoType);
            }
        }

        if (showPageGo != null) {
            if (isValueReference(showPageGo)) {
                ValueBinding vb = context.getApplication().createValueBinding(
                        showPageGo);
                component.setValueBinding("showPageGo", vb);
            } else {
                component.getAttributes().put("showPageGo", showPageGo);
            }
        }

        if (showRowsOfPage != null) {
            if (isValueReference(showRowsOfPage)) {
                ValueBinding vb = context.getApplication().createValueBinding(
                        showRowsOfPage);
                component.setValueBinding("showRowsOfPage", vb);
            } else {
                component.getAttributes().put("showRowsOfPage", showRowsOfPage);
            }
        }

        if (showPageNumber != null) {
            if (isValueReference(showPageNumber)) {
                ValueBinding vb = context.getApplication().createValueBinding(
                        showPageNumber);
                component.setValueBinding("showPageNumber", vb);
            } else {
                component.getAttributes().put("showPageNumber", showPageNumber);
            }
        }

    }//setProperties()
    

}//class ScrollerTag
