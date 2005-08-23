package org.pgist.taglib;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

/**
 * 
 * @author kenny
 *
 */
public class HideTag extends UIComponentTag {

    private String forRole;

    public HideTag() {
    }

    public void setForRole(String forRole) {
        this.forRole = forRole;
    }

    public String getRendererType() {
        return "org.pgist.faces.Hide";
    }

    public String getComponentType() {
        return "org.pgist.faces.ShowHide";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (forRole != null) {
            component.getAttributes().put("forRole", forRole);
        }
    }//setProperties()

    public int doStartTag() throws JspException {
        int rc = 0;
        try {
            rc = super.doStartTag();
        } catch (JspException e) {
            throw e;
        } catch (Throwable t) {
            throw new JspException(t);
        }
        return rc;
    }

    public int doEndTag() throws JspException {
        int rc = 0;
        try {
            rc = super.doEndTag();
        } catch (JspException e) {
            throw e;
        } catch (Throwable t) {
            throw new JspException(t);
        }
        return rc;
    }

    public String getDebugString() {
        String result = "id: " + getId() + " class: " + getClass().getName();
        return result;
    }

}//class ShowTag
