package org.pgist.renderkit;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import java.io.IOException;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <p>Convenient base class for <code>Renderer</code> implementations.</p>
 * @author Kenny
 */
public abstract class BaseRenderer extends Renderer {

    
    public static final String BUNDLE_ATTR = "org.pgist.faces.bundle";


    public String convertClientId(FacesContext context, String clientId) {
        return clientId;
    }


    protected String getKeyAndLookupInBundle(FacesContext context,
                                             UIComponent component,
                                             String keyAttr)
        throws MissingResourceException {
        String key = null, bundleName = null;
        ResourceBundle bundle = null;

        key = (String) component.getAttributes().get(keyAttr);
        bundleName = (String) component.getAttributes().get(BUNDLE_ATTR);

        // if the bundleName is null for this component, it might have
        // been set on the root component.
        if (bundleName == null) {
            UIComponent root = context.getViewRoot();

            bundleName = (String) root.getAttributes().get(BUNDLE_ATTR);
        }
        // verify our component has the proper attributes for key and bundle.
        if (null == key || null == bundleName) {
            throw new MissingResourceException("Can't load JSTL classes",
                                               bundleName, key);
        }

        // verify the required Class is loadable
        // PENDING(edburns): Find a way to do this once per ServletContext.
        if (null == Thread.currentThread().getContextClassLoader().
            getResource("javax.servlet.jsp.jstl.fmt.LocalizationContext")) {
            throw new MissingResourceException("Can't load JSTL classes", bundleName, key);
        }

        // verify there is a ResourceBundle in scoped namescape.
        javax.servlet.jsp.jstl.fmt.LocalizationContext locCtx = null;
        if (null == (locCtx = (javax.servlet.jsp.jstl.fmt.LocalizationContext)
            (Util.getValueBinding(bundleName)).getValue(context)) ||
            null == (bundle = locCtx.getResourceBundle())) {
            throw new MissingResourceException("Can't load ResourceBundle ", bundleName, key);
        }

        return bundle.getString(key);
    }


    protected void encodeRecursive(FacesContext context, UIComponent component)
        throws IOException {

        component.encodeBegin(context);
        if (component.getRendersChildren()) {
            component.encodeChildren(context);
        } else {
            Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                encodeRecursive(context, kid);
            }
        }
        component.encodeEnd(context);

    }


}
