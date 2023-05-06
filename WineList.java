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

    /** Quick Sort using Comparable
     *     O(n log n) in the average case and O(n^2) in the worst case */
    public void quickSort() {
        quickSort(head, tail);
    }

    private void quickSort(WineNode<Wine> left, WineNode<Wine> right) {
        if (left == null || right == null || left == right || left.prev == right) {
            return;
        }

        WineNode<Wine> pivot = partition(left, right);
        quickSort(left, pivot.prev);
        quickSort(pivot.next, right);
    }

    private WineNode<Wine> partition(WineNode<Wine> left, WineNode<Wine> right) {
        Wine pivot = right.wine;
        WineNode<Wine> i = left.prev;

        for (WineNode<Wine> j = left; j != right; j = j.next) {
            if (j.wine.compareTo(pivot) <= 0) {
                i = (i == null) ? left : i.next;
                swap(i, j);
            }
        }

        i = (i == null) ? left : i.next;
        swap(i, right);

        return i;
    }

    private void swap(WineNode<Wine> a, WineNode<Wine> b) {
        if (a == null || b == null || a == b) {
            return;
        }

        Wine temp = a.wine;
        a.wine = b.wine;
        b.wine = temp;
    }


    /** Quick Sort that takes in a Comparator
     *      O(n log n) in the average case and O(n^2) in the worst case */
    public void quickSort(Comparator<Wine> cmp) {
        quickSort(head, tail, cmp);
    }

    private void quickSort(WineNode<Wine> left, WineNode<Wine> right, Comparator<Wine> cmp) {
        if (left == null || right == null || left == right || left.prev == right) {
            return;
        }

        WineNode<Wine> pivot = partition(left, right, cmp);
        quickSort(left, pivot.prev, cmp);
        quickSort(pivot.next, right, cmp);
    }

    private WineNode<Wine> partition(WineNode<Wine> left, WineNode<Wine> right, Comparator<Wine> cmp) {
        Wine pivot = right.wine;
        WineNode<Wine> i = left.prev;

        for (WineNode<Wine> j = left; j != right; j = j.next) {
            if (cmp.compare(j.wine, pivot) <= 0) {
                i = (i == null) ? left : i.next;
                swap(i, j);
            }
        }

        i = (i == null) ? left : i.next;
        swap(i, right);

        return i;
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
