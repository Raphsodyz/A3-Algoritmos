package algoritmos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import model.ResultadoAlgoritmoModel;

public class Dijkstra {
    public static class Vertex implements Comparable<Vertex> {

        private final int number;
        private final Map<Integer, Integer> edges;
        private int distance = Integer.MAX_VALUE;

        public Vertex(int number, Map<Integer, Integer> edges) {
            this.number = number;
            this.edges = edges;
        }

        public int getNumber() {
            return number;
        }

        public Map<Integer, Integer> getEdges() {
            return edges;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    private final Map<Integer, Dijkstra.Vertex> vertexMap;

    public Dijkstra(int [][] adjacentMatrix) {

        vertexMap = new HashMap<>();

        for (int i = 0; i < adjacentMatrix.length; i++) {

            Map<Integer, Integer> edges = new HashMap<>();

            for (int j = 0; j < adjacentMatrix[i].length; j++) {
                if(adjacentMatrix[i][j] > 0) {
                    edges.put(j, adjacentMatrix[i][j]);
                }
            }

            vertexMap.put(i, new Vertex(i, edges));
        }
    }

    public ResultadoAlgoritmoModel run(int verticeInicial) {

        vertexMap.get(verticeInicial).setDistance(0);

        PriorityQueue<Dijkstra.Vertex> toProcess = new PriorityQueue<>();
        toProcess.add(vertexMap.get(verticeInicial));

        Set<Integer> visited = new HashSet<>();

        long inicioExecucao = System.nanoTime();
        while (!toProcess.isEmpty()) {

            Dijkstra.Vertex currentVertex = toProcess.poll();
            if(visited.contains(currentVertex.getNumber())) {
                continue;
            }

            visited.add(currentVertex.getNumber());

            for (Map.Entry<Integer, Integer> currentEdge : currentVertex.getEdges().entrySet()) {
                int weight = currentEdge.getValue();
                Dijkstra.Vertex neighbor = vertexMap.get(currentEdge.getKey());

                if (!visited.contains(neighbor.getNumber())) {
                    int novaDistancia = currentVertex.getDistance() + weight;
                    if (novaDistancia < neighbor.getDistance()) {
                        neighbor.setDistance(novaDistancia);
                        toProcess.add(neighbor);
                    }
                }
            }
        }

        var distancias = vertexMap.values().stream()
                .collect(Collectors.toMap(Vertex::getNumber, Vertex::getDistance));

        StringBuilder caminho = new StringBuilder();
        int custo = 0;

        for (Map.Entry<Integer, Integer> entrada : distancias.entrySet()) {
            caminho.append("{ ");
            caminho.append(0);
            caminho.append(" -> ");
            caminho.append(entrada.getKey());
            caminho.append(" ");
            caminho.append("custo ");
            caminho.append(entrada.getValue());
            caminho.append(" }, ");
            custo += entrada.getValue();
        }

        long fimExecucao = System.nanoTime();

        if (caminho.length() >= 2) {
            caminho.setLength(caminho.length() - 2);
        }

        return new ResultadoAlgoritmoModel("Djikstra", "", verticeInicial, 0, caminho.toString(), custo, visited.size(), fimExecucao - inicioExecucao);
    }
}