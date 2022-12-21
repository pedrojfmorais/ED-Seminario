/**
 * Class RopeNode
 **/
public class RopeNode {
    RopeNode left, right;
    String data;
    int weight;

    /**
     * Constructor
     **/
    public RopeNode(String data) {
        this.data = data;
        left = null;
        right = null;
        weight = data.length();
    }

    /**
     * Constructor
     **/
    public RopeNode() {
        data = null;
        left = null;
        right = null;
        weight = 0;
    }
}
