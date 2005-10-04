package org.pgist.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;


/**
 * UIAction
 * @author kenny
 *
 */
public class UIAction extends UIComponentBase implements ActionSource {

    
    private Map params = new HashMap();
    private MethodBinding action;
    private MethodBinding actionListener;
    
    
    public UIAction() {
    }
    

    public Map getParams() {
        return params;
    }


    public void setParams(Map params) {
        this.params = params;
    }


    public String getFamily() {
        return "org.pgist.faces.UIAction";
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


    public MethodBinding getAction() {
        return action;
    }


    public void setAction(MethodBinding action) {
        this.action = action;
    }


    public MethodBinding getActionListener() {
        return actionListener;
    }


    public void setActionListener(MethodBinding actionListener) {
        this.actionListener = actionListener;
    }


    public boolean isImmediate() {
        return true;
    }


    public void setImmediate(boolean immediate) {
    }


    public void addActionListener(ActionListener listener) {
        addFacesListener(listener);
    }

    public ActionListener[] getActionListeners() {
        return (ActionListener[]) getFacesListeners(ActionListener.class);
    }

    public void removeActionListener(ActionListener listener) {
        removeFacesListener(listener);
    }
    
    
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        super.broadcast(event);
        if (event instanceof ActionEvent) {
            FacesContext context = getFacesContext();
            MethodBinding mb = getActionListener();
            if (mb != null)  mb.invoke(context, new Object[] { event });
            ActionListener listener = context.getApplication().getActionListener();
            if (listener != null) listener.processAction((ActionEvent) event);
        }
    }
    
    
    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, action);
        values[2] = saveAttachedState(context, actionListener);
        return (values);
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        action = (MethodBinding) restoreAttachedState(context, values[1]);
        actionListener = (MethodBinding) restoreAttachedState(context, values[2]);
    }
      

}//class UIAction
