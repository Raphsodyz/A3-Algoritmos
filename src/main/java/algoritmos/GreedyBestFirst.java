package algoritmos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import algoritmos.heuristica.Heuristic;
import model.ResultadoAlgoritmoModel;

public class GreedyBestFirst {

    public static class Vertex implements Comparable<Vertex> {

        private final int number;
        private final Map<Integer, Integer> edges;
        private final double heuristic;

        private GreedyBestFirst.Vertex parent;

        public Vertex(int number, double heuristic, Map<Integer, Integer> edges) {
            this.number = number;
            this.edges = edges;
            this.heuristic = heuristic;
        }

        public int getNumber() {
            return number;
        }

        public Vertex getParent() {
            return parent;
        }

        public Map<Integer, Integer> getEdges() {
            return edges;
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.compare(this.heuristic, other.heuristic);
        }
    }

    private final Map<Integer, Vertex> vertexMap;
    private final int start;
    private final int target;

    public GreedyBestFirst(int[][] adjacentMatrix, int start, int target, Heuristic.HeuristicType heuristicType) {

        this.start = start;
        this.target = target;

        Map<Integer, Double> distances;
        if (heuristicType == Heuristic.HeuristicType.EUCLIDEAN) {
            distances = Heuristic.euclideanDistances(adjacentMatrix, target);
        } else {
            distances = Heuristic.manhattanDistances(adjacentMatrix, target);
        }

        vertexMap = new HashMap<>();
        for (int i = 0; i < adjacentMatrix.length; i++) {

            Map<Integer, Integer> edges = new HashMap<>();

            for (int j = 0; j < adjacentMatrix[i].length; j++) {
                if(adjacentMatrix[i][j] > 0) {
                    edges.put(j, adjacentMatrix[i][j]);
                }
            }

            vertexMap.put(i, new Vertex(i, distances.get(i), edges));
        }
    }

    public ResultadoAlgoritmoModel run(Heuristic.HeuristicType heuristicType) {

        PriorityQueue<Vertex> open = new PriorityQueue<>();
        open.add(vertexMap.get(start));

        Set<Integer> closed = new HashSet<>();
        long inicio = System.nanoTime();

        while (!open.isEmpty()) {
            Vertex current = open.poll();

            if (closed.contains(current.getNumber())) continue;

            if (current.getNumber() == target) {
                var caminho = reconstructPath(current);
                                long fim = System.nanoTime();

                return new ResultadoAlgoritmoModel("Greedy Best First Search", heuristicType.name(), start, target, caminho.toString().replace(", ", " -> "), caminho.size() - 1, caminho.size(), fim - inicio);
            }

            closed.add(current.getNumber());

            for (Map.Entry<Integer, Integer> entry : current.getEdges().entrySet()) {
                Vertex neighbor = vertexMap.get(entry.getKey());

                if (!closed.contains(neighbor.getNumber())) {
                    neighbor.parent = current;
                    open.add(neighbor);
                }
            }
        }

        long fim = System.nanoTime();
        return new ResultadoAlgoritmoModel("Greedy Best First Search", heuristicType.name(), start, target, "", 0, 0, fim - inicio);
    }

    private static List<Integer> reconstructPath(Vertex current) {
        List<Integer> path = new ArrayList<>();
        while (current != null) {
            path.add(current.getNumber());
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}
