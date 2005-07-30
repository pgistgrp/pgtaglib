package org.pgist.util;

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
    
    
}//class ListTableBean
