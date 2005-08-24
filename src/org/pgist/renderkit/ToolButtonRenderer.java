package org.pgist.renderkit;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import com.sun.faces.renderkit.html_basic.FormRenderer;
import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer;
import com.sun.faces.util.Util;


/**
 * Renderer for component ToolButton
 * @author kenny
 *
 */
public class ToolButtonRenderer extends HtmlBasicRenderer {

    
    private String clientId;

    
    public ToolButtonRenderer() {
        clientId = null;
    }
    

    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null)
            throw new NullPointerException(
                    Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        UICommand command = (UICommand) component;
        if (Util.componentIsDisabledOnReadonly(component)) return;
        
        String clientId = command.getClientId(context);
        String paramName = getHiddenFieldName(context, command);
        Map requestParameterMap = context.getExternalContext()
                .getRequestParameterMap();
        String value = (String) requestParameterMap.get(paramName);
        if (value == null || value.equals("") || !clientId.equals(value))
            return;
        ActionEvent actionEvent = new ActionEvent(component);
        component.queueEvent(actionEvent);
    }
    

    protected UIForm getMyForm(FacesContext context, UICommand command) {
        UIComponent parent;
        for (parent = command.getParent(); parent != null; parent = parent
                .getParent())
            if (parent instanceof UIForm)
                break;

        return (UIForm) parent;
    }

    
    protected String getHiddenFieldName(FacesContext context, UICommand command) {
        UIForm uiform = getMyForm(context, command);
        String formClientId = uiform.getClientId(context);
        return formClientId + ':' + "_id" + "cl";
    }

    
    public boolean getRendersChildren() {
        return true;
    }
    

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null)
            throw new NullPointerException(Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        UICommand command = (UICommand) component;
        if (!command.isRendered()) return;
            
        ResponseWriter writer = context.getResponseWriter();
        Util.doAssert(writer != null);
        clientId = command.getClientId(context);
        UIForm uiform = getMyForm(context, command);
        if (uiform == null) return;

        String formClientId = uiform.getClientId(context);
        HtmlBasicRenderer.Param paramList[] = getParamList(context, command);
        StringBuffer sb = new StringBuffer();
        writer.writeText("[", null);
        writer.startElement("b", null);
        writer.startElement("a", component);
        writeIdAttributeIfNecessary(context, writer, component);
        writer.writeAttribute("href", "#", "href");
        Util.renderPassThruAttributes(writer, component, new String[] { "onclick" });
        Util.renderBooleanPassThruAttributes(writer, component);
        sb = new StringBuffer();
        String confirm = (String) command.getAttributes().get("confirm");
        if (confirm!=null && !"".equals(confirm)) {
            sb.append("if (!confirm('").append(confirm).append("')) return false;");
        }
        sb.append("document.forms[");
        sb.append("'");
        sb.append(formClientId);
        sb.append("'");
        sb.append("]['");
        sb.append(getHiddenFieldName(context, command));
        sb.append("'].value='");
        sb.append(clientId);
        sb.append("';");
        int i = 0;
        for (int len = paramList.length; i < len; i++) {
            sb.append("document.forms[");
            sb.append("'");
            sb.append(formClientId);
            sb.append("'");
            sb.append("]['");
            sb.append(paramList[i].getName());
            sb.append("'].value='");
            sb.append(paramList[i].getValue());
            sb.append("';");
        }

        sb.append(" document.forms[");
        sb.append("'");
        sb.append(formClientId);
        sb.append("'");
        sb.append("].submit()");
        sb.append("; return false;");
        writer.writeAttribute("onclick", sb.toString(), null);
        String styleClass = (String) command.getAttributes().get("styleClass");
        if (styleClass != null)
            writer.writeAttribute("class", styleClass, "styleClass");
        String label = null;
        Object value = ((UICommand) component).getValue();
        if (value != null)
            label = value.toString();
        if (label != null && label.length() != 0)
            writer.write(label);
        writer.endElement("b");
        writer.writeText("]", null);
        writer.flush();
    }
    

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null)
            throw new NullPointerException(
                    Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        if (!component.isRendered()) return;

        UIComponent kid;
        for (Iterator kids = component.getChildren().iterator(); kids.hasNext(); kid
                .encodeEnd(context)) {
            kid = (UIComponent) kids.next();
            kid.encodeBegin(context);
            if (kid.getRendersChildren())
                kid.encodeChildren(context);
        }
    }

    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (context == null || component == null)
            throw new NullPointerException(
                    Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        UICommand command = (UICommand) component;
        if (!command.isRendered()) return;

        ResponseWriter writer = context.getResponseWriter();
        Util.doAssert(writer != null);
        writer.endElement("a");
        FormRenderer.addNeededHiddenField(context, getHiddenFieldName(context,
                command));
        HtmlBasicRenderer.Param paramList[] = getParamList(context, command);
        for (int i = 0; i < paramList.length; i++)
            FormRenderer.addNeededHiddenField(context, paramList[i].getName());
    }


}//class ToolButtonRenderer
