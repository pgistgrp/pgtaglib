package org.pgist.component;

import javax.faces.component.html.HtmlInputSecret;

public class OptionalInputComponent extends HtmlInputSecret {


    public OptionalInputComponent() {
        super();
        setRendererType("org.pgist.faces.OptionalInput");
    }

    
}//class OptionalInputComponent
