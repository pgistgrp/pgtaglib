package org.pgist.model;

import java.util.Set;


/**
 * interface node
 * @author kenny
 *
 */
public interface Node {

    
    public Long getId();
    public void setId(Long id);
    public Node getParent();
    public void setParent(Node parent);
    public Set getChildren();
    public void setChildren(Set children);
    public int getDepth();
    
    
}//interface Node
