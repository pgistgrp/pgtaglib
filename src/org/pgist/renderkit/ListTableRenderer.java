package org.pgist.renderkit;


import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.pgist.conf.Caption;
import org.pgist.conf.ListTableTag;
import org.pgist.conf.Row;
import org.pgist.conf.Table;
import org.pgist.conf.Theme;
import org.pgist.conf.ThemeManager;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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
    
    
    public Theme getTheme() {
        return (Theme) themeVar.get();
    }
    
    
    // -------------------------------------------------------- Renderer Methods


    /**
     * <p>Render the beginning of the table for our associated data.</p>
     *
     * @param context   <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> being rendered
     *
     * @throws IOException if an input/output error occurs
     */
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        
        super.encodeBegin(context, component);
        
        ResponseWriter writer = context.getResponseWriter();
        UIData data = (UIData) component;
        
        //Get theme definition
        String theme = (String) data.getAttributes().get("theme");
        if (theme==null || "".equals(theme)) theme = "default";
        Theme theTheme = ThemeManager.getTheme(theme);
        if (theTheme==null) theTheme = ThemeManager.getTheme("default");
        themeVar.set(theTheme);
        
        // Render the beginning of this table
        data.setRowIndex(-1);
        tableBegin(context, data, writer);

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

            // Select the next row (if there is one)
            data.setRowIndex(++rowIndex);
            if (!data.isRowAvailable()) {
                break;
            }

            // Render the beginning, body, and ending of this row
            rowBegin(context, data, writer);
            rowBody(context, data, writer);
            rowEnd(context, data, writer);

        }

    }


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

        // Render the ending of this table
        data.setRowIndex(-1);
        tableEnd(context, data, writer);

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
        
        String width = (String) data.getAttributes().get("width");
        boolean hasWidth = false;
        if (width!=null && !"".equals(width)) hasWidth = true;
        for (Iterator iter=table.getProperties().entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String name = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (hasWidth && "width".equals(name)) {
                writer.writeAttribute(name, width, null);
            } else {
                writer.writeAttribute(name, value, null);
            }
        }//for iter
        
        writer.writeText("\n", null);

        //caption
        UIComponent theCaption = data.getFacet("caption");
        if (theCaption != null) {
            Caption captionTag = (Caption) listTableTag.getCaption();
            writer.startElement(captionTag.compose(), theCaption);
            encodeRecursive(context, theCaption);
            writer.endElement("caption");
            writer.writeText("\n", null);
        }
        
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
