
public interface NodeInterface {
	public int getKey(); //returns node's key 
	public String getValue(); //returns node's value [info]
	public void setLeft(NodeInterface node); //sets left child
	public NodeInterface getLeft(); //returns left child (if there is no left child return null)
	public void setRight(NodeInterface node); //sets right child
	public NodeInterface getRight(); //returns right child (if there is no right child return null)
	public void setParent(NodeInterface node); //sets parent
	public NodeInterface getParent(); //returns the parent (if there is no parent return null)
	public void setHeight(int height); // sets the height of the node
	public int getHeight(); // Returns the height of the node 
}
