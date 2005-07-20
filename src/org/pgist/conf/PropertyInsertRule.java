package org.pgist.conf;

import org.apache.commons.digester.Rule;


/**
 * 
 * @author kenny
 *
 */
public class PropertyInsertRule extends Rule {

    
    public void begin(String namespace, String name, org.xml.sax.Attributes attrs) {
        Object obj = null;
        if ("table".equals(name)) {
            obj = new Table();
        } else if ("caption".equals(name)) {
            obj = new Caption();
        } else if ("header".equals(name)) {
            obj = new Header();
        } else if ("row".equals(name)) {
            obj = new Row();
        } else if ("footer".equals(name)) {
            obj = new Footer();
        } else if ("image".equals(name)) {
            obj = new Image();
        }
        if (obj!=null) {
            digester.push(obj);
        } else {
            System.out.println("PGIST: element "+name+" not known!");
        }
    }


    /* (non-Javadoc)
     * @see org.apache.commons.digester.Rule#end(java.lang.String, java.lang.String)
     */
    public void end(String namespace, String name) {
        Tag set = (Tag) digester.pop();
        if ("table".equals(name)) {
            Table table = (Table) set;
            ListTableTag listTable = (ListTableTag) digester.peek();
            listTable.setTable(table);
        } else if ("caption".equals(name)) {
            Caption caption = (Caption) set;
            ListTableTag listTable = (ListTableTag) digester.peek();
            listTable.setCaption(caption);
        } else if ("header".equals(name)) {
            Header header = (Header) set;
            ListTableTag listTable = (ListTableTag) digester.peek();
            listTable.setHeader(header);
        } else if ("row".equals(name)) {
            Row row = (Row) set;
            ListTableTag listTable = (ListTableTag) digester.peek();
            listTable.setRow(row);
        } else if ("footer".equals(name)) {
            Footer footer = (Footer) set;
            ListTableTag listTable = (ListTableTag) digester.peek();
            listTable.setFooter(footer);
        } else if ("image".equals(name)) {
            Image image = (Image) set;
            ScrollerTag scrollerTag = (ScrollerTag) digester.peek();
            scrollerTag.setImage(image);
        }
    }
    
    
}
