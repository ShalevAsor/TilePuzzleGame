import java.util.Comparator;

/**
 * This class is the comparator for the priority queue of the A* algorithm
 */
class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node, Node other) {
        int estimatedCostIsEqual = Integer.compare(node.getEstimatedCost(), other.getEstimatedCost());
        if(estimatedCostIsEqual == 0){
            return Integer.compare(node.getGeneratedLevel(), other.getGeneratedLevel());
        }
        else{
            return Integer.compare(node.getEstimatedCost(), other.getEstimatedCost());

        }
    }
}
