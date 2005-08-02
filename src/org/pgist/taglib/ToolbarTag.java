package org.pgist.taglib;


import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import com.sun.faces.util.Util;


public class ToolbarTag extends UIComponentTag {

    
    private String styleClass;


    public ToolbarTag() {
    }
    

    public void setStyleClass(String styleClass){
        this.styleClass = styleClass;
    }


    public String getRendererType(){
        return "org.pgist.faces.Toolbar";
    }
    

    public String getComponentType(){
        return "org.pgist.faces.Toolbar";
    }
    

    protected void setProperties(UIComponent component){
        super.setProperties(component);
        if(styleClass != null) {
            if(UIComponentTag.isValueReference(styleClass)) {
                javax.faces.el.ValueBinding vb = Util.getValueBinding(styleClass);
                component.setValueBinding("styleClass", vb);
            } else {
                component.getAttributes().put("styleClass", styleClass);
            }
        }
    }//setProperties()


}//class ToolbarTag
