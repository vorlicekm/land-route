package com.pwc.test.landroute.service;

import com.pwc.test.landroute.model.GraphNode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {
    public List<String> findShortestPathWithLimitedEdges(Map<String, List<String>> graph, String start, String end, int maxEdges) {
        if (start.equals(end)) {
            return Collections.singletonList(start); // If the start and end nodes are the same, return their list as a path.
        }

        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(new GraphNode(start, new ArrayList<>(Collections.singletonList(start)), 0));

        while (!queue.isEmpty()) {
            GraphNode currentNode = queue.poll();
            String currentNodeName = currentNode.getName();
            List<String> currentPath = currentNode.getPath();
            int edgeCount = currentNode.getEdgeCount();

            // Browse the neighbors of the current node.
            for (String neighbor : graph.getOrDefault(currentNodeName, Collections.emptyList())) {
                // Let's check if our edge count has exceeded the limit.
                if (edgeCount + 1 <= maxEdges) {
                    if (neighbor.equals(end)) {
                        // If we have found the end node, we retrace the path.
                        currentPath.add(neighbor);
                        return currentPath;
                    } else {
                        // Otherwise, we're continuing the search.
                        List<String> newPath = new ArrayList<>(currentPath);
                        newPath.add(neighbor);
                        queue.add(new GraphNode(neighbor, newPath, edgeCount + 1));
                    }
                }
            }
        }

        return null; // If we have not arrived at the goal, there is no path with a given edge count constraint.
    }

    //TODO
    public List<String> findShortestPathWithShortestWay(Map<String, List<String>> graph, String start, String end, int maxEdges) {
        // more accurate results, maybe use Dijkstra's algorithm, countries distance by latitude and longitude (field latlng) as weight
        // not applicable, because of more time to implement
        return null;
    }
}