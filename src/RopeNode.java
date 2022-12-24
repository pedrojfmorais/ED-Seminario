public class RopeNode {
    RopeNode left, right;
    String data;
    int weight;

    public RopeNode(String data) {
        this.data = data;
        left = null;
        right = null;
        weight = data.length();
    }

    public RopeNode() {
        data = null;
        left = null;
        right = null;
        weight = 0;
    }
}
