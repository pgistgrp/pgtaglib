package org.pgist.util;

import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectBoolean;

public class ListTableBean {

    
    protected PageSetting pageSetting = new PageSetting();
    protected UIData data = null;
    protected UIInput accountId = null;
    protected UISelectBoolean checked = null;
    
    
    public UIInput getAccountId() {
        return accountId;
    }
    
    
    public void setAccountId(UIInput accountId) {
        this.accountId = accountId;
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
