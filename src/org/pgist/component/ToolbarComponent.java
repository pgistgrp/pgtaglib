package org.pgist.component;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import java.util.Iterator;


public class ToolbarComponent extends UIComponentBase {


    public ToolbarComponent() {
    }


    public String getFamily() {
        return ("org.pgist.faces.Toolbar");
    }


    public boolean getRendersChildren() {
        return (true);
    }


    public void processDecodes(FacesContext context) {
        Iterator kids = getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            kid.processDecodes(context);
        }

        try {
            decode(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
    }


    public void updateModel(FacesContext context) {
    }


}//class ToolbarComponent

