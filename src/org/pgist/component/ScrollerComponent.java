package org.pgist.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;

import org.pgist.renderkit.Util;
import org.pgist.util.PageSetting;


/**
 * This component produces a search engine style scroller that facilitates
 * easy navigation over results that span across several pages. It
 * demonstrates how a component can do decoding and encoding
 * without delegating it to a renderer.
 * 
 * @author kenny
 *
 */
public class ScrollerComponent extends UICommand {

    public static final int ACTION_JUMP = -1;
    public static final int ACTION_DIRECT = -2;
    public static final int ACTION_ROWOFPAGE = -3;

    public static final String FORM_NUMBER_ATTR = "com.sun.faces.FormNumber";

    
    public ScrollerComponent() {
        super();
        this.setRendererType(null);
    }

    
    /**
     * <p>Return the component family for this component.</p>
     */
    public String getFamily() {
        return ("Scroller");
    }
    

    public void decode(FacesContext context) {
        String curPage = null;
        String action = null;
        int actionInt = 0;
        int currentPage = 1;
        String clientId = getClientId(context);
        Map requestParameterMap = (Map) context.getExternalContext()
                .getRequestParameterMap();
        action = (String) requestParameterMap.get(clientId + "_action");
        if (action == null || action.length() == 0) {
            // nothing to decode
            return;
        }
        MethodBinding mb = Util.createConstantMethodBinding(action);

        this.getAttributes().put("action", mb);
        curPage = (String) requestParameterMap.get(clientId + "_page");
        currentPage = Integer.valueOf(curPage).intValue();

        PageSetting setting = (PageSetting) getValue();
        
        String showPageNumber = (String) getAttributes().get("showPageNumber");
        try {
            int pageOfScreen = Integer.parseInt(showPageNumber);
            if (pageOfScreen<=0) pageOfScreen = 10;
            setting.setPageOfScreen(pageOfScreen);
        } catch(Exception e) {
            setting.setPageOfScreen(10);
        }

        // Assert that action's length is 1.
        switch (actionInt = Integer.valueOf(action).intValue()) {
            case ACTION_JUMP:
                setting.setPage(currentPage);
                break;
            case ACTION_ROWOFPAGE:
                curPage = (String) requestParameterMap.get(clientId + "_rowOfPage");
                currentPage = Integer.valueOf(curPage).intValue();
                setting.setRowOfPage(currentPage);
                break;
            default:
        }
        this.queueEvent(new ActionEvent(this));
    }//decode()
    

    public boolean getRendersChildren() {
        return true;
    }
    

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
    

    public void encodeBegin(FacesContext context) throws IOException {
        return;
    }
    

    public void encodeEnd(FacesContext context) throws IOException {
        String clientId = getClientId(context);
        int formNumber = getFormNumber(context);
        
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("table", null);
        
        String width = (String) getAttributes().get("width");
        if (width!=null && !"".equals(width)) {
            writer.writeAttribute("width", width, null);
        } else {
            writer.writeAttribute("width", "100%", null);
        }
        
        writer.startElement("tr", null);
        writer.startElement("td", null);
        writer.writeAttribute("align", "center", null);
        
        writer.startElement("table", null);
        writer.startElement("tr", null);
        
        PageSetting setting = (PageSetting) getValue();
        int page = setting.getPage();
        
        //scrolling information
        String infoType = (String) getAttributes().get("infoType");
        if ("row".equalsIgnoreCase(infoType)) {//show row infomation
            writer.startElement("td", null);
            writer.writeAttribute("style", "margin-right:10px;", null);
            writer.writeText("Rows: ", null);
            writer.startElement("span style=\"color:red;\"", null);
            writer.writeText(setting.getFirstRow()+"-"+setting.getLastRow(), null);
            writer.endElement("span");
            writer.writeText("/"+setting.getRowSize(), null);
        } else if ("page".equalsIgnoreCase(infoType)) {//show page information
            writer.startElement("td", null);
            writer.writeAttribute("style", "margin-right:10px;", null);
            writer.writeText("Page: ", null);
            writer.startElement("span style=\"color:red;\"", null);
            writer.writeText(""+setting.getPage(), null);
            writer.endElement("span");
            writer.writeText("/"+setting.getPageSize(), null);
        }

        //prev
        writer.startElement("td", null);
        writer.writeAttribute("align", "right", null);
        if (page!=1) {
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onClick", getScrollPage(clientId, formNumber, page-1, ACTION_JUMP), null);
        }
        writer.writeText("<<", null);
        if (page>1) {
            writer.endElement("a");
        }
        writer.endElement("td");
        
        //internal numbers
        for (int i=setting.getHead(), size=setting.getTail(); i<=size; i++) {
            writer.startElement("td", null);
            writer.writeAttribute("align", "center", null);
            if (i==page) {
                writer.startElement("span", null);
                writer.writeAttribute("style", "color:red;", null);
            } else {
                writer.startElement("a", null);
                writer.writeAttribute("href", "#", null);
                writer.writeAttribute("onClick", getScrollPage(clientId, formNumber, i, ACTION_JUMP), null);
            }
            writer.writeText(""+i, null);
            if (i==page) {
                writer.endElement("span");
            } else {
                writer.endElement("a");
            }
        }//for i
        writer.endElement("td");
        
        //next
        writer.startElement("td", null);
        writer.writeAttribute("align", "left", null);
        if (page!=setting.getPageSize()) {
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onClick", getScrollPage(clientId, formNumber, page+1, ACTION_JUMP), null);
        }
        writer.writeText(">>", null);
        if (page>1) {
            writer.endElement("a");
        }
        writer.endElement("td");

        //directly go
        String showPageGo = (String) getAttributes().get("showPageGo");
        if ("true".equalsIgnoreCase(showPageGo)) {
            writer.startElement("td", null);
            writer.writeAttribute("align", "center", null);
            writer.writeAttribute("style", "padding-left:10px; padding-right:10px;", null);
            writer.writeText("Page:", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "text", null);
            writer.writeAttribute("style", "border:thin dotted #800080;width:40px;", null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "submit", null);
            writer.writeAttribute("value", "Go", null);
        }
        
        //rows per page
        String showRowsOfPage = (String) getAttributes().get("showRowsOfPage");
        if ("true".equalsIgnoreCase(showRowsOfPage)) {
            writer.startElement("td", null);
            writer.writeAttribute("align", "center", null);
            writer.writeAttribute("style", "padding-left:20px;", null);
            writer.writeText("Rows Per Page:", null);
            writer.startElement("select", null);
            writer.writeAttribute("onChange", getScrollPage(clientId, formNumber, page+1, ACTION_ROWOFPAGE), null);
            int[] options = setting.getOptions();
            for (int i=0; i<options.length; i++) {
                if (options[i]==setting.getRowOfPage()) {
                    writer.startElement("option selected", null);
                } else {
                    writer.startElement("option", null);
                }
                writer.writeAttribute("value", ""+options[i], null);
                writer.writeText(""+options[i], null);
                writer.endElement("option");
            }//for i
            writer.endElement("select");
        }

        writer.endElement("td");
        writer.endElement("tr");
        writer.endElement("table");
        
        writer.endElement("td");
        writer.endElement("tr");
        writer.endElement("table");

        //page
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_page", null);
        writer.endElement("input");
        //action
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_action", null);
        writer.endElement("input");
        //rowOfPage
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_rowOfPage", null);
        writer.endElement("input");
    }//encodeEnd()
    

    private String getScrollPage(String clientId, int formNumber, int page, int action) {
        return "document.forms[" + formNumber + "]['" + clientId
            + "_action'].value='" +action + "';document.forms[" + formNumber + "]['" + clientId
            + "_page'].value='" + page + "';document.forms[" + formNumber + "].submit()";
    }//getScrollPage()


}//class ScrollerComponent

