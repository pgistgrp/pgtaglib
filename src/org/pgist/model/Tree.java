package org.pgist.model;


/**
 * interface Tree
 * @author kenny
 *
 */
public interface Tree {

    
    //getters and setters
    public Long getId();
    public void setId(Long id);
    public Node getRoot();
    public void setRoot(Node root);
    public String getTitle();
    public void setTitle(String title);
    
    
    //utility methods
    public int getNodesCount();
    public Node findNode(Long nodeId);
    
}//class Tree
