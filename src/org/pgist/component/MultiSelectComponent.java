package org.pgist.component;

import java.io.IOException;
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
public class MultiSelectComponent extends UIComponentBase {

    
    public static final String FORM_NUMBER_ATTR = "com.sun.faces.FormNumber";
    protected String clientId;
    protected String formClientId;
    private int columns = 1;
    private String styleClass="";
    private String key="";
    private String label="";
    private ValueBinding value;
    private ValueBinding universalSet;
    private ValueBinding subSet;
    private String[] values = null;

    
    public MultiSelectComponent() {
        setRendererType(null);
    }
    

    public String getFamily() {
        return "org.pgist.faces.MultiSelect";
    }
    

    public boolean getRendersChildren() {
        return true;
    }
    
    
    public void decode(FacesContext context) {
        super.decode(context);
        
        value = getValueBinding("value");
        
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        values = (String[]) request.getParameterValues(getClientId(context)+"_multiSelect");
        
        value.setValue(context, values);
    }
    
    
    public void encodeBegin(FacesContext context) throws IOException {
        String s = (String) getAttributes().get("columns");
        if (s!=null && !"".equals(s)) columns = Integer.parseInt(s);
        
        styleClass = (String) getAttributes().get("styleClass");
        key = (String) getAttributes().get("key");
        label = (String) getAttributes().get("label");
        clientId = getClientId(context);
        formClientId = getFormClientId(context);
        universalSet = getValueBinding("universalSet");
        subSet = getValueBinding("subSet");
        
        return;
    }//encodeBegin()
    
    
    public void encodeEnd(FacesContext context) throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        
        try {
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("width", "100%", null);
            
            int count = 0;
            boolean open = false;
            Collection uSet = (Collection) universalSet.getValue(context);
            Collection sSet = (Collection) subSet.getValue(context);
            for (Iterator iter=uSet.iterator(); iter.hasNext(); ) {
                Object one = iter.next();
                if (count % columns == 0) {
                    if (open) writer.endElement("tr");
                    writer.startElement("tr", null);
                }
                writer.startElement("td", null);
                
                //render
                writer.startElement("label", null);
                writer.startElement("input", null);
                writer.writeAttribute("type", "checkbox", null);
                writer.writeAttribute("name", clientId+"_multiSelect", null);
                writer.writeAttribute("value", BeanUtils.getNestedProperty(one, key), null);
                if (styleClass!=null && styleClass.length()>0) {
                    writer.writeAttribute("class", styleClass, null);
                }
                if (values==null) {
                    for (Iterator iter0=sSet.iterator(); iter0.hasNext(); ) {
                        Object another = iter0.next();
                        if (BeanUtils.getNestedProperty(one, key).equals(BeanUtils.getNestedProperty(another, key))) {
                            writer.writeAttribute("checked", Boolean.TRUE, "value");
                            break;
                        }
                    }//for iter0
                } else {
                    for (int i=0; i<values.length; i++) {
                        if (BeanUtils.getNestedProperty(one, key).equals(values[i])) {
                            writer.writeAttribute("checked", Boolean.TRUE, "value");
                            break;
                        }
                    }//for i
                }
                writer.endElement("input");
                writer.writeText(BeanUtils.getNestedProperty(one, label), null);
                writer.endElement("label");
                
                writer.endElement("td");
                
                count++;
                if (count>=columns) count = 0;
            }//for iter
            
            if (open) writer.endElement("tr");
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


}//class MultiSelectComponent
