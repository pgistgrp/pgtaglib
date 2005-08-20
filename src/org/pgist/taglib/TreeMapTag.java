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
    protected String readBinding = null;
    protected String writeBinding = null;
    protected String title = null;
    protected String content = null;
    protected String username = null;
    protected String tone = null;

    
    public String getComponentType() {
        return ("org.pgist.faces.TreeMap");
    }


    public String getRendererType() {
        return null;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    

    public void setDepth(String depth) {
        this.depth = depth;
    }
    

    public void setReadBinding(String readBinding) {
        this.readBinding = readBinding;
    }


    public void setWriteBinding(String writeBinding) {
        this.writeBinding = writeBinding;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setTone(String tone) {
        this.tone = tone;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void release() {
        super.release();
        styleClass = "";
        depth = "";
        readBinding = "";
        title = "";
        content = "";
        tone = "";
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

        if (tone != null) {
            if (isValueReference(tone)) {
                ValueBinding vb = context.getApplication().createValueBinding(tone);
                component.setValueBinding("tone", vb);
            } else {
                component.getAttributes().put("tone", tone);
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

        if (readBinding != null) {
            if (readBinding != null) {
                component.getAttributes().put("readBinding", readBinding);
            }
        }
        
        if (writeBinding != null) {
            if (writeBinding != null) {
                component.getAttributes().put("writeBinding", writeBinding);
            }
        }
    }//setProperties()
    

}//class TreeMapTag
