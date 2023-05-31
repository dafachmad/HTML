import java.io.*;
import java.util.*;
////////////////////////////////////////////////////////////////
class Node {
	public int Data1;
	public double Data2;
	public Node leftChild; // menunjuk Node kiri (child kiri)
	public Node rightChild; // menunjuk Node kanan (child kanan)
	public void displayNode() { // menampilkan data pada Node
		System.out.print("{" + Data1 + ", " + Data2 + "} ");
	}
} // end class Node
////////////////////////////////////////////////////////////////
class Tree {
	private Node root;
// -------------------------------------------------------------
	public Tree()
		{ root = null; } // belum ada Node pada Tree
// -------------------------------------------------------------
	public Node cariData(int dat) { // mencari data (Node) yang diinginkan )
		Node current = root; // pointer current menunjuk ke Root
		while(current.Data1 != dat) { /* selama data pada Node yang ditunjuk oleh pointer current
										tidak sama dengan data yang dicari (belum ditemukan), maka */
			if(dat < current.Data1) // jika data yang dicari lebih kecil dari data pointer current, maka
				current = current.leftChild; // pointer current di-set untuk menunjuk Node sebelah kiri (child kiri)
			else // jika data yang dicari lebih besar dari data pointer current, maka
				current = current.rightChild; // pointer current di-set untuk me5nunjuk Node sebelah kanan (child kanan)
			if(current == null) // jika pointer current sama dgn NULL (artinya tidak ada Child), maka
				return null; // data (Node) yang dicari tidak ditemukan
		}
		return current; // data (Node) ditemukan
	} // end cariData()
// -------------------------------------------------------------
	public void insert(int dat1, double dat2) {
		Node newNode = new Node(); // membuat Node baru
		newNode.Data1 = dat1; // data pertama disimpan dalam Node baru
		newNode.Data2 = dat2; // data kedua disimpan dalam Node baru
		if(root==null) // jika belum ada data (Node), artinya tree masih kosong
			root = newNode; // Node baru dijadikan root
		else { // jika sudah ada Root
			Node current = root; // membuat pointer current yang diarahkan ke Node yang ditunjuk oleh pointer Root
			Node parent; // pointer parent
			while(true) {
				parent = current; // pointer parent diarahkan ke Node yang ditunjuk oleh pointer current
				if(dat1 < current.Data1) { // jika data yang akan dimasukkan lebih kecil dari data pointer current, maka
					current = current.leftChild; // pointer current di-set untuk menunjuk Node sebelah kiri (child kiri)
					if(current == null) { // jika pointer current tidak menunjuk kemana2, maka
						parent.leftChild = newNode; // masukkan data (Node baru) di kiri pointer parent
						return;
					}
				}
				else { // jika data yang akan dimasukkan lebih besar dari data pointer current, maka
					current = current.rightChild; // pointer current di-set untuk menunjuk Node sebelah kanan (child kanan)
					if(current == null) { // jika pointer current tidak menunjuk kemana2, maka
						parent.rightChild = newNode; // masukkan data (Node baru) di kanan pointer parent
						return;
					}
				}
			} // end while
		}
	} // end insert()
// -------------------------------------------------------------
	public boolean delete(int key) { // menghapus suatu data (node)
		Node current = root; // pointer current diarahkan ke Node yang ditunjuk oleh pointer root
		Node parent = root; // pointer parent diarahkan ke Node yang ditunjuk oleh pointer root
		boolean isLeftChild = true; // variabel isLeftChild = true
		while(current.Data1 != key) { // selama data pointer current tidak sama dengan data yang akan dihapus
			parent = current; // pointer parent diarahkan ke Node yang ditunjuk oleh pointer current
			if(key < current.Data1) { // jika data yang akan dihapus lebih kecil dari data pointer current, maka
				isLeftChild = true; // variabel isLeftChild tetap bernilai true
				current = current.leftChild; // pointer current diarahkan ke Node sebelah kiri (child kiri)
			}
			else { // jika data yang akan dihapus lebih besar dari data pointer current, maka
				isLeftChild = false; // variabel isLeftChild diubah bernilai false
				current = current.rightChild; // pointer current diarahkan ke Node sebelah kanan (child kanan)
			}
			if(current == null) // jika pointer current = NULL, maka
				return false; // data (Node) yang akan dihapus tidak ditemukan
		} // end while
		
		// jika data (Node) yang akan dihapus ditemukan
		// jika data (Node) yang akan dihapus ditemukan dan terletak di Leaf (Node yang tidak punya child)
		if(current.leftChild==null && current.rightChild==null) {
			if(current == root) // jika data (Node) yang akan dihapus terletak di root (artinya hanya ada satu data dalam tree)
				root = null;
			else 
				if(isLeftChild) // jika data (Node) yang akan dihapus terletak disebelah kiri pointer parent, maka
					parent.leftChild = null; // pointer leftChild dari Node yang ditunjuk oleh pointer parent di-set NULL
				else // jika data (Node) yang akan dihapus terletak disebelah kanan pointer parent, maka
					parent.rightChild = null; // pointer rightChild dari Node yang ditunjuk oleh pointer parent di-set NULL
		}
		
		// jika data (Node) yang akan dihapus ditemukan HANYA mempunyai Node disebelah kiri (child kiri)
		else
			if(current.rightChild==null)
				if(current == root) // jika data (Node) yang akan dihapus terletak di root, maka
					root = current.leftChild; /* pointer root di-set menunjuk ke Node yang ditunjuk oleh 
												pointer leftChild dari Node yang ditunjuk oleh pointer current */
				else // jika data (Node) yang akan dihapus tidak terletak di root, maka
					if(isLeftChild) // jika terletak di child sebelah kiri, maka
						parent.leftChild = current.leftChild; // child kiri dari Node yang dihapus menjadi child kiri dari pointer parent
					else // jika terletak di child sebelah kanan, maka
						parent.rightChild = current.leftChild; // child kiri dari Node yang dihapus menjadi child kanan dari pointer parent
						
		else
			// jika data (Node) yang akan dihapus ditemukan HANYA mempunyai Node disebelah kanan (child kanan) 
			if(current.leftChild==null)
				if(current == root)
					root = current.rightChild;
				else 
					if(isLeftChild)
						parent.leftChild = current.rightChild;
					else
						parent.rightChild = current.rightChild;
						
			// jika data (Node) yang akan dihapus ditemukan mempunyai Node disebelah kanan dan kiri (child kanan dan child kiri) 
			else  { // maka harus dicari successor-nya
				Node successor = getSuccessor(current); // cari successor dari data (node) yang akan dihapus (memanggil method getSuccessor()
				if(current == root) // jika data (Node) yang akan dihapus terletak di root, maka
					root = successor;
				else // jika data (Node) yang akan dihapus tidak terletak di root, maka
					if(isLeftChild) // jika disebelah kiri pointer parent
						parent.leftChild = successor;
					else // jika disebelah kanan pointer parent
						parent.rightChild = successor;

				successor.leftChild = current.leftChild;
			} // end else dua child
			return true;
	} // end delete()
// -------------------------------------------------------------
	private Node getSuccessor(Node delNode) {
		Node successorParent = delNode;
		Node successor = delNode;
		Node current = delNode.rightChild;
		while(current != null) {
			successorParent = successor;
			successor = current;
			current = current.leftChild;
		}
		if(successor != delNode.rightChild) {
			//successorParent.leftChild = null; // bisa??? tidak!!!
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = delNode.rightChild;
		}
		return successor;
	}
// -------------------------------------------------------------
	public void traverse(int traverseType) {
		switch(traverseType) {
			case 1: System.out.print("\nPreorder traversal: ");
					preOrder(root);
					break;
			case 2: System.out.print("\nInorder traversal: ");
					inOrder(root);
					break;
			case 3: System.out.print("\nPostorder traversal: ");
					postOrder(root);
					break;
		}
		System.out.println();
	}
// -------------------------------------------------------------
	private void preOrder(Node localRoot) {
		if(localRoot != null) {
			System.out.print(localRoot.Data1 + " ");
			preOrder(localRoot.leftChild);
			preOrder(localRoot.rightChild);
		}
	}
// -------------------------------------------------------------
	private void inOrder(Node localRoot) {
		if(localRoot != null) {
			inOrder(localRoot.leftChild);
			System.out.print(localRoot.Data1 + " ");
			inOrder(localRoot.rightChild);
		}
	}
// -------------------------------------------------------------
	private void postOrder(Node localRoot) {
		if(localRoot != null) {
			postOrder(localRoot.leftChild);
			postOrder(localRoot.rightChild);
			System.out.print(localRoot.Data1 + " ");
		}
	}
// -------------------------------------------------------------
	public void displayTree() {
		Stack globalStack = new Stack();
		globalStack.push(root);
		int nBlanks = 32;
		boolean isRowEmpty = false;
		System.out.println("......................................................");
		while(isRowEmpty==false) {
			Stack localStack = new Stack();
			isRowEmpty = true;
			for(int j=0; j<nBlanks; j++)
				System.out.print(' ');
			while(globalStack.isEmpty()==false) {
				Node temp = (Node)globalStack.pop();
				if(temp != null) {
					System.out.print(temp.Data1);
					localStack.push(temp.leftChild);
					localStack.push(temp.rightChild);
					if(temp.leftChild != null || temp.rightChild != null)
						isRowEmpty = false;
				}
				else {
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				for(int j=0; j<nBlanks*2-2; j++)
					System.out.print(' ');
			}
			System.out.println();
			nBlanks /= 2;
			while(localStack.isEmpty()==false)
				globalStack.push( localStack.pop() );
		}
		System.out.println("......................................................");
	} // end displayTree()
// -------------------------------------------------------------
} // end class Tree
////////////////////////////////////////////////////////////////
class binaryTree {
	public static void main(String[] args) throws IOException {
		int value;
		Tree theTree = new Tree();
		theTree.insert(50, 1.5);
		theTree.insert(25, 1.2);
		theTree.insert(75, 1.7);
		theTree.insert(12, 1.5);
		theTree.insert(37, 1.2);
		theTree.insert(43, 1.7);
		theTree.insert(30, 1.5);
		theTree.insert(33, 1.2);
		theTree.insert(87, 1.7);
		theTree.insert(93, 1.5);
		theTree.insert(97, 1.5);
		while(true) {
			System.out.println("Ketik huruf pertama dari menu pilihan");
			System.out.print("show, insert, find, delete, traverse: ");
			int choice = getChar();
			switch(choice) {
				case 's':
					theTree.displayTree();
					break;
				case 'i':
					System.out.print("Masukan datanya: ");
					value = getInt();
					theTree.insert(value, value + 0.9);
					break;
				case 'f':
					System.out.print("Masukan data yang dicari: ");
					value = getInt();
					Node found = theTree.cariData(value);
					if(found != null) {
						System.out.print("Data ditemukan: ");
						found.displayNode();
						System.out.print("\n");
					}
					else
						System.out.print("Data tidak ditemukan");
						System.out.print(value + '\n');
						break;
				case 'd':
					System.out.print("Masukan data yang akan dihapus: ");
					value = getInt();
					boolean didDelete = theTree.delete(value);
					if(didDelete)
						System.out.print("Data " + value + " terhapus!" + '\n');
					else
						System.out.print("Data tidak dapat dihapus");
					System.out.print(value + '\n');
					break;
				case 't':
					System.out.print("Pilih 1, 2 atau 3: ");
					value = getInt();
					theTree.traverse(value);
					break;
				default:
					System.out.print("Masukan salah\n");
			} // end switch
		} // end while
	} // end main()
// -------------------------------------------------------------
	public static String getString() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
// -------------------------------------------------------------
	public static char getChar() throws IOException {
		String s = getString();
		return s.charAt(0);
	}
//-------------------------------------------------------------
	public static int getInt() throws IOException {
		String s = getString();
		return Integer.parseInt(s);
	}
// -------------------------------------------------------------
} // end class TreeApp
////////////////////////////////////////////////////////////////