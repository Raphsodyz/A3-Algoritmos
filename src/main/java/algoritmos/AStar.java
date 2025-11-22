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

public class AStar {

    public static class Vertex implements Comparable<Vertex> {

        private final int number;
        private final Map<Integer, Integer> edges;
        private final double heuristic;

        private double distance = Double.POSITIVE_INFINITY;
        private AStar.Vertex parent;

        public Vertex(int number, double heuristic, Map<Integer, Integer> edges) {
            this.number = number;
            this.edges = edges;
            this.heuristic = heuristic;
        }

        public int getNumber() {
            return this.number;
        }

        public AStar.Vertex getParent(){
            return this.parent;
        }

        public Map<Integer, Integer> getEdges() {
            return this.edges;
        }

        public double f() {
            return this.heuristic + this.distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.compare(this.f(), other.f());
        }
    }

    private final Map<Integer, AStar.Vertex> vertexMap;
    private final int start;
    private final int target;

    public AStar(int [][] adjacentMatrix, int start, int target, Heuristic.HeuristicType heuristicType) {

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

            vertexMap.put(i, new AStar.Vertex(i, distances.get(i), edges));
        }
    }

    public ResultadoAlgoritmoModel run(Heuristic.HeuristicType heuristicType) {

        vertexMap.get(this.start).setDistance(0);

        PriorityQueue<AStar.Vertex> open = new PriorityQueue<>();
        open.add(vertexMap.get(this.start));

        Set<Integer> closed = new HashSet<>();
        long inicio = System.nanoTime();

        while (!open.isEmpty()) {
            AStar.Vertex currentVertex = open.poll();

            if(closed.contains(currentVertex.getNumber())) continue;

            if (currentVertex.getNumber() == target) {
                var caminho = reconstructPath(currentVertex);
                long fim = System.nanoTime();

                return new ResultadoAlgoritmoModel("AStar", heuristicType.name(), start, target, caminho.toString().replace(", ", " -> "), caminho.size() - 1, caminho.size(), fim - inicio);
            }

            closed.add(currentVertex.getNumber());

            for (Map.Entry<Integer, Integer> currentEdge : currentVertex.getEdges().entrySet()) {
                int weight = currentEdge.getValue();
                AStar.Vertex neighbor = vertexMap.get(currentEdge.getKey());

                if (!closed.contains(neighbor.getNumber())) {
                    double tentativeG = currentVertex.distance + weight;

                    if (tentativeG < neighbor.distance) {
                        neighbor.setDistance(tentativeG);
                        neighbor.parent = currentVertex;
                        open.add(neighbor);
                    }
                }
            }
        }

        long fim = System.nanoTime();
        return new ResultadoAlgoritmoModel("AStar", heuristicType.name(), start, target, "", 0, 0, fim - inicio);
    }

    private static List<Integer> reconstructPath(AStar.Vertex current) {
        List<Integer> path = new ArrayList<>();
        while (current != null) {
            path.add(current.getNumber());
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}
