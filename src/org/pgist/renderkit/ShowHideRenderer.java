package org.pgist.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.pgist.util.RbacProxy;

import com.sun.faces.util.Util;


public abstract class ShowHideRenderer extends BaseRenderer {

    
    public static final String roleProxy = "org.pgist.RBAC_PROXY";
    private static RbacProxy rbacProxy = null;
    private boolean showType = true;

    
    public ShowHideRenderer(boolean show) {
        this.showType = show;
    }
    
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        boolean show = false;

        if (rbacProxy==null) {
            String roleProxyClass = (String) context.getExternalContext().getInitParameter(roleProxy);
            try {
                rbacProxy = (RbacProxy) Class.forName(roleProxyClass).newInstance();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        String forRole = (String) component.getAttributes().get("forRole");
        if (forRole==null || "".equals(forRole)) {
            if (showType) show = true;
        }
        
        String[] roles = forRole.split(",");
        
        if (showType) {//show
            if (rbacProxy.checkRole(roles)) {
                show = true;
            }
        } else {//hide
            if (!rbacProxy.checkRole(roles)) {
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


}//class ShowHideRenderer
