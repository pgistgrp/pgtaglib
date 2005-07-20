package org.pgist.util;

import java.util.Hashtable;


/**
 * 
 * @author kenny
 *
 */
public class PageSetting {

    
    private int page = 1;
    private int rowSize = 0;
    private int pageSize = 0;
    private int rowOfPage = 20;
    private Hashtable parameters = new Hashtable();
    
    
    public PageSetting() {
    }


    public PageSetting(int page) {
        if (page<1) {
            this.page = 1;
        } else {
            this.page = page;
        }
    }

    
    public int getPage() {
        return page;
    }


    public void setPage(int page) {
        this.page = page;
    }


    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public Hashtable getParameters() {
        return parameters;
    }


    public void setParameters(Hashtable parameters) {
        this.parameters = parameters;
    }


    public int getRowOfPage() {
        return rowOfPage;
    }


    public void setRowOfPage(int rowOfPage) {
        this.rowOfPage = rowOfPage;
    }


    public int getRowSize() {
        return rowSize;
    }


    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
        pageSize = (int)Math.ceil(((float)rowSize)/rowOfPage);
        if (page>pageSize) {
            page = pageSize;
        }
    }
    
    
    public int getFirstRow() {
        int firstRow = (page-1)*rowOfPage;
        if (firstRow<0) {
            firstRow = 0;
        }
        return firstRow;
    }
    
    
    public String get(String name) {
        return (String) parameters.get(name);
    }
    
    
    public void set(String name, String value) {
        parameters.put(name, value);
    }
    
    
}
