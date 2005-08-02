package org.pgist.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectBoolean;

public class ListTableBean {

    
    protected PageSetting pageSetting = new PageSetting();
    protected UIData data = null;
    protected UIInput objectId = null;
    protected UISelectBoolean checked = null;
    
    
    public UIInput getObjectId() {
        return objectId;
    }
    
    
    public void setObjectId(UIInput objectId) {
        this.objectId = objectId;
    }
    
    
    public UISelectBoolean getChecked() {
        return checked;
    }
    
    
    public void setChecked(UISelectBoolean checked) {
        this.checked = checked;
    }
    
    
    public UIData getData() {
        return data;
    }
    
    
    public void setData(UIData data) {
        this.data = data;
    }
    
    
    public PageSetting getPageSetting() {
        return pageSetting;
    }
    
    
    public void setPageSetting(PageSetting pageSetting) {
        this.pageSetting = pageSetting;
    }
    
    
    public Long selectedId() {
        return (Long) objectId.getValue();
    }//getSelectedId()
    
    
    public List selectedIds(Class rowClass, String property) {
        List idList = new ArrayList();
        
        try {
            StringBuffer methodName = new StringBuffer("get");
            methodName.append(property.toUpperCase().charAt(0)).append(property.substring(1));
            Method method = rowClass.getMethod(methodName.toString(), null);
            
            int n = data.getRowCount();
            for (int i = 0; i < n; i++) {
                data.setRowIndex(i);
                if (checked.isSelected()) {
                    idList.add(method.invoke(data.getRowData(), null));
                }
            }//for i
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return idList;
    }//selectedIds()
    
    
}//class ListTableBean

