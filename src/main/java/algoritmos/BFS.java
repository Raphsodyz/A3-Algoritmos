package algoritmos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.ResultadoAlgoritmoModel;

public class BFS {
    public static ResultadoAlgoritmoModel run(Map<Integer, List<Integer>> graph, int startNode) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        LinkedList<Integer> queue = new LinkedList<>();
        List<Integer> caminho = new ArrayList<>();
        
        long inicio = System.nanoTime();

        queue.add(startNode);
        visited[startNode] = true;

        while (!queue.isEmpty()) {
            int node = queue.removeFirst();
            caminho.add(node);

            for (int neigh : graph.get(node)) {
                if (!visited[neigh]) {
                    visited[neigh] = true;
                    queue.add(neigh);
                }
            }
        }

        long fim = System.nanoTime();
        return new ResultadoAlgoritmoModel("BFS", "", startNode, 0, caminho.toString().replace(", ", " -> "), caminho.size(), caminho.size(), fim - inicio);
    }
}
