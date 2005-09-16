package org.pgist.renderkit;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;

import org.pgist.conf.ListTableTag;
import org.pgist.conf.Row;
import org.pgist.conf.Table;
import org.pgist.conf.Theme;
import org.pgist.conf.ThemeManager;
import org.pgist.util.PageSetting;

import com.sun.faces.renderkit.html_basic.FormRenderer;

/**
 * <p><code>Renderer</code> that supports generating markup for the per-row data
 * associated with a <code>UIData</code> component.  You can easily specialize
 * the behavior of the <code>Renderer</code> by subclassing and overriding the
 * <code>tableBegin()</code>, <code>rowBegin()</code>,
 * <code>rowBody()</code>, <code>rowEnd()</code>, and <code>tableEnd()</code>
 * methods.  The default implementation renders an HTML table with
 * headers and footers.</p>
 * 
 * @author kenny
 *
 */
public class ListTableRenderer extends BaseRenderer {

    
    private static ThreadLocal themeVar = new ThreadLocal();
    public static final String FORM_NUMBER_ATTR = "com.sun.faces.FormNumber";
    public boolean postMode = false;
    
    
    public Theme getTheme() {
        return (Theme) themeVar.get();
    }

    
    public void decode(FacesContext context, UIComponent component) throws NullPointerException {
        postMode = true;
        
        PageSetting setting = (PageSetting) component.getValueBinding("pageSetting").getValue(context);
        String clientId = component.getClientId(context);

        Map requestParameterMap = (Map) context.getExternalContext().getRequestParameterMap();
        String curPage = (String) requestParameterMap.get(clientId + "_page");
        try {
            int currentPage = Integer.valueOf(curPage).intValue();
            setting.setPage(currentPage);
        } catch(Exception e) {
            setting.setPage(1);
        }

        String rowOfPageStr = (String) requestParameterMap.get(clientId + "_rowOfPage");
        try {
            int rowOfPage = Integer.valueOf(rowOfPageStr).intValue();
            setting.setRowOfPage(rowOfPage);
        } catch(Exception e) {
        }
    }//decode()
    
    
    // -------------------------------------------------------- Renderer Methods


    /**
     * <p>Render the beginning of the table for our associated data.</p>
     *
     * @param context   <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> being rendered
     *
     * @throws IOException if an input/output error occurs
     */
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        
        super.encodeBegin(context, component);
        
        ResponseWriter writer = context.getResponseWriter();
        UIData data = (UIData) component;
        
        /*
         * The actionBinding is used by the component to grab data instead of using prepared data by
         * other program such as backing bean. With this binding the page itself can grab data directly. 
         */
        String binding = (String) data.getAttributes().get("actionBinding");
        MethodBinding mb = context.getApplication().createMethodBinding(binding, new Class[] { ActionEvent.class, Map.class });
        Map map = new HashMap();
        map.put("postMode", ""+postMode);
        mb.invoke(context, new Object[] { new ActionEvent(component), map });
        
        //Get theme definition
        String theme = (String) data.getAttributes().get("theme");
        if (theme==null || "".equals(theme)) theme = "default";
        Theme theTheme = ThemeManager.getTheme(theme);
        if (theTheme==null) theTheme = ThemeManager.getTheme("default");
        themeVar.set(theTheme);
        
        //java script
        String clientId = component.getClientId(context);
        String tagId = (String) data.getAttributes().get("id");
        int formNumber = getFormNumber(context);
        writer.startElement("script", null);
        writer.writeAttribute("type", "text/javascript", null);
        writer.writeText("\n", null);
        writer.writeText("<!--", null);
        writer.writeText("\n", null);
        writer.writeText("function "+tagId+"_scroll(page) {", null);
        writer.writeText("document.forms[" + formNumber + "]['" + clientId + "_page'].value=page;", null);
        writer.writeText("document.forms[" + formNumber + "]['" + getMyForm(context, component).getClientId(context) + ":_idcl'].value='';", null);
        writer.writeText("document.forms[" + formNumber + "].submit();", null);
        writer.writeText("}", null);
        writer.writeText("function "+tagId+"_changeRowOfPage(n) {", null);
        writer.writeText("document.forms[" + formNumber + "]['" + clientId + "_page'].value=1;", null);
        writer.writeText("document.forms[" + formNumber + "]['" + clientId + "_rowOfPage'].value=n;", null);
        writer.writeText("document.forms[" + formNumber + "]['" + getMyForm(context, component).getClientId(context) + ":_idcl'].value='';", null);
        writer.writeText("document.forms[" + formNumber + "].submit();", null);
        writer.writeText("}", null);
        writer.writeText("\n", null);
        writer.writeText("//-->", null);
        writer.writeText("\n", null);
        writer.endElement("script");
        writer.writeText("\n", null);

        PageSetting setting = (PageSetting) component.getValueBinding("pageSetting").getValue(context);
        //page
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_page", null);
        writer.writeAttribute("value", ""+setting.getPage(), null);
        writer.endElement("input");
        //rowOfPage
        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId+"_rowOfPage", null);
        writer.writeAttribute("value", ""+setting.getRowOfPage(), null);
        writer.endElement("input");
        
        //Render the outmost table
        writer.startElement("table", data);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        String width = (String) data.getAttributes().get("width");
        if (width!=null && !"".equals(width)) {
            writer.writeAttribute("width", width, null);
        } else {
            writer.writeAttribute("width", "100%", null);
        }
        writer.writeText("\n", null);
        
        //Render topScroller
        writer.startElement("tr", data);
        writer.startElement("td", data);
        writer.writeAttribute("width", "100%", null);
        writer.startElement("table", data);
        writer.writeAttribute("width", "100%", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        writer.startElement("tr", data);
        renderTopScroller(context, data, writer);
        writer.writeText("\n", null);
        
        //Render filter
        renderFilter(context, data, writer);
        writer.endElement("tr");
        writer.endElement("table");
        writer.endElement("td");
        writer.endElement("tr");
        writer.writeText("\n", null);
        
        //Render title
        writer.startElement("tr", data);
        writer.startElement("td", data);
        writer.startElement("table", data);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("width", "100%", null);
        writer.writeText("\n", null);
        writer.startElement("tr", data);
        renderTitle(context, data, writer);
        
        //Render toolbar
        renderToolbar(context, data, writer);
        writer.endElement("tr");
        writer.endElement("table");
        writer.endElement("td");
        writer.endElement("tr");
        writer.writeText("\n", null);
        
        //Render the content table
        writer.startElement("tr", data);
        writer.startElement("td", data);
        data.setRowIndex(-1);
        tableBegin(context, data, writer);
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
    

    protected UIForm getMyForm(FacesContext context, UIComponent component) {
        UIComponent parent;
        for(parent = component.getParent(); parent != null; parent = parent.getParent()) {
            if(parent instanceof UIForm) break;
        }

        return (UIForm)parent;
    }

    
    protected String getHiddenFieldName(FacesContext context, UIComponent component) {
        UIForm uiform = getMyForm(context, component);
        String formClientId = uiform.getClientId(context);
        return formClientId + ':' + "_id" + "cl";
    }


    /**
     * render the top scroller component
     * @param context
     * @param data
     * @param writer
     */
    private void renderTopScroller(FacesContext context, UIData data, ResponseWriter writer) throws IOException {
        UIComponent topScroller = data.getFacet("topScroller");
        if (topScroller != null) {
            writer.startElement("td", topScroller);
            writer.writeAttribute("align", "left", null);
            encodeRecursive(context, topScroller);
            writer.endElement("td");
        }
    }//renderTopScroller()


    /**
     * render the top scroller component
     * @param context
     * @param data
     * @param writer
     */
    private void renderBottomScroller(FacesContext context, UIData data, ResponseWriter writer) throws IOException {
        UIComponent buttomScroller = data.getFacet("bottomScroller");
        if (buttomScroller != null) {
            writer.startElement("td", buttomScroller);
            writer.writeAttribute("align", "left", null);
            encodeRecursive(context, buttomScroller);
            writer.endElement("td");
        }
    }//renderBottomScroller()


    /**
     * 
     * @param context
     * @param data
     * @param writer
     */
    private void renderFilter(FacesContext context, UIData data, ResponseWriter writer) throws IOException {
        UIComponent filter = data.getFacet("filter");
        if (filter != null) {
            writer.startElement("td", filter);
            writer.writeAttribute("align", "right", null);
            encodeRecursive(context, filter);
            writer.endElement("td");
        }
    }


    private void renderTitle(FacesContext context, UIData data, ResponseWriter writer) throws IOException {
        UIComponent title = data.getFacet("title");
        if (title != null) {
            writer.startElement("td", title);
            writer.startElement("b", title);
            writer.writeAttribute("align", "left", null);
            encodeRecursive(context, title);
            writer.endElement("b");
            writer.endElement("td");
        }
    }


    private void renderToolbar(FacesContext context, UIData data, ResponseWriter writer) throws IOException {
        UIComponent toolbar = data.getFacet("toolbar");
        if (toolbar != null) {
            writer.startElement("td", toolbar);
            writer.writeAttribute("align", "right", null);
            encodeRecursive(context, toolbar);
            writer.endElement("td");
        }
    }


    /**
     * <p>Render the body rows of the table for our associated data.</p>
     *
     * @param context   <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> being rendered
     *
     * @throws IOException if an input/output error occurs
     */
    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {

        super.encodeChildren(context, component);
        ResponseWriter writer = context.getResponseWriter();
        UIData data = (UIData) component;

        int processed = 0;
        int rowIndex = data.getFirst() - 1;
        int rows = data.getRows();

        // Iterate over the specified rows of data
        while (true) {

            // Have we displayed the requested number of rows?
            if ((rows > 0) && (++processed > rows)) {
                break;
            }

            // Select the next row (if there is scroller2one)
            data.setRowIndex(++rowIndex);
            if (!data.isRowAvailable()) {
                break;
            }

            // Render the beginning, body, and ending of this row
            rowBegin(context, data, writer);
            rowBody(context, data, writer);
            rowEnd(context, data, writer);

        }

    }//encodeChildren()


    /**
     * <p>Render the ending of the table for our associated data.</p>
     *
     * @param context   <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> being rendered
     *
     * @throws IOException if an input/output error occurs
     */
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {

        super.encodeEnd(context, component);
        ResponseWriter writer = context.getResponseWriter();
        UIData data = (UIData) component;

        data.setRowIndex(-1);
        tableEnd(context, data, writer);
        writer.endElement("td");
        writer.endElement("tr");

        //Render bottomScroller
        writer.startElement("tr", data);
        writer.startElement("td", data);
        writer.writeAttribute("width", "100%", null);
        writer.startElement("table", data);
        writer.writeAttribute("width", "100%", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("border", "0", null);
        writer.startElement("tr", data);
        renderBottomScroller(context, data, writer);

        //Render filter
        renderFilter(context, data, writer);
        writer.endElement("tr");
        writer.endElement("table");
        writer.endElement("td");
        writer.endElement("tr");
        writer.writeText("\n", null);

        //end the outmost table
        writer.endElement("table");
        FormRenderer.addNeededHiddenField(context, getHiddenFieldName(context, component));
    }


    /**
     * <p>Return <code>true</code> to indicate that we do indeed wish to be
     * responsible for rendering the children of the associated component.</p>
     */
    public boolean getRendersChildren() {

        return (true);

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Return the number of child components of type <code>UIColumn</code>
     * are registered with the specified <code>UIData</code> component.</p>
     *
     * @param data <code>UIData</code> component for which to count
     */
    protected int getColumnCount(UIData data) {

        int n = 0;
        Iterator kids = data.getChildren().iterator();
        while (kids.hasNext()) {
            if (kids.next() instanceof UIColumn) {
                n++;
            }
        }
        return (n);

    }


    /**
     * <p>Return the number of child components of type <code>UIColumn</code>
     * are registered with the specified <code>UIData</code> component
     * and have a facet named <code>footer</code>.</p>
     *
     * @param data <code>UIData</code> component for which to count
     */
    protected int getColumnFooterCount(UIData data) {

        int n = 0;
        Iterator kids = data.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            if ((kid instanceof UIColumn) &&
                (kid.getFacet("footer") != null)) {
                n++;
            }
        }
        return (n);

    }


    /**
     * <p>Return the number of child components of type <code>UIColumn</code>
     * are registered with the specified <code>UIData</code> component
     * and have a facet named <code>header</code>.</p>
     *
     * @param data <code>UIData</code> component for which to count
     */
    protected int getColumnHeaderCount(UIData data) {

        int n = 0;
        Iterator kids = data.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            if ((kid instanceof UIColumn) &&
                (kid.getFacet("header") != null)) {
                n++;
            }
        }
        return (n);

    }


    /**
     * <p>Render the markup for the beginning of the current body row.  The
     * default implementation renders <code>&lt;tr&gt;</code>.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param data    <code>UIData</code> being rendered
     * @param writer  <code>ResponseWriter</code> to render to
     *
     * @exception IOException if an input/output error occurs
     */
    protected void rowBegin(FacesContext context, UIData data,
                            ResponseWriter writer) throws IOException {

        Theme theme = getTheme();
        ListTableTag listTableTag = (ListTableTag) theme.getTag("listTable");
        Row row = (Row) listTableTag.getRow();

        int alter = row.alterSize();
        String alterColor = row.getColor(data.getRowIndex() % alter);
        writer.startElement("tr", data);
        writer.writeAttribute("style", "background-color:"+alterColor+";", null);
        writer.writeAttribute("onmouseover", "this.style.backgroundColor='"+row.getHighlight()+"'", null);
        writer.writeAttribute("onmouseout", "this.style.backgroundColor='"+alterColor+"'", null);

        writer.writeText("\n", null);
    }


    /**
     * <p>Render the markup for the content of the current body row.  The
     * default implementation renders the descendant components of each
     * child <code>UIColumn</code>, surrounded by <code>&lt;td&gt;</code>
     * and <code>&lt;/td&gt;</code>.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param data    <code>UIData</code> being rendered
     * @param writer  <code>ResponseWriter</code> to render to
     *
     * @exception IOException if an input/output error occurs
     */
    protected void rowBody(FacesContext context, UIData data,
                           ResponseWriter writer) throws IOException {

        // Iterate over the UIColumn children of this UIData component
        Iterator columns = data.getChildren().iterator();
        while (columns.hasNext()) {

            // Only process UIColumn children
            UIComponent column = (UIComponent) columns.next();
            if (!(column instanceof UIColumn)) {
                continue;
            }

            // Create the markup for this column
            writer.startElement("td", column);
            Iterator contents = column.getChildren().iterator();
            while (contents.hasNext()) {
                encodeRecursive(context, (UIComponent) contents.next());
            }
            writer.endElement("td");
            writer.writeText("\n", null);

        }

    }


    /**
     * <p>Render the markup for the ending of the current body row.  The
     * default implementation renders <code>&lt;/tr&gt;</code>.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param data    <code>UIData</code> being rendered
     * @param writer  <code>ResponseWriter</code> to render to
     *
     * @exception IOException if an input/output error occurs
     */
    protected void rowEnd(FacesContext context, UIData data,
                          ResponseWriter writer) throws IOException {

        writer.endElement("tr");
        writer.writeText("\n", null);

    }


    /**
     * <p>Render the markup for the beginnning of an entire table.  The
     * default implementation renders:</p>
     * <ul>
     * <li>A <code>&lt;table&gt;</code> element.</li>
     * <li>If the <code>UIData</code> component has a facet named
     * <code>header</code>, render it in a table row with a
     * <code>colspan</code> set to span all the columns in the table.</li>
     * <li>If any of the child <code>UIColumn</code> components has a facet
     * named <code>header</code>, render them in a table row with a
     * each header in a <code>&lt;th&gt;</code> element.</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param data    <code>UIData</code> being rendered
     * @param writer  <code>ResponseWriter</code> to render to
     *
     * @exception IOException if an input/output error occurs
     */
    protected void tableBegin(FacesContext context, UIData data,
                              ResponseWriter writer) throws IOException {

        // Render the outermost table element
        writer.startElement("table", data);
        
        Theme theme = getTheme();
        ListTableTag listTableTag = (ListTableTag) theme.getTag("listTable");
        Table table = listTableTag.getTable();
        
        for (Iterator iter=table.getProperties().entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String name = (String) entry.getKey();
            String value = (String) entry.getValue();
            writer.writeAttribute(name, value, null);
        }//for iter
        
        writer.writeAttribute("width", "100%", null);
        writer.writeText("\n", null);

        // Render the table and column headers (if any)
        UIComponent theHeader = data.getFacet("header");
        int n = getColumnHeaderCount(data);
        if ((theHeader != null) || (n > 0)) {
            writer.startElement("thead", theHeader);
        }
        if (theHeader != null) {
            writer.startElement("tr", theHeader);
            writer.startElement("th", theHeader);
            writer.writeAttribute("colspan", "" + getColumnCount(data), null);
            writer.writeText("\n", null);
            encodeRecursive(context, theHeader);
            writer.writeText("\n", null);
            writer.endElement("th");
            writer.endElement("tr");
            writer.writeText("\n", null);
        }
        
        if (n > 0) {
            writer.startElement("tr", data);
            writer.writeText("\n", null);
            Iterator columns = data.getChildren().iterator();
            while (columns.hasNext()) {
                UIComponent column = (UIComponent) columns.next();
                if (!(column instanceof UIColumn)) {
                    continue;
                }
                writer.startElement("th", column);
                UIComponent facet = column.getFacet("header");
                if (facet != null) {
                    encodeRecursive(context, facet);
                }
                writer.endElement("th");
                writer.writeText("\n", null);
            }
            writer.endElement("tr");
            writer.writeText("\n", null);
        }
        if ((theHeader != null) || (n > 0)) {
            writer.endElement("thead");
            writer.writeText("\n", null);
        }

        // Render the beginning of the table body
        writer.startElement("tbody", data);
        writer.writeText("\n", null);

    }


    /**
     * <p>Render the markup for the ending of an entire table.  The
     * default implementation renders:</p>
     * <ul>
     * <li>If any of the child <code>UIColumn</code> components has a facet
     * named <code>footer</code>, render them in a table row with a
     * each footer in a <code>&lt;th&gt;</code> element.</li>
     * <li>If the <code>UIData</code> component has a facet named
     * <code>footer</code>, render it in a table row with a
     * <code>colspan</code> set to span all the columns in the table.</li>
     * <li>A <code>&lt;/table&gt;</code> element.</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param data    <code>UIData</code> being rendered
     * @param writer  <code>ResponseWriter</code> to render to
     *
     * @exception IOException if an input/output error occurs
     */
    protected void tableEnd(FacesContext context, UIData data,
                            ResponseWriter writer) throws IOException {

        // Render the end of the table body
        writer.endElement("tbody");
        writer.writeText("\n", null);

        // Render the table and column footers (if any)
        UIComponent footer = data.getFacet("footer");
        int n = getColumnFooterCount(data);
        if ((footer != null) || (n > 0)) {
            writer.startElement("tfoot", footer);
        }
        if (n > 0) {
            writer.startElement("tr", data);
            writer.writeText("\n", null);
            Iterator columns = data.getChildren().iterator();
            while (columns.hasNext()) {
                UIComponent column = (UIComponent) columns.next();
                if (!(column instanceof UIColumn)) {
                    continue;
                }
                writer.startElement("th", column);
                UIComponent facet = column.getFacet("footer");
                if (facet != null) {
                    encodeRecursive(context, facet);
                }
                writer.endElement("th");
                writer.writeText("\n", null);
            }
            writer.endElement("tr");
            writer.writeText("\n", null);
        }
        if (footer != null) {
            writer.startElement("tr", footer);
            writer.startElement("th", footer);
            writer.writeAttribute("colspan", "" + getColumnCount(data), null);
            writer.writeText("\n", null);
            encodeRecursive(context, footer);
            writer.writeText("\n", null);
            writer.endElement("th");
            writer.endElement("tr");
            writer.writeText("\n", null);
        }
        if ((footer != null) || (n > 0)) {
            writer.endElement("tfoot");
            writer.writeText("\n", null);
        }

        // Render the ending of the outermost table element
        writer.endElement("table");
        writer.writeText("\n", null);

    }


}//class ListTableRenderer
