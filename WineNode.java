// **********************************************************************************
// Title: Wine Node
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: WineNode.java
// Description: Creates a Serializable WineNode for use in WineList and WineTree.
//    The wine variable stores a Wine object. The left and right variables represent
//    the left and right children of the current node in a binary search tree, and the
//    next and prev variables represent the next and previous nodes in a linked list.
//    The index variable represents the position of the current node in the list.
// **********************************************************************************
import java.io.Serializable;

public class WineNode<Wine> implements Serializable {
    protected Wine wine;
    protected WineNode<Wine> left;
    protected WineNode<Wine> right;
    protected WineNode<Wine> next;
    protected WineNode<Wine> prev;
    protected int index;

    public WineNode(Wine wine) {
        this.wine = wine;
        this.index = index;
    }
}
