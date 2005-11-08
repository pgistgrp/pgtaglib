package org.pgist.util;


/**
 * Utils is a static class to provide some common-use methods.
 * @author kenny
 *
 */
public class Utils {

    
    public static String getGrayColor(float f) {
        if (f<0.0F) {
            f = 0.0F;
        } else if (f>1.0F) {
            f = 1.0F;
        }
        String s = Integer.toHexString(Math.round(f*255));
        return s + s + s;
    }//getGrayColor()

    
    public static String getFontSize(float f) {
        if (f<0.0F) {
            f = 0.0F;
        } else if (f>1.0F) {
            f = 1.0F;
        }
        String s = ""+Math.round(f*150);
        return s + "%";
    }//getFontSize
    
    
}//class Utils
