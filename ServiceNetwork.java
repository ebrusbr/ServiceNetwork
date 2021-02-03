import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Arrays.asList;

class ServiceNetwork {
    public static void main(String[] args) {
        Graph graph = Graph.readGraphFromFile("testGraph.txt");
        // System.out.println(graph);
        
        ArrayList<String> pathList = new ArrayList<String>();

        try {
            pathList.addAll(Arrays.asList("A", "B", "C"));
            System.out.println(GraphAlgos.sumOfEdges(graph, pathList));

            pathList = new ArrayList<String>();
            pathList.addAll(Arrays.asList("A", "D"));
            System.out.println(GraphAlgos.sumOfEdges(graph, pathList));

            pathList = new ArrayList<String>();
            pathList.addAll(Arrays.asList("A", "D", "C"));
            System.out.println(GraphAlgos.sumOfEdges(graph, pathList));

            pathList = new ArrayList<String>();
            pathList.addAll(Arrays.asList("A", "E", "B", "C", "D"));
            System.out.println(GraphAlgos.sumOfEdges(graph, pathList));

            pathList = new ArrayList<String>();
            pathList.addAll(Arrays.asList("A", "E", "D"));
            System.out.println(GraphAlgos.sumOfEdges(graph, pathList));

        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(GraphAlgos.countNumberOfPathsWithMaxHops(graph, "C", "C", 3));
            System.out.println(GraphAlgos.countNumberOfPathsWithExactHops(graph, "A", "C", 4));

            System.out.println(GraphAlgos.calcShortestPath(graph, "A", "C"));
            System.out.println(GraphAlgos.calcShortestPath(graph, "B", "B"));
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

    }
}