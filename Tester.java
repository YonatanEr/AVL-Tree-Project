import java.util.Arrays;

public class Tester {
	public static void main(String [] args) {
		AVLTree t = new AVLTree();
		for (int i=0; i<4; i++) {
			t.insert(i, "n" + Integer.toString(i));
		}
		
		int [] keys = t.keysToArray();
		System.out.println(Arrays.toString(keys));
		System.out.println();

		AVLTree.PrintAVLTree(t);
		for (int i=0; i<4; i++) {
			t.delete(i);
			AVLTree.PrintAVLTree(t);
			System.out.println();
		}
		
	}
}
