package org.pgist.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.pgist.util.RbacManager;

import com.sun.faces.util.Util;


public abstract class ShowHideRenderer extends BaseRenderer {

    
    private boolean showType = true;

    
    public ShowHideRenderer(boolean show) {
        this.showType = show;
    }
    
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        boolean show = false;

        String forRole = (String) component.getAttributes().get("forRole");
        if (forRole==null || "".equals(forRole)) {
            if (showType) show = true;
        }
        
        String[] roles = forRole.split(",");
        
        if (showType) {//show
            if (RbacManager.checkRole(roles)) {
                show = true;
            }
        } else {//hide
            if (!RbacManager.checkRole(roles)) {
                show = true;
            }
        }
        
        if (show) {
            if(context == null || component == null)
                throw new NullPointerException(Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
            if(!component.isRendered()) return;
            
            for(Iterator kids = component.getChildren().iterator(); kids.hasNext(); ) {
                UIComponent kid = (UIComponent)kids.next();
                if(kid.isRendered()) {
                    encodeRecursive(context, kid);
                }
            }//for kids
        }
    }//encodeChildren()


    public boolean checkRole(FacesContext context, UIComponent component) {
        boolean show = false;

        String forRole = (String) component.getAttributes().get("forRole");
        if (forRole==null || "".equals(forRole)) {
            if (showType) show = true;
        }
        
        String[] roles = forRole.split(",");
        
        if (showType) {//show
            if (RbacManager.checkRole(roles)) {
                show = true;
            }
        } else {//hide
            if (!RbacManager.checkRole(roles)) {
                show = true;
            }
        }
        
        return show;
    }//checkRole()
    
    
}//class ShowHideRenderer
