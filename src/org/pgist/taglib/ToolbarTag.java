package org.pgist.taglib;


import com.sun.faces.util.Util;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


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

