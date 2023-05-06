import java.io.Serializable;

public class WineNode<Wine> implements Serializable {
    protected Wine wine;
    protected WineNode<Wine> left;
    protected WineNode<Wine> right;
    protected WineNode<Wine> next;
    protected WineNode<Wine> prev;

    public WineNode(Wine wine) {
        this.wine = wine;
    }
}