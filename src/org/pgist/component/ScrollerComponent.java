package org.pgist.component;

import org.pgist.conf.Image;
import org.pgist.conf.ScrollerTag;
import org.pgist.conf.Theme;
import org.pgist.conf.ThemeManager;
import org.pgist.renderkit.Util;
import org.pgist.util.PageSetting;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;

import java.io.IOException;
import java.util.Map;


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
        System.out.println("@@@@@@@@@@  ScrollerComponent : decode");
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
    

    public void encodeBegin(FacesContext context) throws IOException {
        return;
    }
    

    public void encodeEnd(FacesContext context) throws IOException {
        System.out.println("@@@@@@@@@@  ScrollerComponent : encodeEnd");
        //Get theme definition
        String themeName = (String) getAttributes().get("theme");
        if (themeName==null || "".equals(themeName)) themeName = "default";
        Theme theme = ThemeManager.getTheme(themeName);
        if (theme==null) theme = ThemeManager.getTheme("default");
        
        String clientId = getClientId(context);
        int formNumber = getFormNumber(context);
        
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("table", null);
        
        ScrollerTag scrollerTag = (ScrollerTag) theme.getTag("scroller");
        Image image = scrollerTag.getImage();
        
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
        System.out.println("head: "+setting.getHead()+" | tail: "+setting.getTail());
        
        writer.startElement("td", null);
        writer.writeAttribute("style", "padding-right:20px; border-right:1px dotted #FF0000", null);
        writer.writeText("Result:", null);
        writer.startElement("br", null);
        writer.startElement("span style=\"color:red;\"", null);
        writer.writeText(""+setting.getPageSize(), null);
        writer.endElement("span");
        writer.writeText(" pages", null);
        
        //prev
        writer.startElement("td", null);
        writer.writeAttribute("align", "right", null);
        String imgURL = "";
        if (page==1) {
            imgURL = image.getProperty("pgi");
        } else {
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onmousedown", getScrollPage(clientId, formNumber, page-1, ACTION_JUMP), null);
            imgURL = image.getProperty("pgi1");
        }
        writer.startElement("img", null);
        writer.writeAttribute("src", "/pgist/images/"+imgURL, null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("width", "60", null);
        writer.writeAttribute("height", "18", null);
        writer.startElement("br", null);
        writer.writeText("Prev", null);
        if (page>1) {
            writer.endElement("a");
        }
        writer.endElement("td");
        
        //internal numbers
        for (int i=setting.getHead(), size=setting.getTail(); i<=size; i++) {
            writer.startElement("td", null);
            writer.writeAttribute("align", "center", null);
            String s;
            if (i==page) {
                writer.startElement("span", null);
                writer.writeAttribute("style", "color:red;", null);
                s = "s1";
            } else {
                writer.startElement("a", null);
                writer.writeAttribute("href", "#", null);
                writer.writeAttribute("onmousedown", getScrollPage(clientId, formNumber, i, ACTION_JUMP), null);
                s = "s";
            }
            writer.startElement("img", null);
            writer.writeAttribute("src", "/pgist/images/"+image.getProperty(s), null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "12", null);
            writer.writeAttribute("height", "18", null);
            writer.startElement("br", null);
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
        writer.writeAttribute("style", "border-right:1px dotted #FF0000", null);
        writer.writeAttribute("align", "left", null);
        imgURL = "";
        if (page==setting.getPageSize()) {
            imgURL = image.getProperty("t");
        } else {
            writer.startElement("a", null);
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onmousedown", getScrollPage(clientId, formNumber, page+1, ACTION_JUMP), null);
            imgURL = image.getProperty("t1");
        }
        writer.startElement("img", null);
        writer.writeAttribute("src", "/pgist/images/"+imgURL, null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("width", "50", null);
        writer.writeAttribute("height", "18", null);
        writer.startElement("br", null);
        writer.writeText("Next", null);
        if (page>1) {
            writer.endElement("a");
        }
        writer.endElement("td");

        //directly go
        writer.startElement("td", null);
        writer.writeAttribute("align", "center", null);
        writer.writeAttribute("style", "padding-left:20px; padding-right:20px; border-right:1px dotted #FF0000", null);
        writer.writeText("Page:", null);
        writer.startElement("input", null);
        writer.writeAttribute("type", "text", null);
        writer.writeAttribute("style", "border:thin dotted #800080;width:40px;", null);
        writer.startElement("br", null);
        writer.startElement("input", null);
        writer.writeAttribute("type", "submit", null);
        writer.writeAttribute("value", "Go", null);
        
        //rows per page
        writer.startElement("td", null);
        writer.writeAttribute("align", "center", null);
        writer.writeAttribute("style", "padding-left:20px;", null);
        writer.writeText("Rows Per Page:", null);
        writer.startElement("br", null);
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


    public boolean getRendersChildren() {
        return true;
    }
    

    //
    // Helper methods
    // 

    /**
     * Write the markup to render a navigation widget.  Override this to
     * replace the default navigation widget of link with something
     * else.
     */
    protected void writeNavWidgetMarkup(FacesContext context, String clientId,
            int navActionType, boolean enabled) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String facetOrientation = "NORTH";
        String facetName = null;
        String linkText = null;
        String localLinkText = null;
        UIComponent facet = null;
        boolean isCurrentPage = false;
        boolean isPageNumber = false;

        // Assign values for local variables based on the navActionType
        switch (navActionType) {
            case 1:
                facetName = "next";
                linkText = "Next";
                break;
            case 2:
                facetName = "previous";
                linkText = "Previous";
                break;
            default:
                facetName = "number";
                linkText = "" + navActionType;
                isPageNumber = true;
                // heuristic: if navActionType is number, and we are not
                // enabled, this must be the current page.
                if (!enabled) {
                    facetName = "current";
                    isCurrentPage = true;
                }
                break;
        }

        // leverage any navigation facets we have
        writer.write("\n&nbsp;");
        if (enabled) {
            //writer.write("<a "
            //        + getAnchorAttrs(context, clientId, navActionType) + ">");
        }

        facet = getFacet(facetName);
        // render the facet pertaining to this widget type in the NORTH
        // and WEST cases.
        if (facet != null) {
            // If we're rendering a "go to the Nth page" link
            if (isPageNumber) {
                // See if the user specified an orientation
                String facetO = (String) getAttributes().get(
                        "FACET_MARKUP_ORIENTATION_ATTR");
                if (facet != null) {
                    facetOrientation = facetO;
                    // verify that the orientation is valid
                    if (!(facetOrientation.equalsIgnoreCase("NORTH")
                            || facetOrientation.equalsIgnoreCase("SOUTH")
                            || facetOrientation.equalsIgnoreCase("EAST") || facetOrientation
                            .equalsIgnoreCase("WEST"))) {
                        facetOrientation = "NORTH";
                    }
                }
            }

            // output the facet as specified in facetOrientation
            if (facetOrientation.equalsIgnoreCase("NORTH")
                    || facetOrientation.equalsIgnoreCase("EAST")) {
                facet.encodeBegin(context);
                if (facet.getRendersChildren()) {
                    facet.encodeChildren(context);
                }
                facet.encodeEnd(context);
            }
            // The difference between NORTH and EAST is that NORTH
            // requires a <br>.
            if (facetOrientation.equalsIgnoreCase("NORTH")) {
                writer.startElement("br", null); // PENDING(craigmcc)
                writer.endElement("br");
            }
        }

        // if we have a facet, only output the link text if
        // navActionType is number
        if (null != facet) {
            if (navActionType != 1
                    && navActionType != 2) {
                writer.write(linkText);
            }
        } else {
            writer.write(linkText);
        }

        // output the facet in the EAST and SOUTH cases
        if (null != facet) {
            if (facetOrientation.equalsIgnoreCase("SOUTH")) {
                writer.startElement("br", null); // PENDING(craigmcc)
                writer.endElement("br");
            }
            // The difference between SOUTH and WEST is that SOUTH
            // requires a <br>.
            if (facetOrientation.equalsIgnoreCase("SOUTH")
                    || facetOrientation.equalsIgnoreCase("WEST")) {
                facet.encodeBegin(context);
                if (facet.getRendersChildren()) {
                    facet.encodeChildren(context);
                }
                facet.encodeEnd(context);
            }
        }

        if (enabled) {
            writer.write("</a>");
        }

    }//writeNavWidgetMarkup()
    

    // PENDING: avoid doing this each time called.  Perhaps
    // store in our own attr?
    protected UIForm getForm(FacesContext context) {
        UIComponent parent = this.getParent();
        while (parent != null) {
            if (parent instanceof UIForm) {
                break;
            }
            parent = parent.getParent();
        }
        return (UIForm) parent;
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
    }
    

}

