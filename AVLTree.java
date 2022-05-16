

public class AVLTree {
	private NodeInterface root;
	private NodeInterface min_node;
	private NodeInterface max_node;
	private int size;
	
	public AVLTree(){
		this.root = null;
		this.min_node = null;
		this.max_node = null;
		this.size=0;
	}
	
	 public static void PrintAVLTree(AVLTree t ){
	        System.out.println();
		 	printBinaryTree(t.getRoot(), 0);
		 	System.out.println();
	}    
  
	
	  public static void printBinaryTree(NodeInterface root, int level){
		  //Stack Overflow
		  	if(root==null)
		         return;
		    printBinaryTree(root.getRight(), level+1);
	        String root_print = "<" + Integer.toString(root.getKey()) + ", " + root.getValue() + ">";
		    if(level!=0){
		        for(int i=0;i<level-1;i++)
		            System.out.print("|\t");
		        System.out.println("|------------" + root_print);
		    }
		    else
		        System.out.println(root_print);
		    printBinaryTree(root.getLeft(), level+1);
		}    
		
		
	
	  public boolean empty() {	//O(1) 
		  //return True if the tree is empty, otherwise it returns false
	    return (this.root==null);
	    
	  }
	  
	
	
	  public String search(int k){	//O(log(n))
		  //return the info of the node with key = k, if there is no node with key = k returns false
		  NodeInterface node = this.BST_search(k);
		  if (node!=null) {
			  return node.getValue();
		  }
		  return null;
		  
	  }
	  
	  public NodeInterface BST_search(int k) {	//O(log(n)
		  // returns node with key = k, if there is no node with key = k returns false
		  NodeInterface node=this.root;
		  while (node!=null) {
			  if (k==node.getKey()) {	 	//found it
				  return node;}
			  if (k<node.getKey()) {		//go left
				  node=node.getLeft(); } 
			  else {						//go right
				  node=node.getRight(); }
		  }
		  return null; 						//did not find k
	  }
	  
	
	
	  
	  
	   public int insert(int k, String i) {		//O(log(n)) 
		// inserts new node with key=k and info=i to AVL
		// fixes the tree if there are AVL violations
		// returns amount of rotations needed or -1 if k is in the tree
		  if (this.search(k)!=null) {			// O(log(n))
			  return -1;}
		  this.size++;
		  NodeInterface node=new AVLNode(k,i);	// new node to insert
		  this.update_max_min_insertion(node);	
		  this.BST_insert(node);
		  return ( post_insert_rotations(node.getParent()) );
	   }
	      
	   private void BST_insert(NodeInterface node) {	//O(log((n))
		   //insert to regular BST
		   NodeInterface parent = this.root;
		   NodeInterface last_parent=null;
		   while (parent!=null) {
			   last_parent=parent;
			   if (node.getKey()<parent.getKey()) {
				   parent=parent.getLeft();
				   }
			   else {
				   parent=parent.getRight();
				   }
		   }
		   node.setParent(last_parent);
		   if (last_parent==null) {
			   this.root=node;}
		   else {
			   if (node.getKey()<last_parent.getKey()) {
				   last_parent.setLeft(node);
			   }
			   else {
				   last_parent.setRight(node);
			   }
		   }
	   }
	   
	   private int post_insert_rotations(NodeInterface node) {				//O(log(n))
		   // checks if there are AVL criminals 
		   // send calls rotation functions if needed
		   // returns amount of rotations needed to fix the AVL tree
		   int last_height;
		   while (node!=null) {
			   last_height=node.getHeight();
			   this.update_height(node);	
			   if (Math.abs( this.BF(node) )<2) {
				   if (node.getHeight()==last_height) {	
					   return 0;	
					   }
				   node=node.getParent(); }		//keep looking up	
			   else {	//rotation										
				   return ( post_insertion_rotation_type(node) );
					   }
				   }
		   return 0;
		   }
	   
	   private int post_insertion_rotation_type(NodeInterface node){	//O(1)
		   // checks type of rotation needed and send to rotation function
		   // returns amount of rotations needed
		   if (this.BF(node)==-2) {
			   NodeInterface right=node.getRight();
			   if (this.BF(right)==-1) {		
				   this.left_rotation(node);			return 1;	}		//RR	
			   else {// this.BF(right)=1
				   this.right_left_rotation(node);		return 2;	}		//RL	
			   }
		   else {
			   if (this.BF(node)==2) {
				   NodeInterface left=node.getLeft();	
				   if (this.BF(left)==-1) {	
					   this.left_right_rotation(node);	return 2;	}		//LR	
				   else {	// this.BF(right)=1
					   this.right_rotation(node);		return 1;	}		//LL 	
				   }
			   }	
		   return 0;
		   }
	   
	   private void left_rotation(NodeInterface node) {	//O(1)
		   // performs left rotation
		   NodeInterface R = node.getRight();
		   NodeInterface RL = R.getLeft();	//could be null
		   R.setLeft(node);
		   replace_top(R,node);
		   if (RL!=null) {
			   RL.setParent(node);}
		   node.setRight(RL);
		   this.update_height(node);		//O(1)
		   this.update_height(R);			//O(1)   
	   }
	   	 
	   private void right_left_rotation(NodeInterface node) {	//O(1)
		// performs right_left rotation
		   this.right_rotation(node.getRight());
		   this.left_rotation(node);
	   }
	   
	   private void left_right_rotation(NodeInterface node) {	//O(1)
		// performs left_right rotation
		   this.left_rotation(node.getLeft());	
		   this.right_rotation(node);				
	   }
	
	   private void right_rotation(NodeInterface node) {	//O(1)
		// performs right rotation
		   NodeInterface L = node.getLeft();
		   NodeInterface LR = L.getRight();	//could be null
		   L.setRight(node);
		   replace_top(L,node);
		   if (LR!=null) {
			   LR.setParent(node);}
		   node.setLeft(LR);
		   this.update_height(node);		//O(1)
		   this.update_height(L);			//O(1)   
	   }
	   
	   private void replace_top(NodeInterface son, NodeInterface node) {	//O(1)
		   //switches relations between son and node
		   //if needed updates root 
		   if (this.root==node) {
			   this.root=son;			
			   son.setParent(null);}
		   else {
			   NodeInterface parent=node.getParent();
			   son.setParent(parent);
			   if (parent.getLeft()==node) {
				   parent.setLeft(son);
			   }
			   else {
				   parent.setRight(son);}
			   }
		   node.setParent(son);
	   }
	    
	   private int calc_node_height(NodeInterface node) {	//O(1)
		   //returns node's height
		   //-1 for null
		   if (node==null) {
			   return -1;}
		   NodeInterface 	Left=node.getLeft(),	Right=node.getRight();
		   int 			LH=-1,					RH=-1;
		   if (Left!=null) {
			   LH=Left.getHeight();
		   }
		   if (Right!=null) {
			   RH=Right.getHeight();
		   }
		   return (1 + Math.max(LH ,RH) );
	   }
	   
	   private void update_height(NodeInterface node) {	//O(1)
		   //updates node's height
		   if (node!=null) {
			   node.setHeight( this.calc_node_height(node) );
			   }
	   }
	   
	   public int BF(NodeInterface node) {	//O(1)
		   //returns node's BF 
		   //0 for null
		   if (node==null) {
			   return 0;
		   }
		   int LH = this.calc_node_height(node.getLeft());
		   int RH = this.calc_node_height(node.getRight());
		   return (LH - RH);
	   }
	   
	   private void update_max_min_insertion(NodeInterface node) {	//O(1)
		   //updates the max and min of the tree given new node
		   if (this.empty()) {
			   this.max_node=node;
			   this.min_node=node;}
		   else {
			   if (node.getKey()>this.max_node.getKey()) {
				   this.max_node=node;}
			   if (node.getKey()<this.min_node.getKey()) {
				   this.min_node=node;}
			   }
	   }
	   
	   
	
	
	   public int delete(int k)		//O(log(n))
	   //deletes node with key = k from the tree
	   //returns amount of rotations needed to fix the tree
	   //if key = k is not in the tree returns -1
	   {
		   NodeInterface node=this.BST_search(k);				//O(log(n))
		   if (node==null) {
			   return -1;}	//k is not in the tree
		   this.size--;
		   NodeInterface baseline = this.BST_delete(node);			//O(log(n))				
		   this.update_max_min_deletion(k);						//O(log(n)
		   return post_deletion_rotations(baseline);			//O(log(n))
	   }
	   
	   
	   private NodeInterface BST_delete(NodeInterface node) {	//O(log(n)
		   //deletes node from BST 
		   //returns new baseline for fixing the tree
		   NodeInterface baseline=node.getParent();			//remember the ones we have
		   if (this.count_children(node)==0) {			//node is leaf
			   remove_leaf(node);}
		   else {//node is not leaf
			   if (this.count_children(node)==1) {		//node has one child
				   bypass_node(node);}
			   else {									//node has two children
				  NodeInterface successor=this.find_successor(node);
				  baseline=successor.getParent();
				  if (successor.getParent()==node) {
					  baseline=successor;
				  }
				  if (this.count_children(successor)==0) {
					  this.remove_leaf(successor);
				  }
				  else {
					  this.bypass_node(successor);;
				  }
				  switch_node_with_new_node(node, successor );
			   }
		   }
		   return baseline;	//base line for further rotations check
	   }
	   
	   private void switch_node_with_new_node(NodeInterface node, NodeInterface x) {
		   //O(1)
		   // replaces x with node
		   NodeInterface parent = node.getParent();
		   NodeInterface left = node.getLeft();
		   NodeInterface right = node.getRight();
		   x.setHeight(node.getHeight());
		   x.setParent(parent);
		   if (parent==null) {
			   this.root=x;}
		   else {
			   if (parent.getLeft()==node) {
				   parent.setLeft(x);}
			   else {
				   parent.setRight(x);}
		   		}
		   x.setLeft(left);
		   if (left!=null) {
			   left.setParent(x);
		   }
		   x.setRight(right);
		   if (right!=null) {
			   right.setParent(x);
		   }
	   }
	   
	   
	   private int post_deletion_rotations(NodeInterface baseline) {	//O(log(n)) 
		   // checks if rotations are needed and calls function to rotate if needed
		   //returns amount of rotations needed to fix the tree
		   int last_height, count_rotations=0;
		   NodeInterface last_parent;
		   while (baseline!=null) {
			   last_height=baseline.getHeight();
			   this.update_height(baseline);
			   if(Math.abs(this.BF(baseline))<2) {
				   		if (last_height==baseline.getHeight()) {
					   		return count_rotations;}
				   		else {
				   			baseline=baseline.getParent();}
			   }
			   else {	//bf is 2 
				   last_parent=baseline.getParent();
				   count_rotations = count_rotations + delete_rotation_type(baseline);
				   baseline=last_parent;
				   }
		   }
		   return count_rotations;
	   }
	   
	   
	   private int delete_rotation_type(NodeInterface baseline) {
		   // checks what kind of rotation is needed and calls functions to perform rotations
		   // returns amount of rotations performed
		   //O(1)
		   if (this.BF(baseline)==2) {
			   if( this.BF( baseline.getLeft() ) == -1 ) {
				   this.left_right_rotation(baseline);			
				   return 2;
			   }
			   else {
				   this.right_rotation(baseline);				
				   return 1;
			   }
		   }
		   else {
			   if( this.BF( baseline.getRight() ) == 1 ) {
				   this.right_left_rotation(baseline);
				   return 2;
			   }
			   else {
				   this.left_rotation(baseline);
				   return 1;
			   }
		   }
	   }
	   
	   private void remove_leaf(NodeInterface node) {	
		   //removes node which is leaf
		   //O(1)
		   if (this.root==node) {		//Tree is lonely leaf
			   this.empty_the_Tree();
		   }
		   else {						//node has a parent
			   NodeInterface parent=node.getParent();
			   if (parent.getLeft()==node) {
				   parent.setLeft(null);
				   }
			   else {
				   parent.setRight(null);
				   }
		   }	
	   }
	   
	   private void bypass_node(NodeInterface node) {
		   //bypasses node with single child
		   //O(1)
		   NodeInterface left=node.getLeft(), right=node.getRight();	
		   if (this.root==node) {	//null parent
			   if (left!=null) {
				   this.root=left;
				   left.setParent(null);}
			   else {
				   this.root=right;
				   right.setParent(null);}
		   }
		   else {					//parent is not null
			   NodeInterface parent=node.getParent();
			   if (parent.getLeft()==node) {	
				   if (left!=null) {				//LL
					   parent.setLeft(left);
					   left.setParent(parent);}
				   else {							//LR
					   parent.setLeft(right);
					   right.setParent(parent);}
				   }
			   else {//parent.Right is not null
				   if (left!=null) {				//RL
					   parent.setRight(left);
					   left.setParent(parent); }
				   else {							//RR
					   parent.setRight(right);
					   right.setParent(parent);}
				   }
			   }
		   }
	   
	   
	   private void empty_the_Tree() {	//O(1)	
		   // sets the Tree to default
		   this.root=null;
		   this.max_node=null;
		   this.min_node=null;
	   }
	   
	   private NodeInterface find_successor(NodeInterface node) {	//O(log(n))
		   // find successor of given node
		   // returns successor
		   if (node.getRight()!=null) {
			   node=node.getRight();
			   while (node.getLeft()!=null) {	//find min of the path
				   node=node.getLeft();
			   }
			   return node;
		   }
		   NodeInterface parent=node.getParent();
		   while (parent!=null && parent.getRight()==node) {
			   parent=parent.getParent();
			   node=node.getParent();
			   }
		   return parent;
	   }
	   
	   private int count_children(NodeInterface node) {	//O(1)
		   // returns amount of children of a given node 
		   if (node.getLeft()==null && node.getRight()==null) {
			   return 0;
		   }
		   if (node.getLeft()!=null && node.getRight()!=null) {
			   return 2;
		   }
		   return 1;
	   }
	   
	   private void update_max_min_deletion(int k) {	//O(log(n)) + O(log(n)) = O(log(n))
		   //updates max and min if needed after deletion
		   if (this.root==null) {this.max_node=null;this.max_node=null;}
		   else {
			   if (k==this.min_node.getKey()) {
				   NodeInterface min=this.root;
				   while (min.getLeft()!=null) {	//find new min
					   min=min.getLeft();
				   }
				   this.min_node=min;
			   }
			   if (k==this.max_node.getKey()) {		//find new max
				   NodeInterface max=this.root;
				   while (max.getRight()!=null) {
					   max=max.getRight();
				   }
				   this.max_node=max;
			   }
		   }
	   }
	
	
	   public String min()
	   {	//O(1) due to maintenance
		   if (this.empty()) {
			   return null;
		   }
		   return this.min_node.getValue();
		   
	   }
	
	
	
	   public String max()
	   {	//O(1) due to maintenance
		   if (this.empty()) {
			   return null;
		   }
		   return this.max_node.getValue();
		   
	   }
	 
	   public static int index;
	   public static NodeInterface [] nodes_array;
	   
	   private void in_order_nodes_rec(NodeInterface node) {
		   //O(n) time	
		   //O(log(n)) memory recursion stack
		   //returns sorted nodes by keys 
		   if (node!=null) {
			   in_order_nodes_rec(node.getLeft());
			   nodes_array[index]=node;
			   index++;
			   in_order_nodes_rec(node.getRight());
		   }
	   }	
	
	
	  public int[] keysToArray()
	  { 
		  //O(n)
		  //returns sorted (by key) array of keys inside the tree, if tree is empty returns null
		  nodes_array = new NodeInterface[this.size];
		  index=0;
		  in_order_nodes_rec(this.root);
		  int [] keys_array = new int [this.size];
		  for (int i=0;i<this.size;i++) {	
			  keys_array[i]=nodes_array[i].getKey();
		  }
		  return keys_array;
	  }
	
	 
	  
	  public String[] infoToArray()
	  {
		  //O(n)
		  //returns sorted (by key) array of values inside the tree, if tree is empty returns null
		  nodes_array = new NodeInterface[this.size];
		  index=0;
		  in_order_nodes_rec(this.root);
		  String [] vals_array = new String [this.size];
		  for (int i=0;i<this.size;i++) {	
			  vals_array[i]=nodes_array[i].getValue();
		  }
		  return vals_array;
	  }
	
	  
	 
	   public int size()
	   {	//O(1) due to maintenance
		   //returns size of the tree
		   return this.size;
		   
	   }
	   
	  
	   public NodeInterface getRoot()
	   {	//O(1) due to maintenance
		   //returns the node of the tree
		   return this.root;
	   }
	  
	  
}
	
	
	
	
