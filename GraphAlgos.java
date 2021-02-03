import java.util.*;

public class GraphAlgos {

    private static int numberOfPaths = 0;

    private enum Operation { 
        LTE, EQ; 
    } 

     public static int sumOfEdges(Graph graph, ArrayList<String> path) throws InvalidInputException {
         if (path.size() == 0) {
             throw new InvalidInputException("ERROR: Path is empty.");
         }
         int totalWeights = 0;
         for(int i = 0; i < path.size()-1; i++) {
             String currentNode = path.get(i);
             String nextNode = path.get(i+1);
             if (!graph.getNodes().contains(currentNode)) {
                 throw new InvalidInputException("ERROR: " + currentNode + " not found in graph.");
             }
             if (graph.getNeighbors(currentNode).containsKey(nextNode)) {
                 totalWeights += graph.getDistance(currentNode, nextNode);
             } else {
                 throw new InvalidInputException("NO SUCH EDGE");
             }
         }

        return totalWeights;
     }

     public static int calcShortestPath(Graph graph, String startNode, String endNode) throws InvalidInputException {
         if (!graph.getNodes().contains(startNode) || !graph.getNodes().contains(endNode)) {
             throw new InvalidInputException("ERROR: Node(s) not found in graph.");
         }

        HashMap<String, Tuple> distances = new HashMap<>();
        PriorityQueue<Tuple> priorityQueue = new PriorityQueue<Tuple>();

        for (String node : graph.getNodes()) {
            Tuple nodeAndDistance = new Tuple(node, Integer.MAX_VALUE);
            if (node.equals(startNode)) {
                nodeAndDistance.distance = 0;
            }
            distances.put(node, nodeAndDistance);
        }
        priorityQueue.add(distances.get(startNode));
    
         while (priorityQueue.size() > 0) {
            Tuple bestNodeAndDistance = priorityQueue.poll();
            String bestNode = bestNodeAndDistance.node;
            int bestDistance = bestNodeAndDistance.distance;
            if (bestDistance == 0) {
                bestNodeAndDistance.distance = Integer.MAX_VALUE;
            }

            for (String neighbor : graph.getNeighbors(bestNode).keySet()) {
                int newDist = bestDistance + graph.getDistance(bestNodeAndDistance.node, neighbor);
                if (newDist < distances.get(neighbor).distance) {
                    distances.get(neighbor).distance = newDist;
                    priorityQueue.add(distances.get(neighbor));
                }
            }

            if (bestNode.equals(endNode) && bestDistance != 0) {
                return bestDistance;
            }
        }
        throw new InvalidInputException("NO PATH FROM " + startNode + " to " + endNode);
    }   

    public static int countNumberOfPathsWithExactHops(Graph graph, String startNode, String endNode, int numberOfHops) throws InvalidInputException {
        if (!graph.getNodes().contains(startNode) || !graph.getNodes().contains(endNode)) {
            throw new InvalidInputException("ERROR: Node(s) not found in graph.");
        }
        return countNumberOfPaths(graph, startNode, endNode,  numberOfHops, Operation.EQ);
    }

    public static int countNumberOfPathsWithMaxHops(Graph graph, String startNode, String endNode, int numberOfHops) throws InvalidInputException {
        if (!graph.getNodes().contains(startNode) || !graph.getNodes().contains(endNode)) {
            throw new InvalidInputException("ERROR: Node(s) not found in graph.");
        }
        return countNumberOfPaths(graph, startNode, endNode,  numberOfHops, Operation.LTE);
    }

    private static int countNumberOfPaths(Graph graph, String startNode, String endNode, int allowedNumberOfHops, Operation op) {
        ArrayList<String> pathList = new ArrayList<String>();
        numberOfPaths = 0;
        pathList.add(startNode);
        countNumberOfPathsUtil(graph, startNode, endNode, pathList, allowedNumberOfHops, op);
        return numberOfPaths;
     }

    private static void countNumberOfPathsUtil(Graph graph, String startNode, String endNode, ArrayList<String> pathList,  int allowedNumberOfHops, Operation op) {
        int hopsSoFar = pathList.size() - 1;
        if(hopsSoFar > allowedNumberOfHops) {
            return;
        }
        if (startNode.equals(endNode) && hopsSoFar != 0) {
            if (op == Operation.LTE || ((hopsSoFar == allowedNumberOfHops) && op == Operation.EQ)) {
                numberOfPaths += 1;
                // System.out.println("pathlist: " + pathList);
                return;
            }
        }

        for (String neighbor : graph.getNeighbors(startNode).keySet()) {
            pathList.add(neighbor);
            countNumberOfPathsUtil(graph, neighbor, endNode, pathList, allowedNumberOfHops, op);
            pathList.remove(pathList.size() - 1);
        }
    }

}
