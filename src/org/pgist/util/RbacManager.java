package org.pgist.util;

import javax.faces.context.FacesContext;


public class RbacManager {

    
    public static final String roleChecker = "org.pgist.RBAC_CHECKER";
    private static RbacChecker checker;
    
    
    static {
        FacesContext context = FacesContext.getCurrentInstance();
        String roleCheckerClass = (String) context.getExternalContext().getInitParameter(roleChecker);
        try {
            checker = (RbacChecker) Class.forName(roleCheckerClass).newInstance();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }//static
    

    public static boolean checkRole(String[] roles) {
        return checker.checkRole(roles);
    }
    
    
}//class RbacManager
