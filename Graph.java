import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map<String, Map<String, Integer>> adjacencyList = new HashMap<>();

    public Map<String, Integer> getNeighbors(String nodeName) {
        return adjacencyList.get(nodeName);
    }

    public Set<String> getNodes() {
        return adjacencyList.keySet();
    }

    public void addEdge(String startNode, String endNode, int distance) {
        if (adjacencyList.containsKey(startNode)) {
            this.getNeighbors(startNode).put(endNode, distance);
        } else {
            Map<String, Integer> neighbors = new HashMap<>();
            neighbors.put(endNode, distance);
            adjacencyList.put(startNode, neighbors);
        }
    }

    public Integer getDistance(String startNode, String endNode) {
        return this.getNeighbors(startNode).get(endNode);
    }

    public static Graph readGraphFromFile(String filepath) {
        BufferedReader txtReader = null;
        String currentLine = null;
        String[] currentData = null;
        Graph graph = new Graph();

        try {
            txtReader = new BufferedReader(new FileReader(filepath));
            while((currentLine = txtReader.readLine()) != null) {
                currentLine = currentLine.replaceAll("[->]", "");
                currentData = currentLine.split("\\(|\\)");
                graph.addEdge(currentData[0], currentData[2], Integer.parseInt(currentData[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: could not find the input file.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("ERROR: could not read file.");
            e.getStackTrace();
            System.exit(0);
        }

        return graph;
    }

    @Override
    public String toString() {
        String outputStr = "";

        if (adjacencyList.isEmpty()) {
            outputStr += "Graph is empty!";
        } else {
            for (String eachNode : adjacencyList.keySet()) {
                if (this.getNeighbors(eachNode).isEmpty()) {
                    outputStr += eachNode + " has no neighbors.\n";
                } else {
                    for (String eachNeighbor : this.getNeighbors(eachNode).keySet()) {
                        outputStr += eachNode + "--(" + this.getDistance(eachNode, eachNeighbor) + ")-->" + eachNeighbor+ "\n";
                    }
                }
            }
        }

        return outputStr;
    }

}
