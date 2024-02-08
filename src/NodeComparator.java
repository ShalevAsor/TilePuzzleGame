import java.util.Comparator;

/**
 * This class is the comparator for the priority queue of the A* algorithm
 */
class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node, Node other) {
        return Integer.compare(node.getEstimatedCost(), other.getEstimatedCost());
    }
}
