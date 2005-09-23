package org.pgist.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;


/**
 * MultiSelect Component
 * @author kenny
 *
 */
public class AjaxSelectComponent extends UIComponentBase {

    
    public static final String FORM_NUMBER_ATTR = "com.sun.faces.FormNumber";
    protected String clientId;
    protected String formClientId;
    private String style="";
    private String styleClass="";
    private String[] keyValues = null;
    private ValueBinding value;
    private ValueBinding initValue;
    Collection init = null;
    String keyName = null;
    String labelName = null;
    String ajax= null;

    
    public AjaxSelectComponent() {
        setRendererType(null);
    }
    

    public String getFamily() {
        return "org.pgist.faces.AjaxSelect";
    }
    

    public boolean getRendersChildren() {
        return true;
    }
    
    
    public void decode(FacesContext context) {
        super.decode(context);
        
        value = getValueBinding("value");
        
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        keyValues = ((String) request.getParameter(getClientId(context)+"_keyValues")).split(",");
        
        value.setValue(context, keyValues);
    }
    
    
    public void encodeBegin(FacesContext context) throws IOException {
        style = (String) getAttributes().get("style");
        styleClass = (String) getAttributes().get("styleClass");
        clientId = getClientId(context);
        formClientId = getFormClientId(context);
        keyName = (String) getAttributes().get("key");
        labelName = (String) getAttributes().get("label");
        ajax = (String) getAttributes().get("ajax");
        
        initValue = getValueBinding("initValue");
        Object initValues = initValue.getValue(context);
        if (initValues==null) {
            init = new ArrayList();
        } else if (initValues instanceof Collection) {
            init = (Collection) initValues;
        }
        
        return;
    }//encodeBegin()
    
    
    public void encodeEnd(FacesContext context) throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        
        try {
            String varPrefix = getClientId(context).replace(':', '_');
            String selectedId = getClientId(context)+"_selected";
            String unSelectedId = getClientId(context)+"_unselected";
            String keysId = getClientId(context)+"_keyValues";
            String pageId = getClientId(context)+"_currentPage";
            String searchId = getClientId(context)+"_search";
            
            writer.startElement("input", null);
            writer.writeAttribute("id", keysId, null);
            writer.writeAttribute("type", "hidden", null);
            writer.writeAttribute("name", keysId, null);
            StringBuffer sb = new StringBuffer();
            for (Iterator iter=init.iterator(); iter.hasNext();) {
                Object one = iter.next();
                sb.append(BeanUtils.getNestedProperty(one, keyName));
                if (iter.hasNext()) sb.append(",");
            }//for iter
            writer.writeAttribute("value", sb.toString(), null);
            writer.endElement("input");

            writer.startElement("input", null);
            writer.writeAttribute("type", "hidden", null);
            writer.writeAttribute("id", pageId, null);
            writer.writeAttribute("value", "1", null);
            writer.endElement("input");
            
            writer.write("<script language=\"JavaScript\">\n");
            
            writer.write("function "+varPrefix+"_callback(data) {\n");
            writer.write("$('"+pageId+"').value=data[data.length-1];\n");
            writer.write("var opt=document.getElementById('"+unSelectedId+"').options;\n");
            writer.write("for (var i=opt.length-1; i>=0; i--) {\n");
            writer.write("opt[i]=null;\n");
            writer.write("}\n");
            writer.write("for (var i=0; i<data.length-1; i++) {\n");
            writer.write("opt[opt.length]=new Option(data[i]['"+labelName+"'], data[i]['"+keyName+"']);\n");
            writer.write("}\n");
            writer.write("}\n");
            
            writer.write("</script>\n");
            
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "50%", null);
            writer.writeAttribute("valign", "top", null);
            writer.startElement("div", null);
            
            //left
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            writer.writeAttribute("colspan", "2", null);
            writer.startElement("select", null);
            writer.writeAttribute("style", "width:100%;", null);
            writer.writeAttribute("id", selectedId, null);
            writer.writeAttribute("size", "10", null);
            writer.writeAttribute("multiple", Boolean.TRUE, "value");
            for (Iterator iter=init.iterator(); iter.hasNext();) {
                Object one = iter.next();
                writer.startElement("option", null);
                writer.writeAttribute("value", BeanUtils.getNestedProperty(one, keyName), null);
                writer.writeText(BeanUtils.getNestedProperty(one, labelName), null);
                writer.endElement("option");
            }//for iter
            writer.endElement("select");
            writer.endElement("td");
            writer.endElement("tr");
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "50%", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("value", "Delete", null);
            writer.writeAttribute("onClick", "deleteSelection('"+selectedId+"'); selection2Hidden('"+selectedId+"', '"+keysId+"');", null);
            writer.endElement("td");
            writer.startElement("td", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("value", "Add", null);
            writer.writeAttribute("onClick", "var obj=document.getElementById('"+getClientId(context)+"_right"+"'); obj.style.display='inline'; this.disabled=true; AjaxGlossary.getTermList("+varPrefix+"_callback, 1, '');", null);
            writer.endElement("td");
            writer.endElement("tr");
            writer.endElement("table");
            writer.endElement("div");

            writer.endElement("td");
            writer.startElement("td", null);
            writer.writeAttribute("width", "50%", null);
            writer.writeAttribute("valign", "top", null);
            writer.startElement("div", null);
            writer.writeAttribute("style", "display:none;", null);
            writer.writeAttribute("id", getClientId(context)+"_right", null);

            //right
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onclick", "transferSelection('"+unSelectedId+"', '"+selectedId+"');selection2Hidden('"+selectedId+"', '"+keysId+"');return false;", null);
            writer.startElement("img", null);
            writer.writeAttribute("src", context.getExternalContext().encodeResourceURL(context.getApplication().getViewHandler().getResourceURL(context, "/images/leftarrow.png")), null);
            writer.writeAttribute("style", "border:0;", null);
            writer.endElement("a");
            writer.endElement("td");
            writer.startElement("td", null);
            writer.writeAttribute("style", "width:100%;", null);
            
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%;", null);
            writer.writeAttribute("height", "100%;", null);
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%;", null);
            writer.writeAttribute("height", "100%;", null);
            writer.startElement("select", null);
            writer.writeAttribute("style", "width:100%;", null);
            writer.writeAttribute("id", unSelectedId, null);
            writer.writeAttribute("size", "10", null);
            writer.writeAttribute("multiple", Boolean.TRUE, "value");
            writer.endElement("select");
            writer.endElement("td");
            writer.endElement("tr");
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.startElement("tr", null);
            
            //buttons
            writer.startElement("td", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "text", null);
            writer.writeAttribute("value", "", null);
            writer.writeAttribute("size", "10", null);
            writer.writeAttribute("id", searchId, null);
            writer.writeAttribute("onkeyup", "AjaxGlossary.getTermList("+varPrefix+"_callback, 1, $('"+searchId+"').value);", null);
            writer.endElement("input");
            writer.endElement("td");
            writer.startElement("td", null);
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onClick", "AjaxGlossary.getTermList("+varPrefix+"_callback, $('"+pageId+"').value-1, $('"+searchId+"').value);return false;", null);
            writer.writeText("<", null);
            writer.endElement("a");
            writer.endElement("td");
            writer.startElement("td", null);
            writer.write("&nbsp;");
            writer.endElement("td");
            writer.startElement("td", null);
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onClick", "AjaxGlossary.getTermList("+varPrefix+"_callback, $('"+pageId+"').value+1, $('"+searchId+"').value);return false;", null);
            writer.writeText(">", null);
            writer.endElement("a");
            writer.endElement("td");

            writer.endElement("tr");
            writer.endElement("td");
            writer.endElement("tr");
            writer.endElement("table");
            
            writer.endElement("tr");
            writer.endElement("td");
            writer.endElement("tr");
            writer.endElement("table");
            
            writer.endElement("td");
            writer.endElement("tr");
            writer.endElement("table");
            
            writer.endElement("div");
            writer.endElement("td");
            writer.endElement("tr");
            writer.endElement("table");
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeEnd()
    

    protected int getFormNumber(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestMap();
        int numForms = 0;
        Integer formsInt = null;
        // find out the current number of forms in the page.
        if (null != (formsInt = (Integer) requestMap.get(FORM_NUMBER_ATTR))) {
            numForms = formsInt.intValue();
            //     since the form index in the document starts from 0.
            numForms--;
        }
        return numForms;
    }//getFormNumber()
    

    private String getFormClientId(FacesContext context) {
        UIComponent parent;
        for(parent = getParent(); parent != null; parent = parent.getParent())
            if(parent instanceof UIForm) break;

        UIForm uiform = (UIForm)parent;
        return uiform.getClientId(context);
    }


}//class AjaxSelectComponent
