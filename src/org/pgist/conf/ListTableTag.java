package org.pgist.conf;


/**
 * 
 * @author kenny
 *
 */
public class ListTableTag extends Tag {

    
    private Table    table;
    private Caption  caption;
    private Header   header;
    private Row      row;
    private Footer   footer;
    
    
    public ListTableTag() {
        name ="listTable";
    }
    
    public Table getTable() {
        return table;
    }


    public void setTable(Table table) {
        this.table = table;
    }


    public Caption getCaption() {
        return caption;
    }


    public void setCaption(Caption caption) {
        this.caption = caption;
    }


    public Header getHeader() {
        return header;
    }


    public void setHeader(Header header) {
        this.header = header;
    }

    
    public Footer getFooter() {
        return footer;
    }
    

    public void setFooter(Footer footer) {
        this.footer = footer;
    }
    

    public Row getRow() {
        return row;
    }
    

    public void setRow(Row row) {
        this.row = row;
    }
    

}
