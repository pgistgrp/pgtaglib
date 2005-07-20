package org.pgist.conf;


/**
 * 
 * @author kenny
 *
 */
public class ScrollerTag extends Tag {

    
    private Image image;
    
    
    public ScrollerTag() {
        name = "scroller";
    }


    public Image getImage() {
        return image;
    }


    public void setImage(Image image) {
        this.image = image;
    }
    
    
}
