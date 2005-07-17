package org.pgist.renderkit;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;


/**
 * 
 * @author kenny
 *
 */
public class ConstantMethodBinding extends MethodBinding implements StateHolder {

    
    private String outcome = null;


    public ConstantMethodBinding() {
    }


    public ConstantMethodBinding(String yourOutcome) {
        outcome = yourOutcome;
    }


    public Object invoke(FacesContext context, Object params[]) {
        return outcome;
    }


    public Class getType(FacesContext context) {
        return String.class;
    }

    
    // ----------------------------------------------------- StateHolder Methods

    
    public Object saveState(FacesContext context) {
        return outcome;
    }


    public void restoreState(FacesContext context, Object state) {
        outcome = (String) state;
    }


    private boolean transientFlag = false;


    public boolean isTransient() {
        return (this.transientFlag);
    }


    public void setTransient(boolean transientFlag) {
        this.transientFlag = transientFlag;
    }
    
    
}
