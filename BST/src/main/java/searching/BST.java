package searching;
import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * With the given partial implementation of a Binary Search Tree, we ask you to implement
 * the following methods :
 * - minKey() which return the smallest key in the BST, or null if it is empty
 * - higherKey(Key key) which Returns the least key strictly greater than the given key, 
 *   or null if there is no such key
 * 
 * You also need to implement an iterator for the BST
 */
public class BST<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    private Node root;
    // root of BST

    private class Node {
        private final Key key;       // sorted by key
        private Value val;           // associated data
        private Node left, right;    // left and right subtrees
        private int size;            // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST() {}

    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /**
     * Search for key, update value if key is found. Grow table if key is new.
     *
     * @param  key the key
     * @param  val the value
     */
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


    /**
     * TODO 
     * The expected complexity is O(h) where h is the height of the BST
     * 
     * Returns the minimum key

     * @return the minimum key, null if the tree is empty
     */
    public Key minKey() {
        if (root==null) return null;
         Node current = root;
         while (current.left != null) {
             current = current.left;
         }
        return current.key;
    }



    /**
     * TODO
     * The expected complexity is O(h) where h is the height of the BST
     * 
     * Returns the least key strictly greater than the given key, or null if there is no such key.
     *
     * @param key - the key
     * @return the least key greater than key, or null if there is no such key
     */
    public Key higherKey(Key key) {
         return higherKey(root , key);
    }

    private Key higherKey(Node x, Key key) {

         if (x == null) return null;
         int res = key.compareTo(x.key);
         if (res >= 0) {
            return higherKey(x.right, key);
         }
         Key k_res = higherKey(x.left, key);
         if (k_res == null) return x.key;
         int c_2 = k_res.compareTo(x.key);
         if (c_2 < 0) return k_res;
         return x.key;

    }


    /**
     * TODO
     * 
     * Returns an iterator that iterates through the keys in Incresing order
     * (In-Order transversal).
     * @return an iterator that iterates through the items in FIFO order.
     */
    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator();
    }

    // the iterator doesn't implement remove() optional method

    private class BSTIterator implements Iterator<Key> {
        private List<Key> lst = new ArrayList<Key>();
        private int current = 0;
        BSTIterator () {
            build(root);
        }
        public void build (Node root) {
            if (root == null) return;
            build(root.left);
            lst.add(root.key);
            build(root.right);
        }
        @Override
        public boolean hasNext() {
            return current < lst.size();
        }

        @Override
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return lst.get(current++);
        }
    }
}