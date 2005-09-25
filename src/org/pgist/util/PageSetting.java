package org.pgist.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author kenny
 *
 */
public class PageSetting {

    
    private int page = 1;
    private int rowSize = 0;
    private int pageSize = 0;
    private int rowOfPage = 10;
    private int pageOfScreen = 10;
    private int head = 1;
    private int tail = 1;
    private Map parameters = new HashMap();
    private int[] options = {
        10,
        15,
        20,
        25,
        30,
        50,
        80,
        100,
        200
    };
    
    
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


    public Map getParameters() {
        return parameters;
    }


    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }


    public int getRowOfPage() {
        return rowOfPage;
    }


    public void setRowOfPage(int rowOfPage) {
        this.rowOfPage = rowOfPage;
    }


    public int getPageOfScreen() {
        return pageOfScreen;
    }


    public void setPageOfScreen(int pageOfScreen) {
        this.pageOfScreen = pageOfScreen;
    }


    public int getRowSize() {
        return rowSize;
    }
    
    
    public int getHead() {
        return head;
    }


    public int getTail() {
        return tail;
    }
    
    public int[] getOptions() {
        return options;
    }


    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
        pageSize = (int)Math.ceil(((float)rowSize)/rowOfPage);
        
        if (page<=0) {
            page = 1;
        } else if (page>pageSize) {
            page = pageSize;
        }
        
        if (pageSize<=pageOfScreen) {//we have only a few pages
            head = 1;
        } else {//too many pages
            head = (page / pageOfScreen);
            if (page % pageOfScreen==0) head--;
            head = head * pageOfScreen + 1;
        }
        tail = head + pageOfScreen - 1;
        if (tail > pageSize) tail = pageSize;
    }//setRowSize()
    
    
    public int getFirstRow() {
        int firstRow = (page-1)*rowOfPage;
        if (firstRow<0) {
            firstRow = 0;
        }
        return firstRow;
    }
    
    
    public int getLastRow() {
        int lastRow = getFirstRow()+rowOfPage-1;
        if (lastRow>rowSize) {
            lastRow = rowSize-1;
        }
        return lastRow;
    }
    
    
    public Object get(String name) {
        return parameters.get(name);
    }
    
    
    public void set(String name, Object value) {
        parameters.put(name, value);
    }
    
    
}
