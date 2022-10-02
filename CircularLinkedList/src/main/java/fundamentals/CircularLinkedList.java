package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n = 0;          // size of the stack
    private Node last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public static void main(String args[]) {
        CircularLinkedList<Integer> cl = new CircularLinkedList<Integer>();
        cl.enqueue(8);
        cl.enqueue(18);
        cl.enqueue(98);
        cl.enqueue(666);
        cl.remove(1);
        cl.remove(1);
        System.out.println(cl.size());
        for (Integer elem : cl) {
            System.out.println(elem);
        }
    }
    public CircularLinkedList() {
        n = 0;
        last = null;
    }

    public boolean isEmpty() {
         return n==0;
    }

    public int size() {
         return n;
    }

    private long nOp() {
        return nOp;
    }



    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        n++;
        nOp++;
        if (last==null) {
            last = new Node(item, null);
            last.next = last;
            return;
        }
        if (last==last.next) {
            last.next = new Node(item, last);
            last = last.next;
            return;
        }
        last.next = new Node(item, last.next);
        last = last.next;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) {
        if (n==0) return null;
        if (index>=n) throw new IndexOutOfBoundsException();
        nOp++;
        if (n==1) {
            Node temp = last;
            last = null;
            n = 0;
            return temp.item;
        }
        Node prev = last;
        Node current = last.next;
         for (int i=index; i > 0; i--) {
             prev = current;
             current = current.next;
         }
         n--;
         prev.next = current.next;
         return current.item;
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator(last, nOp);
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {
        Node current;
        Node first;
        long nop_current;
        boolean used = false;
        // TODO You probably need a constructor here and some instance variables
        public ListIterator(Node current1, long nop) {
            if (current1==null) {
                first = null; current = null; nop_current = nop;
                return;
            }
            first = current1.next;
            current = current1.next;
            nop_current = nop;
        }

        @Override
        public boolean hasNext() {
            if (current == null) return false;
             return !(current==first && used);
        }

        @Override
        public Item next() {
            if (nop_current != nOp) throw new ConcurrentModificationException();
            if (current == null) return null;
            Node temp = current;
            current = current.next;
            used = true;
            return temp.item;
        }

    }

}