package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * Tag for Discourse UpTree.
 * @author kenny
 *
 */
public class DoUpTreeTag extends UIComponentTag {

    
    protected String styleClass = null;

    
    public String getComponentType() {
        return "org.pgist.faces.Discourse";
    }
    

    public String getRendererType() {
        return "org.pgist.faces.doUpTree";
    }
    

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void release() {
        super.release();
        styleClass = "";
    }
    

    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = context.getApplication().createValueBinding(
                        styleClass);
                component.setValueBinding("styleClass", vb);
            } else {
                component.getAttributes().put("styleClass", styleClass);
            }
        }

    }//setProperties()

}//class DoLayoutTag
