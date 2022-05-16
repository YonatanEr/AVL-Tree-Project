

public class AVLNode implements NodeInterface{
		private int key;
		private String info;
		private NodeInterface left;
		private NodeInterface right;
		private int height;
		private NodeInterface parent;
		
		public AVLNode (int key, String info){
			this.key = key;
			this.info = info;
			this.left = null;
			this.right = null;
			this.height = 0;
			this.parent = null;
		}
		
		public int getKey()
		{	//returns node's key
			//O(1)
			return this.key; 

		}
		public String getValue()
		{	//returns node's value
			//O(1)
			return this.info;

		}
		public void setLeft(NodeInterface node)
		{	//sets node's left son 
			//O(1)
			this.left=node;

		}
		public NodeInterface getLeft()
		{	//return node's left son 
			//O(1)
			
			return this.left; 
		}
		public void setRight(NodeInterface node)
		{
			//sets node's right son 
			//O(1)
			this.right=node;

		}
		public NodeInterface getRight()
		{	//return node's right son 
			//O(1)
			
			return this.right; 

		}
		public void setParent(NodeInterface node)
		{	
			//sets node's parent
			//O(1)
			
			this.parent=node;

		}
		public NodeInterface getParent()
		{	//returns node's parent
			//O(1)
			return this.parent;

		}

		public void setHeight(int height)
		{	//sets node's height
			//O(1)
			this.height=height; 
		}
		
		public int getHeight()
		{	//returns node's height
			//O(1)
			return this.height;
		}
	
  }