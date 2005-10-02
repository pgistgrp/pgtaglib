package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * Tag for Discourse TreeMap.
 * @author kenny
 *
 */
public class DoTreeMapTag extends UIComponentTag {

    
    protected String styleClass = null;
    protected String depth = null;

    
    public String getComponentType() {
        return "org.pgist.faces.Discourse";
    }
    

    public String getRendererType() {
        return "org.pgist.faces.doTreeMap";
    }
    

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void setDepth(String depth) {
        this.depth = depth;
    }


    public void release() {
        super.release();
        styleClass = "";
        depth = "";
    }
    

    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = context.getApplication().createValueBinding(styleClass);
                component.setValueBinding("styleClass", vb);
            } else {
                component.getAttributes().put("styleClass", styleClass);
            }
        }

        if (depth != null) {
            if (isValueReference(depth)) {
                ValueBinding vb = context.getApplication().createValueBinding(depth);
                component.setValueBinding("depth", vb);
            } else {
                component.getAttributes().put("depth", depth);
            }
        }

    }//setProperties()

}//class DoTreeMapTag
