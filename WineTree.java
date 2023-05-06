import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

public class WineTree implements Serializable, Iterable<Wine> {
    private WineNode<Wine> root;
    private int size = 0;
    private Comparator<Wine> comparator;

    /** Default WineTree compares wines by their color */
    public WineTree() {
        this.comparator = WineComparators.byColor();
    }

    /** Create a WineTree with a specified comparator */
    public WineTree(Comparator<Wine> comparator) {
        this.comparator = comparator;
    }

    /** Returns true if there is a wine with the same name */
    public boolean search(Wine wine) {
        WineNode<Wine> current = root; // Start from the root

        while (current != null) {
            if (wine.compareTo(current.wine) < 0) {
                current = current.left;
            }
            else if (wine.compareTo(current.wine) > 0) {
                current = current.right;
            }
            else // wine matches current.wine
                return true; // Wine is found
        }

        return false; // Wine not found
    }

    /** Insert a wine into the wine tree */
    public boolean insert(Wine wine) {
        if (root == null)
            root = new WineNode<Wine>(wine);
        else {
            // Locate the parent node
            WineNode<Wine> parent = null;
            WineNode<Wine> current = root;
            while (current != null) {
                if (comparator.compare(wine, current.wine) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (comparator.compare(wine, current.wine) > 0) {
                    parent = current;
                    current = current.right;
                }
                else
                    return false; // Duplicate node not inserted
            }

            // Create the new node and attach it to the parent node
            if (comparator.compare(wine, (Wine)parent.wine) < 0)
                parent.left = new WineNode<>(wine);
            else
                parent.right = new WineNode<>(wine);
        }

        size++;
        return true; // Element inserted successfully
    }

    /** Delete a wine from the wine tree */
    public boolean delete(Wine wine) {
        // Locate the node to be deleted and also locate its parent node
        WineNode<Wine> parent = null;
        WineNode<Wine> current = root;

        while (current != null) {
            if (comparator.compare(wine, current.wine) < 0) {
                parent = current;
                current = current.left;
            }
            else if (comparator.compare(wine, current.wine) > 0) {
                parent = current;
                current = current.right;
            }
            else
                break; // Wine is in the tree pointed at current
        }

        if (current == null)
            return false; // Wine is not in the tree

        // Case 1: current has no left child
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            }
            else {
                if (comparator.compare(wine, parent.wine) < 0)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
        }
        else {
            // Case 2: The current node has a left child
            WineNode<Wine> parentOfRightMost = current;
            WineNode<Wine> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the wine in current by the wine in rightMost
            current.wine = rightMost.wine;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;
            else
                // Special case: parentOfRightMost == current
                parentOfRightMost.left = rightMost.left;
        }

        size--;
        return true; // Element deleted successfully
    }

    /** Remove all elements from the tree */
    public void clear() {
        root = null;
        size = 0;
    }

    /** Finds a wine by name */
    public Wine findByName(String name) {
        WineNode<Wine> current = root;

        while (current != null) {
            if (name.equalsIgnoreCase(current.wine.getName()))
                return current.wine;
            else if (name.compareToIgnoreCase(current.wine.getName()) < 0)
                current = current.left;
            else
                current = current.right;
        }

        return null; // Wine not found
    }

    /** Finds a list of wines by color */
    public WineList findByColor(String color) {
        WineList result = new WineList();
        findByColorHelper(color, root, result);
        return result;
    }

    private void findByColorHelper(String color, WineNode<Wine> current, WineList result) {
        if (current == null) {
            return;
        }
        findByRegionHelper(color, current.left, result);
        if (current.wine.getColor().toLowerCase().contains(color.toLowerCase())) {
            result.add(current.wine);
        }
        findByRegionHelper(color, current.right, result);
    }

    /** Finds a list of wines by grape */
    public WineList findByGrape(String grape) {
        WineList result = new WineList();
        findByGrapeHelper(grape, root, result);
        return result;
    }
    private void findByGrapeHelper(String grape, WineNode<Wine> current, WineList result) {
        if (current == null) {
            return;
        }
        findByGrapeHelper(grape, current.left, result);
        if (current.wine.getGrape().equalsIgnoreCase(grape))
            result.add(current.wine);
        findByGrapeHelper(grape, current.right, result);
    }

    /** Finds a list of wines by producer */
    public WineList findByProducer(String producer) {
        WineList result = new WineList();
        findByProducerHelper(producer, root, result);
        return result;

    }
    private void findByProducerHelper(String producer, WineNode<Wine> current, WineList result) {
        if (current == null) {
            return;
        }
        findByProducerHelper(producer, current.left, result);
        if (current.wine.getProducer().equalsIgnoreCase(producer)) {
            result.add(current.wine);
        }
        findByProducerHelper(producer, current.right, result);
    }

    /** Finds a list of wines by region (does not have to be an exact match) */
    public WineList findByRegion(String region) {
        WineList result = new WineList();
        findByRegionHelper(region, root, result);
        return result;
    }
    private void findByRegionHelper(String region, WineNode<Wine> current, WineList result) {
        if (current == null) {
            return;
        }
        findByRegionHelper(region, current.left, result);
        if (current.wine.getRegion().toLowerCase().contains(region.toLowerCase())) {
            result.add(current.wine);
        }
        findByRegionHelper(region, current.right, result);
    }

    /** Save tree to file */
    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {

            out.writeObject(this);

            // Flush the output stream to ensure all data is written to the file
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Return an iterator */
    @Override
    public Iterator<Wine> iterator() {
        return new WineTreeIterator();
    }

    /** Wine Tree Iterator uses inorder traversal */
    private class WineTreeIterator implements Iterator<Wine> {
        // Store the elements in a list
        private WineList list = new WineList();
        private int current = 0; // Point to the current element in list

        public WineTreeIterator() {
            inorder();
        }

        /** Inorder traversal from the root */
        private void inorder() {
            inorder(root);
        }

        /** Inorder traversal from a subtree */
        private void inorder(WineNode<Wine> root) {
            if (root == null) return;
            inorder(root.left);
            list.add(root.wine);
            inorder(root.right);
        }

        @Override /** More wines for traversing? */
        public boolean hasNext() {
            return current < list.size();
        }

        @Override /** Get the current wine and move to the next */
        public Wine next() {
            return list.get(current++);
        }

        @Override // Remove the wine returned by the last next()
        public void remove() {
            if (current == 0) // next() has not been called yet
                throw new IllegalStateException();

            delete(list.get(--current));
            list.clear(); // Clear the list
            inorder(); // Rebuild the list
        }
    }
}
