package org.pgist.model;


/**
 * interface ITree
 * @author kenny
 *
 */
public interface ITree {

    
    //getters and setters
    public Long getId();
    public void setId(Long id);
    public INode getRoot();
    public void setRoot(INode root);
    public String getTitle();
    public void setTitle(String title);
    
    
    //utility methods
    public int getNodesCount();
    public INode findNode(Long nodeId);
    
}//class ITree
