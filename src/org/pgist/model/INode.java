package org.pgist.model;

import java.util.Set;


/**
 * interface node
 * @author kenny
 *
 */
public interface INode {

    
    public Long getId();
    public void setId(Long id);
    public INode getParent();
    public void setParent(INode parent);
    public Set getChildren();
    public void setChildren(Set children);
    public int getDepth();
    public IContent getContent();
    public IUser getOwner();
    
    
}//interface INode
