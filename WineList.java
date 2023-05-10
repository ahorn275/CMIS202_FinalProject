// **********************************************************************************
// Title: Wine List Class
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: WineList.java
// Description: Creates a linked list implementation of a Serializable collection
//     of Wine objects and provides methods for adding and removing wines, getting
//     a wine from a specified index, checking if a wine is in the list, getting the
//     size of the list, clearing the list, as well as two options for merge sort: one
//     which uses Comparable and another which uses a specified Comparator
// **********************************************************************************
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class WineList implements Serializable, Iterable<Wine> {
    private WineNode head;
    private WineNode tail;
    private int size;

    /** Construct an empty wine list */
    public WineList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /** Add a wine to the list */
    public void add(Wine wine) {
        WineNode<Wine> newNode = new WineNode(wine);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    /** Remove a wine from the list and return true if removed */
    public boolean remove(Wine wine) {
        if (head == null) {
            return false;
        }
        if (head.wine.equals(wine)) {
            head = head.next;
            size--;
            return true;
        }
        WineNode<Wine> prev = head;
        WineNode<Wine> current = head.next;
        while (current != null) {
            if (current.wine.equals(wine)) {
                prev.next = current.next;
                if (current == tail) {
                    tail = prev;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /** Get a wine from a specified index in the list */
    public Wine get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        WineNode<Wine> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.wine;
    }

    /** Check if a wine is in this list */
    public boolean contains(Wine wine) {
        WineNode<Wine> current = head;
        while (current != null) {
            if (current.wine.equals(wine)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    /** Return size of wine list */
    public int size() {
        return size;
    }

    /** Clear the list */
    public void clear() {
        size = 0;
        head = tail = null;
    }

    /** Divide-and-Conquer merge sort using Comparable - O(n log n) time complexity */
    public void mergeSort() {
        if (head != null) {
            head = mergeSort(head);
            tail = head;
            while (tail.next != null) {
                tail = tail.next;
            }
        }
    }

    private WineNode<Wine> mergeSort(WineNode<Wine> head) {
        if (head.next == null) {
            return head;
        }
        WineNode<Wine> slow = head;
        WineNode<Wine> fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        WineNode<Wine> right = mergeSort(slow.next);
        slow.next = null;
        WineNode<Wine> left = mergeSort(head);
        return merge(left, right);
    }

    private WineNode<Wine> merge(WineNode<Wine> left, WineNode<Wine> right) {
        WineNode<Wine> merged = new WineNode<>(null);
        WineNode<Wine> cur = merged;
        while (left != null && right != null) {
            if (left.wine.compareTo(right.wine) <= 0) {
                cur.next = left;
                left = left.next;
            } else {
                cur.next = right;
                right = right.next;
            }
            cur = cur.next;
        }
        cur.next = (left != null) ? left : right;
        return merged.next;
    }

    /** Merge sort using a specified Comparator - O(n log n) time complexity */
    public void mergeSort(Comparator<Wine> cmp) {
        head = mergeSort(head, cmp);
    }

    private WineNode<Wine> mergeSort(WineNode<Wine> start, Comparator<Wine> cmp) {
        if (start == null || start.next == null) {
            return start;
        }
        WineNode<Wine> middle = getMiddle(start);
        WineNode<Wine> nextOfMiddle = middle.next;
        middle.next = null;
        WineNode<Wine> left = mergeSort(start, cmp);
        WineNode<Wine> right = mergeSort(nextOfMiddle, cmp);
        return merge(left, right, cmp);
    }

    private WineNode<Wine> getMiddle(WineNode<Wine> start) {
        if (start == null) {
            return null;
        }
        WineNode<Wine> slow = start;
        WineNode<Wine> fast = start;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private WineNode<Wine> merge(WineNode<Wine> left, WineNode<Wine> right, Comparator<Wine> cmp) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        WineNode<Wine> result;
        if (cmp.compare(left.wine, right.wine) <= 0) {
            result = left;
            result.next = merge(left.next, right, cmp);
        } else {
            result = right;
            result.next = merge(left, right.next, cmp);
        }
        return result;
    }

    /** Returns whether the list is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return an iterator */
    @Override
    public Iterator<Wine> iterator() {
        return new WineListIterator();
    }

    /** Wine List Iterator class */
    private class WineListIterator implements Iterator<Wine> {
        private WineNode<Wine> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Wine next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Wine wine = current.wine;
            current = current.next;
            return wine;
        }
    }
}
