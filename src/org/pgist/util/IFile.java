package org.pgist.util;

import java.io.InputStream;


/**
 * 
 * @author kenny
 *
 */
public interface IFile {

    
    public Long getId();
    public InputStream getStream() throws Exception;
    
    
}//interface IFile
