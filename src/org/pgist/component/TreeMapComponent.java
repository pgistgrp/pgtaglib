package org.pgist.component;

import java.io.IOException;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.pgist.model.Tree;


public class TreeMapComponent extends UIComponentBase {

    
    public TreeMapComponent() {
        setRendererType(null);
    }
    

    public String getFamily() {
        return "org.pgist.faces.TreeMap";
    }
    

    public boolean getRendersChildren() {
        return true;
    }

    
    public void encodeBegin(FacesContext context) throws IOException {
        return;
    }//encodeBegin()
    
    
    public void encodeEnd(FacesContext context) throws IOException {
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        String binding = (String) getAttributes().get("actionBinding");
        MethodBinding mb = context.getApplication().createMethodBinding(binding, new Class[] { ActionEvent.class, Long.class });
        Tree tree = (Tree) mb.invoke(context, new Object[] { new ActionEvent(this), new Long(request.getParameter("threadId")) });
        
        System.out.println("---> "+tree);
        
        ResponseWriter writer = context.getResponseWriter();
    }//encodeEnd()
    

}//class TreeMapComponent
