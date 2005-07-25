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
    private int pageOfScreen = 20;
    private int head = 1;
    private int tail = 1;
    private Hashtable parameters = new Hashtable();
    private int[] options = {
        10,
        20,
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
            if (page<pageOfScreen/2) {//nearer from left end
                head = 1;
            } else if ((pageSize-page)<pageOfScreen/2) {//nearer from right end
                head = pageSize - pageOfScreen;
            } else {
                head = page - pageOfScreen/2;
            }
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
            lastRow = rowSize;
        }
        return lastRow;
    }
    
    
    public String get(String name) {
        return (String) parameters.get(name);
    }
    
    
    public void set(String name, String value) {
        parameters.put(name, value);
    }
    
    
}
