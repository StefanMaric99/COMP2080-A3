package ca.gbc.comp2080.assign3;

import ca.gbc.comp2080.datastructures.BTNode;
import ca.gbc.comp2080.datastructures.BTPosition;
import ca.gbc.comp2080.datastructures.BinarySearchTree;
import ca.gbc.comp2080.datastructures.Comparator;
import ca.gbc.comp2080.datastructures.Dictionary;
import ca.gbc.comp2080.datastructures.Entry;
import ca.gbc.comp2080.datastructures.InvalidEntryException;
import ca.gbc.comp2080.datastructures.InvalidKeyException;
import ca.gbc.comp2080.datastructures.Position;

/** Implementation of an AVL tree. */
public class AVLTree extends BinarySearchTree implements Dictionary {

	public AVLTree(Comparator c) {
		super(c);
	}

	public AVLTree() {
		super();
	}

	/** Nested class for the nodes of an AVL tree. */
	protected static class AVLNode extends BTNode {

		protected int height; // we add a height field to a BTNode

		/** Preferred constructor */
		AVLNode(Object element, BTPosition parent, BTPosition left, BTPosition right) {
			super(element, parent, left, right);
			height = 0;
			if (left != null)
				height = Math.max(height, 1 + ((AVLNode) left).getHeight());
			if (right != null)
				height = Math.max(height, 1 + ((AVLNode) right).getHeight());
		} // we assume that the parent will revise its height if needed

		public void setHeight(int h) {
			height = h;
		}

		public int getHeight() {
			return height;
		}
	}

	/**
	 * Print the content of the tree root.
	 */
	public void printRoot() {
		System.out.println("printRoot: " + value(left(root())).toString() + " " + value(root()).toString() + " "
				+ value(right(root())).toString());
	}

	/**
	 * Print the the tree using preorder traversal.
	 */
	public void preorderPrint(Position v) {
		if (isInternal(v))
			System.out.print(value(v).toString() + " ");
		if (hasLeft(v))
			preorderPrint(left(v));
		if (hasRight(v))
			preorderPrint(right(v));
	}

	/** Creates a new binary search tree node (overrides super's version). */
	protected BTPosition createNode(Object element, BTPosition parent, BTPosition left, BTPosition right) {
		return new AVLNode(element, parent, left, right); // now use AVL nodes
	}

	/** Returns the height of a node (call back to an AVLNode). */
	protected int height(Position p) {
		return ((AVLNode) p).getHeight();
	}

	/** Sets the height of an internal node (call back to an AVLNode). */
	protected void setHeight(Position p) { // called only if p is internal
		((AVLNode) p).setHeight(1 + Math.max(height(left(p)), height(right(p))));
	}

	/** Returns whether a node has balance factor between -1 and 1. */
	protected boolean isBalanced(Position p) {
		int bf = height(left(p)) - height(right(p));
		return ((-1 <= bf) && (bf <= 1));
	}

	/**
	 * Returns a child of p with height no smaller than that of the other child
	 */
	protected Position tallerChild(Position p) {
		if (height(left(p)) > height(right(p)))
			return left(p);
		else if (height(left(p)) < height(right(p)))
			return right(p);
		// equal height children - break tie using parent's type
		if (isRoot(p))
			return left(p);
		if (p == left(parent(p)))
			return left(p);
		else
			return right(p);
	}

	/**
	 * Insert a new element into the AVL tree.
	 */
	public Entry insert(Object k, Object v) throws InvalidKeyException {
		Entry toReturn = super.insert(k, v); // calls our new createNode method
		rebalance(actionPos); // rebalance up from the insertion position
		return toReturn;
	}

	/**
	 * Remove an element from the AVL tree.
	 */
	public Entry remove(Entry ent) throws InvalidEntryException {
		Entry toReturn = super.remove(ent);
		if (toReturn != null) // we actually removed something
			rebalance(actionPos); // rebalance up the tree
		return toReturn;
	}

	/**
	 * Rebalance method called by insert and remove. Traverses the path from zPos to
	 * the root. For each node encountered, we recompute its height and perform a
	 * trinode restructuring if it's unbalanced.
	 */
	protected void rebalance(Position zPos) {
		if (isInternal(zPos))
			setHeight(zPos);
		while (!isRoot(zPos)) { // traverse up the tree towards the root
			zPos = parent(zPos);
			setHeight(zPos);
			if (!isBalanced(zPos)) {
				// Perform a trinode restructuring starting from zPos's tallest grandchild
				Position xPos = tallerChild(tallerChild(zPos));
				zPos = restructure(xPos); // tri-node restructure
				setHeight(left(zPos)); // recompute heights
				setHeight(right(zPos));
				setHeight(zPos);
			}
		}
	}

	// ***************************************
	// DO NOT MODIFY THE CODE ABOVE THIS LINE.
	// ADD YOUR CODE BELOW THIS LINE.
	//
	// ***************************************

	/**
	 * Perform a trinode restructuring starting from x, z's tallest grandchild.
	 * Input: xPos, position of (pointer to) x Output: position of (pointer to) the
	 * new root of the subtree that was restructured.
	 */
	protected Position restructure(Position xPos) {

		// COMPLETE THIS METHOD

		// You may add your own method(s) to this file.

		BTPosition test1, test2, test3, test4, test5, test6;
		BTPosition testA, testB, testC, testD;

		Position yPos = parent(xPos);
		Position zPos = parent(yPos);

		boolean xLeftChild = (xPos ==left(yPos));
		boolean yLeftChild = (yPos ==left(zPos));

		BTPosition BTPX = (BTPosition)xPos;
		BTPosition BTPY = (BTPosition)yPos;
		BTPosition BTPZ = (BTPosition)zPos;

		if(xLeftChild && yLeftChild){
			test1 = BTPX;
			test2 = BTPY;
			test3 = BTPZ;
			testA = test1.getLeft();
			testB = test1.getRight();
			testC = test2.getRight();
			testD = test3.getRight();

		} else if(!xLeftChild && yLeftChild) {
			test1 = BTPY;
			test2 = BTPX;
			test3 = BTPZ;
			testA = test1.getLeft();
			testB = test2.getLeft();
			testC = test2.getRight();
			testD = test3.getRight();
		} else if(xLeftChild && !yLeftChild){
			test1 = BTPZ;
			test2 = BTPX;
			test3 = BTPY;
			testA = test1.getLeft();
			testB = test2.getLeft();
			testC = test2.getRight();
			testD = test3.getRight();
		} else {
			test1 = BTPZ;
			test2 = BTPY;
			test3 = BTPX;
			testA = test1.getLeft();
			testB = test2.getLeft();
			testC = test3.getLeft();
			testD = test3.getRight();
		}

		if(isRoot(zPos)) {
			root = test2;
			test2.setParent(null);
		} else {
			BTPosition zParent = (BTPosition)parent(zPos);
			if(zPos == left(zParent)){
				test2.setParent((zParent));
				zParent.setLeft(test2);
			} else {
				test2.setParent(zParent);
				zParent.setRight(test2);
			}
		}

		test2.setLeft((test1));
		test1.setParent((test2));
		test2.setRight((test3));

		test3.setParent((test2));
		test1.setLeft((testA));
		testA.setParent((test1));

		test1.setRight((testB));
		testB.setParent((test1));
		test3.setLeft((testC));

		testC.setParent(test3);
		test3.setRight(testD);
		testD.setParent(test3);


		return (test2); // replace this line with your code

	} // restructure

} // end AVLTree class
