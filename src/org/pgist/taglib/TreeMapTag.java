package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;


/**
 * Tag for tree map
 * @author kenny
 *
 */
public class TreeMapTag extends UIComponentTag {

    
    protected String styleClass = null;
    protected String depth = null;
    protected String actionBinding = null;
    protected String title = null;
    protected String content = null;
    protected String conbar = null;
    protected String focus = null;
    protected String username = null;

    
    public String getComponentType() {
        return ("org.pgist.faces.TreeMap");
    }


    public String getRendererType() {
        return (null);
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void setDepth(String depth) {
        this.depth = depth;
    }
    

    public void setactionBinding(String actionBinding) {
        this.actionBinding = actionBinding;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setConbar(String conbar) {
        this.conbar = conbar;
    }


    public void setFocus(String focus) {
        this.focus = focus;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void release() {
        super.release();
        styleClass = "";
        depth = "";
        actionBinding = "";
        title = "";
        content = "";
        conbar = "";
        focus = "";
        username = "";
    }

    
    protected void setProperties(UIComponent component) {
        
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = context.getApplication().createValueBinding(styleClass);
                component.setValueBinding("styleClass", vb);
            } else {
                component.getAttributes().put("styleClass", styleClass);
            }
        }

        if (depth != null) {
            if (isValueReference(depth)) {
                ValueBinding vb = context.getApplication().createValueBinding(depth);
                component.setValueBinding("depth", vb);
            } else {
                component.getAttributes().put("depth", depth);
            }
        }

        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = context.getApplication().createValueBinding(title);
                component.setValueBinding("title", vb);
            } else {
                component.getAttributes().put("title", title);
            }
        }

        if (content != null) {
            if (isValueReference(content)) {
                ValueBinding vb = context.getApplication().createValueBinding(content);
                component.setValueBinding("content", vb);
            } else {
                component.getAttributes().put("content", content);
            }
        }

        if (conbar != null) {
            if (isValueReference(conbar)) {
                ValueBinding vb = context.getApplication().createValueBinding(conbar);
                component.setValueBinding("conbar", vb);
            } else {
                component.getAttributes().put("conbar", conbar);
            }
        }

        if (focus != null) {
            if (isValueReference(focus)) {
                ValueBinding vb = context.getApplication().createValueBinding(focus);
                component.setValueBinding("focus", vb);
            } else {
                component.getAttributes().put("focus", focus);
            }
        }

        if (username != null) {
            if (isValueReference(username)) {
                ValueBinding vb = context.getApplication().createValueBinding(username);
                component.setValueBinding("username", vb);
            } else {
                component.getAttributes().put("username", username);
            }
        }

        if (actionBinding != null) {
            if (actionBinding != null) {
                component.getAttributes().put("actionBinding", actionBinding);
            }
        }
    }//setProperties()
    

}//class TreeMapTag
