package org.pgist.model;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * @author kenny
 *
 */
public interface IFile {

    
    public Long getId();
    public InputStream getInputStream() throws Exception;
    public OutputStream getOutputStream() throws Exception;
    
    
}//interface IFile
