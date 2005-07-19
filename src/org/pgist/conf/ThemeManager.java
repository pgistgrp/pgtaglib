package org.pgist.conf;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;


/**
 * 
 * @author kenny
 *
 */
public class ThemeManager {
    
    
    public static final String config = "org.pgist.THEME_MANAGER_PATH";
    private static Map themes = new HashMap();
    
    
    static {
        FacesContext facescontext = FacesContext.getCurrentInstance();
        if(facescontext != null) {
            ExternalContext context = facescontext.getExternalContext();
            String configPath = (String) context.getInitParameter(config);
            if (configPath!=null) {
                configPath = configPath.trim();
                System.out.println("PGIST: import theme file "+configPath);
            } else {
                configPath = "";
                System.out.println("PGIST: context parameter "
                        + config
                        + " not found, use default theme!");
            }
            
            //read themes
            parseTheme(ThemeManager.class.getResourceAsStream("theme-default.xml"));
            String[] paths = configPath.split(",");
            for (int i=0; i<paths.length; i++) {
                if (paths[i]!=null && !"".equals(paths[i])) {
                    try {
                        parseTheme(context.getResourceAsStream(paths[i]));
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }//for i
        } else {
            System.out.println("PGIST: failed to get FacesContext, the ThemeManager of pgtaglib failed to initialize!");
        }
    }//static

    
    public static Theme getTheme(String name) {
        return (Theme) themes.get(name);
    }//getTheme()


    private static void parseTheme(InputStream stream) {
        Digester digester = new Digester();
        digester.setValidating(false);
        
        digester.addObjectCreate("theme", Theme.class);
        digester.addSetProperties("theme", "name", "name");
        
        digester.addObjectCreate("theme/listTable", ListTableTag.class);
        digester.addSetNext("theme/listTable", "addTag");
        
        PropertyInsertRule rule = new PropertyInsertRule();
        digester.addRule("theme/listTable/table", rule);
        digester.addCallMethod("theme/listTable/table/property", "addProperty", 2);
        digester.addCallParam("theme/listTable/table/property", 0, "name");
        digester.addCallParam("theme/listTable/table/property", 1, "value");
        
        digester.addRule("theme/listTable/caption", rule);
        digester.addCallMethod("theme/listTable/caption/property", "addProperty", 2);
        digester.addCallParam("theme/listTable/caption/property", 0, "name");
        digester.addCallParam("theme/listTable/caption/property", 1, "value");

        digester.addRule("theme/listTable/header", rule);
        digester.addCallMethod("theme/listTable/header/property", "addProperty", 2);
        digester.addCallParam("theme/listTable/header/property", 0, "name");
        digester.addCallParam("theme/listTable/header/property", 1, "value");

        digester.addRule("theme/listTable/row", rule);
        digester.addCallMethod("theme/listTable/row/alter-color", "addColor", 1);
        digester.addCallParam("theme/listTable/row/alter-color", 0, "value");
        digester.addCallMethod("theme/listTable/row/highlight", "setHighlight", 1);
        digester.addCallParam("theme/listTable/row/highlight", 0, "value");
        digester.addCallMethod("theme/listTable/row/property", "addProperty", 2);
        digester.addCallParam("theme/listTable/row/property", 0, "name");
        digester.addCallParam("theme/listTable/row/property", 1, "value");

        digester.addRule("theme/listTable/footer", rule);
        digester.addCallMethod("theme/listTable/footer/property", "addProperty", 2);
        digester.addCallParam("theme/listTable/footer/property", 0, "name");
        digester.addCallParam("theme/listTable/footer/property", 1, "value");

        digester.addRule("theme/listTable/scroller", rule);
        digester.addCallMethod("theme/listTable/scroller/property", "addProperty", 2);
        digester.addCallParam("theme/listTable/scroller/property", 0, "name");
        digester.addCallParam("theme/listTable/scroller/property", 1, "value");

        digester.addObjectCreate("theme/scroller", ScrollerTag.class);
        digester.addSetNext("theme/scroller", "addTag");
        
        digester.addRule("theme/scroller/image", rule);
        digester.addCallMethod("theme/scroller/image/property", "addProperty", 2);
        digester.addCallParam("theme/scroller/image/property", 0, "name");
        digester.addCallParam("theme/scroller/image/property", 1, "value");

        try {
            Theme theme = (Theme) digester.parse(stream);
            System.out.println("    import theme "+theme.getName());
            themes.put(theme.getName(), theme);
            for (Iterator iter=theme.getTags().entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iter.next();
                Tag tag = (Tag) entry.getValue();
                if (tag instanceof ListTableTag) {
                    ListTableTag listTable = (ListTableTag) tag;
                    tag = listTable.getTable();
                } else if (tag instanceof ScrollerTag) {
                    ScrollerTag scroller = (ScrollerTag) tag;
                    tag = scroller.getImage();
                }
            }
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//parseTheme()
    
    
}
