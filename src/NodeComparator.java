import java.util.Comparator;

/**
 * This class is the comparator for the priority queue of the A* algorithm
 */
class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node, Node other) {
        int totalCostToNode = node.getCostToNode() + node.getHeuristic();
        int totalCostToOther = other.getCostToNode() + other.getHeuristic();

        return Integer.compare(totalCostToNode, totalCostToOther);
    }
}
