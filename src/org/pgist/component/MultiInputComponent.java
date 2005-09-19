package org.pgist.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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


/**
 * MultiSelect Component
 * @author kenny
 *
 */
public class MultiInputComponent extends UIComponentBase {

    
    public static final String FORM_NUMBER_ATTR = "com.sun.faces.FormNumber";
    protected String clientId;
    protected String formClientId;
    private String style="";
    private String styleClass="";
    private ValueBinding value;
    private ValueBinding initValue;
    private String[] values = null;
    private boolean postMode = false;
    Collection init = null;

    
    public MultiInputComponent() {
        setRendererType(null);
    }
    

    public String getFamily() {
        return "org.pgist.faces.MultiInput";
    }
    

    public boolean getRendersChildren() {
        return true;
    }
    
    
    public void decode(FacesContext context) {
        super.decode(context);
        
        value = getValueBinding("value");
        
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        values = (String[]) request.getParameterValues(getClientId(context)+"_multiInput");
        
        value.setValue(context, values);
        postMode = true;
    }
    
    
    public void encodeBegin(FacesContext context) throws IOException {
        style = (String) getAttributes().get("style");
        styleClass = (String) getAttributes().get("styleClass");
        clientId = getClientId(context);
        formClientId = getFormClientId(context);
        
        if (!postMode) {
            initValue = getValueBinding("initValue");
            Object initValues = initValue.getValue(context);
            if (initValues==null) {
                init = new ArrayList();
            } else if (initValues instanceof Collection) {
                init = (Collection) initValues;
            } else if (initValues instanceof String[]) {
                init = Arrays.asList((String[])initValues);
            }
        } else {
            if (values==null) {
                init = new ArrayList();
            } else {
                init = Arrays.asList((String[])values);
            }
        }
        
        return;
    }//encodeBegin()
    
    
    public void encodeEnd(FacesContext context) throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        
        try {
            String varPrefix = getClientId(context).replace(':', '_');
            String tableId = getClientId(context)+"_table";
            String lastRowId = getClientId(context)+"_lastrow";
            String count = varPrefix+"_count";
            
            writer.startElement("table", null);
            writer.writeAttribute("id", tableId, null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            
            //encode init list
            int n = init.size();
            int i = 0;
            writer.write("\n");
            writer.write("<script language=\"JavaScript\">");
            writer.write("\n");
            writer.write("var "+count+" = "+n+";");
            writer.write("\n");
            //function _addNewInput
            writer.write("function "+varPrefix+"_addNewInput() {");
            writer.write("\n");
            writer.write("var table=document.getElementById('"+tableId+"');");
            writer.write("\n");
            writer.write("var lastRow=document.getElementById('"+lastRowId+"');");
            writer.write("\n");
            writer.write("var newRow = table.insertRow(lastRow.rowIndex);");
            writer.write("\n");
            writer.write("var cell1 = newRow.insertCell(0);");
            writer.write("\n");
            writer.write("var cell2 = newRow.insertCell(1);");
            writer.write("\n");
            writer.write("cell1.innerHTML='<input id=\\\""
                    +getClientId(context)+"_multiInput_'+"+count
                    +"+'\\\" type=\\\"text\\\" name=\\\""+getClientId(context)+"_multiInput\\\" value=\\\"\\\">';");
            writer.write("\n");
            writer.write("cell2.innerHTML='<input type=\\\"button\\\" value=\\\"Delete\\\" onClick=\\\"+"+varPrefix+"_deleteInput(this);\\\">';");
            writer.write("\n");
            writer.write(count+"++; }");
            writer.write("\n");
            
            //function _deleteInput
            writer.write("function "+varPrefix+"_deleteInput(node) {");
            writer.write("\n");
            writer.write("document.getElementById('"+tableId+"').deleteRow(node.parentNode.parentNode.rowIndex)");
            writer.write("\n");
            writer.write("}");
            
            writer.write("</script>");
            writer.write("\n");
            
            for (Iterator iter=init.iterator(); iter.hasNext(); ) {
                writer.startElement("tr", null);
                writer.startElement("td", null);
                writer.startElement("input", null);
                writer.writeAttribute("id", getClientId(context)+"_multiInput_"+i, null);
                writer.writeAttribute("type", "text", null);
                writer.writeAttribute("name", getClientId(context)+"_multiInput", null);
                writer.writeAttribute("value", iter.next().toString(), null);
                writer.endElement("input");
                writer.endElement("td");
                writer.startElement("td", null);
                writer.startElement("input", null);
                writer.writeAttribute("type", "button", null);
                writer.writeAttribute("value", "Delete", null);
                writer.writeAttribute("onClick", varPrefix+"_deleteInput(this);", null);
                writer.endElement("input");
                writer.endElement("td");
                writer.endElement("tr");
                i++;
            }//for iter
            
            //encode new Input
            writer.startElement("tr", null);
            writer.writeAttribute("id", lastRowId, null);
            writer.startElement("td", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("value", "Add Input", null);
            writer.writeAttribute("onClick", varPrefix+"_addNewInput();", null);
            writer.endElement("input");
            writer.endElement("td");
            writer.startElement("td", null);
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


}//class MultiInputComponent
