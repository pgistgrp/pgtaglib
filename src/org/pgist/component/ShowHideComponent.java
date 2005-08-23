package org.pgist.component;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;


/**
 * Base Component for show/hide tag
 * @author kenny
 *
 */
public class ShowHideComponent extends UIComponentBase {

    
    public ShowHideComponent() {
    }
    

    public String getFamily() {
        return "org.pgist.faces.ShowHide";
    }

    
    public boolean getRendersChildren() {
        return true;
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

    
}//class ShowHideComponent
