package org.pgist.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
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
    
    
    /**
     * 
     * @return
     */
    public PageSetting getPageSetting(FacesContext context) {
        UIComponent parent = getParent();
        while (parent != null) {
            String rendererType = parent.getRendererType();
            if ("ListTable".equals(rendererType)) {
                PageSetting setting = (PageSetting) parent.getValueBinding("pageSetting").getValue(context);
                System.out.println("============  "+setting);
                return setting;
            }
            parent = parent.getParent();
        }
        
        return null;
    }//getPageSetting()
    

    /**
     * 
     * @return
     */
    public String getParentId(FacesContext context) {
        UIComponent parent = getParent();
        while (parent != null) {
            String rendererType = parent.getRendererType();
            if ("ListTable".equals(rendererType)) {
                String parentId = (String) parent.getAttributes().get("id");
                return parentId;
            }
            parent = parent.getParent();
        }
        
        return null;
    }//getParentId()
    

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
        System.out.println("!!! @ ScrollerComponent.encodeBegin");
        return;
    }
    

    public void encodeEnd(FacesContext context) throws IOException {
        System.out.println("@ ScrollerComponent - encodeEnd !");

        ResponseWriter writer = context.getResponseWriter();

        String parentId = getParentId(context);
        String clientId = getClientId(context);
        int formNumber = getFormNumber(context);
        
        writer.startElement("table", null);
        writer.writeAttribute("cell-padding", "0", null);
        writer.writeAttribute("cell-spacing", "0", null);
        writer.writeText("\n", null);
        writer.startElement("tr", null);
        String styleClass = (String) getAttributes().get("styleClass");
        if (styleClass!=null && !"".equals(styleClass)) {
            writer.writeAttribute("class", styleClass, null);
        }
        writer.writeText("\n", null);
        
        PageSetting setting = getPageSetting(context);
        int page = setting.getPage();
        
        //scrolling information
        String infoType = (String) getAttributes().get("infoType");
        if ("row".equalsIgnoreCase(infoType)) {//show row infomation
            writer.startElement("td nowrap", null);
            writer.writeAttribute("valign", "middle", null);
            writer.writeAttribute("style", "padding-right:10px;", null);
            writer.writeText("\n", null);
            writer.writeText("Results: ", null);
            writer.startElement("span", null);
            writer.writeAttribute("style", "color:red;", null);
            writer.writeText((setting.getFirstRow()+1)+"-"+(setting.getLastRow()+1), null);
            writer.endElement("span");
            writer.writeText("/"+setting.getRowSize(), null);
        } else if ("page".equalsIgnoreCase(infoType)) {//show page information
            writer.startElement("td nowrap", null);
            writer.writeAttribute("valign", "middle", null);
            writer.writeAttribute("style", "padding-right:20px;", null);
            writer.writeText("\n", null);
            writer.writeText("Page: ", null);
            writer.startElement("span", null);
            writer.writeAttribute("style", "color:red;", null);
            writer.writeText(""+setting.getPage(), null);
            writer.endElement("span");
            writer.writeText("/"+setting.getPageSize(), null);
        }
        writer.endElement("td");
        
        writer.startElement("td nowrap", null);
        writer.writeAttribute("align", "center", null);
        writer.writeAttribute("valign", "middle", null);
        writer.writeText("\n", null);
        
        writer.startElement("table", null);
        writer.writeText("\n", null);
        writer.startElement("tr", null);
        if (styleClass!=null && !"".equals(styleClass)) {
            writer.writeAttribute("class", styleClass, null);
        }
        writer.writeText("\n", null);

        //prev screen
        writer.startElement("td nowrap", null);
        writer.writeAttribute("align", "right", null);
        writer.writeAttribute("valign", "middle", null);
        writer.writeText("\n", null);
        if (page!=1) {
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            int n = setting.getPage() - setting.getPageOfScreen();
            if (n<1) n = 1;
            writer.writeAttribute("onClick", parentId+"_scroll("+n+");", null);
        }
        writer.writeText("<<", null);
        if (page>1) {
            writer.endElement("a");
        }
        writer.endElement("td");
        writer.writeText("\n", null);
        
        //prev page
        writer.startElement("td nowrap", null);
        writer.writeAttribute("align", "right", null);
        writer.writeAttribute("valign", "middle", null);
        if (page!=1) {
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onClick", parentId+"_scroll("+(page-1)+");", null);
        }
        writer.writeText("<", null);
        if (page>1) {
            writer.endElement("a");
        }
        writer.endElement("td");
        writer.writeText("\n", null);
        
        //internal numbers
        for (int i=setting.getHead(), size=setting.getTail(); i<=size; i++) {
            writer.startElement("td nowrap", null);
            if (i!=setting.getHead()) {
                writer.writeAttribute("style", "border-left:1px solid #9999ff;", null);
            }
            writer.writeAttribute("align", "center", null);
            if (i==page) {
                writer.startElement("span", null);
                writer.writeAttribute("style", "color:red;", null);
            } else {
                writer.startElement("a", null);
                writer.writeAttribute("href", "#", null);
                writer.writeAttribute("onClick", parentId+"_scroll("+i+");", null);
            }
            writer.writeText(""+i, null);
            if (i==page) {
                writer.endElement("span");
            } else {
                writer.endElement("a");
            }
            writer.endElement("td");
            writer.writeText("\n", null);
        }//for i
        
        //next page
        writer.startElement("td nowrap", null);
        writer.writeAttribute("align", "left", null);
        if (page!=setting.getPageSize()) {
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onClick", parentId+"_scroll("+(page+1)+");", null);
        }
        writer.writeText(">", null);
        if (page>1) {
            writer.endElement("a");
        }
        writer.endElement("td");
        writer.writeText("\n", null);

        //next screen
        writer.startElement("td nowrap", null);
        writer.writeAttribute("align", "left", null);
        if (page!=setting.getPageSize()) {
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            int n = setting.getPage() + setting.getPageOfScreen();
            if (n>setting.getPageSize()) n = setting.getPageSize();
            writer.writeAttribute("onClick", parentId+"_scroll("+n+");", null);
        }
        writer.writeText(">>", null);
        if (page>1) {
            writer.endElement("a");
        }
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.endElement("tr");
        writer.endElement("table");
        writer.endElement("td");
        writer.writeText("\n", null);

        //directly go
        String showPageGo = (String) getAttributes().get("showPageGo");
        if ("true".equalsIgnoreCase(showPageGo)) {
            writer.startElement("td nowrap", null);
            writer.writeAttribute("align", "center", null);
            writer.writeAttribute("style", "padding-left:10px;", null);
            writer.writeText("Page:", null);
            writer.startElement("input", null);
            writer.writeAttribute("name", clientId+"_go", null);
            writer.writeAttribute("type", "text", null);
            writer.writeAttribute("style", "border:thin dotted #800080;width:25px;", null);
            writer.writeAttribute("onKeyDown", "if (event.which!=13) return false;"+parentId+"_scroll(document.forms["+getFormNumber(context)+"]['"+clientId+"_go'].value);", null);
            writer.writeAttribute("value", ""+setting.getPage(), null);
            writer.startElement("input", null);
            writer.writeAttribute("type", "button", null);
            
            writer.writeAttribute("onClick", parentId+"_scroll(document.forms["+getFormNumber(context)+"]['"+clientId+"_go'].value);", null);
            writer.writeAttribute("value", "Go", null);
            writer.endElement("td");
            writer.writeText("\n", null);
        }
        
        //rows per page
        String showRowsOfPage = (String) getAttributes().get("showRowsOfPage");
        if ("true".equalsIgnoreCase(showRowsOfPage)) {
            writer.startElement("td nowrap", null);
            writer.writeAttribute("align", "center", null);
            writer.writeAttribute("style", "padding-left:10px;", null);
            writer.writeText("Rows Per Page:", null);
            writer.startElement("select", null);
            //writer.writeAttribute("onChange", getScrollPage(clientId, formNumber, page+1, ACTION_ROWOFPAGE), null);
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
            writer.endElement("td");
            writer.writeText("\n", null);
        }

        writer.endElement("tr");
        writer.endElement("table");
        
    }//encodeEnd()
    

}//class ScrollerComponent

