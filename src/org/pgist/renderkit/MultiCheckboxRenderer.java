package org.pgist.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer;
import com.sun.faces.util.Util;


public class MultiCheckboxRenderer extends HtmlBasicRenderer {

    
    public MultiCheckboxRenderer() {
    }

    
    public boolean getRendersChildren() {
        return true;
    }

    
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null)
            throw new NullPointerException(Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        if (!component.isRendered()) return;
        UIData data = (UIData) component;
        data.setRowIndex(-1);
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("table", data);
        writeIdAttributeIfNecessary(context, writer, component);
        String styleClass = (String) data.getAttributes().get("styleClass");
        if (styleClass != null)
            writer.writeAttribute("class", styleClass, "styleClass");
        Util.renderPassThruAttributes(writer, component,
                new String[] { "rows" });
        writer.writeText("\n", null);
        UIComponent header = getFacet(data, "header");
        int headerFacets = getFacetCount(data, "header");
        String headerClass = (String) data.getAttributes().get("headerClass");
        if (header != null || headerFacets > 0) {
            writer.startElement("thead", data);
            writer.writeText("\n", null);
        }
        if (header != null) {
            writer.startElement("tr", header);
            writer.startElement("th", header);
            if (headerClass != null)
                writer.writeAttribute("class", headerClass, "headerClass");
            writer.writeAttribute("colspan", "" + getColumnCount(data), null);
            writer.writeAttribute("scope", "colgroup", null);
            encodeRecursive(context, header);
            writer.endElement("th");
            writer.endElement("tr");
            writer.writeText("\n", null);
        }
        if (headerFacets > 0) {
            writer.startElement("tr", data);
            writer.writeText("\n", null);
            for (Iterator columns = getColumns(data); columns.hasNext(); writer
                    .writeText("\n", null)) {
                UIColumn column = (UIColumn) columns.next();
                writer.startElement("th", column);
                if (headerClass != null)
                    writer.writeAttribute("class", headerClass, "headerClass");
                writer.writeAttribute("scope", "col", null);
                UIComponent facet = getFacet(column, "header");
                if (facet != null)
                    encodeRecursive(context, facet);
                writer.endElement("th");
            }

            writer.endElement("tr");
            writer.writeText("\n", null);
        }
        if (header != null || headerFacets > 0) {
            writer.endElement("thead");
            writer.writeText("\n", null);
        }
        UIComponent footer = getFacet(data, "footer");
        int footerFacets = getFacetCount(data, "footer");
        String footerClass = (String) data.getAttributes().get("footerClass");
        if (footer != null || footerFacets > 0) {
            writer.startElement("tfoot", data);
            writer.writeText("\n", null);
        }
        if (footer != null) {
            writer.startElement("tr", footer);
            writer.startElement("td", footer);
            if (footerClass != null)
                writer.writeAttribute("class", footerClass, "footerClass");
            writer.writeAttribute("colspan", "" + getColumnCount(data), null);
            encodeRecursive(context, footer);
            writer.endElement("td");
            writer.endElement("tr");
            writer.writeText("\n", null);
        }
        if (footerFacets > 0) {
            writer.startElement("tr", data);
            writer.writeText("\n", null);
            for (Iterator columns = getColumns(data); columns.hasNext(); writer
                    .writeText("\n", null)) {
                UIColumn column = (UIColumn) columns.next();
                writer.startElement("td", column);
                if (footerClass != null)
                    writer.writeAttribute("class", footerClass, "footerClass");
                UIComponent facet = getFacet(column, "footer");
                if (facet != null)
                    encodeRecursive(context, facet);
                writer.endElement("td");
            }

            writer.endElement("tr");
            writer.writeText("\n", null);
        }
        if (footer != null || footerFacets > 0) {
            writer.endElement("tfoot");
            writer.writeText("\n", null);
        }
    }
    

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null)
            throw new NullPointerException(
                    Util.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        if (!component.isRendered()) return;
        UIData data = (UIData) component;
        String columnClasses[] = getColumnClasses(data);
        int columnStyle = 0;
        int columnStyles = columnClasses.length;
        String rowClasses[] = getRowClasses(data);
        int rowStyles = rowClasses.length;
        ResponseWriter writer = context.getResponseWriter();
        Iterator kids = null;
        Iterator grandkids = null;
        int processed = 0;
        int rowIndex = data.getFirst() - 1;
        int rows = data.getRows();
        int rowStyle = 0;
        writer.startElement("tbody", component);
        writer.writeText("\n", null);
        boolean open = false;
        int rowCount = 0;
        int columns = 1;
        String columnsStr = (String) component.getAttributes().get("columns");
        if (columnsStr!=null && !"".equals(columnsStr)) {
            columns = Integer.parseInt(columnsStr);
        }
        do {
            if (rows > 0 && ++processed > rows) break;
            data.setRowIndex(++rowIndex);
            if (!data.isRowAvailable()) break;
            if (rowCount % columns == 0) {
                if(open) {
                    writer.endElement("tr");
                    writer.writeText("\n", null);
                    open = false;
                }
                writer.startElement("tr", component);
                if(rowStyles > 0) {
                    writer.writeAttribute("class", rowClasses[rowStyle++], "rowClasses");
                    if(rowStyle >= rowStyles)
                        rowStyle = 0;
                }
                writer.writeText("\n", null);
                open = true;
                columnStyle = 0;
            }
            columnStyle = 0;
            for (kids = getColumns(data); kids.hasNext(); writer.writeText("\n", null)) {
                UIColumn column = (UIColumn) kids.next();
                writer.startElement("td", column);
                if (columnStyles > 0) {
                    writer.writeAttribute("class", columnClasses[columnStyle++], "columnClasses");
                    if (columnStyle >= columnStyles)
                        columnStyle = 0;
                }
                for (grandkids = getChildren(column); grandkids.hasNext(); ) {
                    encodeRecursive(context, (UIComponent) grandkids.next());
                }//for grandkids
                writer.endElement("td");
                rowCount++;
            }//for kids

        } while (true);
        
        if(open) {
            for (int i=rowCount; i<columns; i++) {
                writer.startElement("td", null);
                writer.writeText("&nbsp;", null);
                writer.endElement("td");
            }//for i
            writer.endElement("tr");
            writer.writeText("\n", null);
        }

        writer.endElement("tbody");
        writer.writeText("\n", null);
        data.setRowIndex(-1);
    }//encodeChildren()
    

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null)
            throw new NullPointerException(
                    Util
                            .getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
        if (!component.isRendered()) return;
        UIData data = (UIData) component;
        data.setRowIndex(-1);
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("table");
        writer.writeText("\n", null);
    }
    

    private String[] getColumnClasses(UIData data) {
        String values = (String) data.getAttributes().get("columnClasses");
        if (values == null)
            return new String[0];
        values = values.trim();
        ArrayList list = new ArrayList();
        while (values.length() > 0) {
            int comma = values.indexOf(",");
            if (comma >= 0) {
                list.add(values.substring(0, comma).trim());
                values = values.substring(comma + 1);
            } else {
                list.add(values.trim());
                values = "";
            }
        }
        String results[] = new String[list.size()];
        return (String[]) list.toArray(results);
    }
    

    private int getColumnCount(UIData data) {
        int columns = 0;
        for (Iterator kids = getColumns(data); kids.hasNext();) {
            UIComponent kid = (UIComponent) kids.next();
            columns++;
        }

        return columns;
    }
    

    private Iterator getColumns(UIData data) {
        List results = new ArrayList();
        for (Iterator kids = data.getChildren().iterator(); kids.hasNext();) {
            UIComponent kid = (UIComponent) kids.next();
            if ((kid instanceof UIColumn) && kid.isRendered())
                results.add(kid);
        }

        return results.iterator();
    }
    

    private int getFacetCount(UIData data, String name) {
        int n = 0;
        for (Iterator kids = getColumns(data); kids.hasNext();) {
            UIComponent kid = (UIComponent) kids.next();
            if (getFacet(kid, name) != null)
                n++;
        }

        return n;
    }
    

    private String[] getRowClasses(UIData data) {
        String values = (String) data.getAttributes().get("rowClasses");
        if (values == null)
            return new String[0];
        values = values.trim();
        ArrayList list = new ArrayList();
        while (values.length() > 0) {
            int comma = values.indexOf(",");
            if (comma >= 0) {
                list.add(values.substring(0, comma).trim());
                values = values.substring(comma + 1);
            } else {
                list.add(values.trim());
                values = "";
            }
        }
        String results[] = new String[list.size()];
        return (String[]) list.toArray(results);
    }


}//class MultiCheckboxRenderer
