package algoritmos.heuristica;

import java.util.HashMap;
import java.util.Map;

public class Heuristic {


    public static enum HeuristicType {
        MANHATTAN,
        EUCLIDEAN
    }


    private Heuristic() {
        throw new RuntimeException("Utility class");
    }


    public static Map<Integer, Double> manhattanDistances(int[][] adjacentMatrix, int target) {
        int [] targetCoords = getCoordinates(adjacentMatrix.length, target);

        Map<Integer, Double> distances = new HashMap<>();
            for (int i = 0; i < adjacentMatrix.length; i++) {
            int [] startCoords = getCoordinates(adjacentMatrix.length, i);
            distances.put(i, manhattanDistance(startCoords, targetCoords));
        }

        return distances;
    }


    public static Map<Integer, Double> euclideanDistances(int[][] adjacentMatrix, int target) {
        int [] targetCoords = getCoordinates(adjacentMatrix.length, target);

        Map<Integer, Double> distances = new HashMap<>();
            for (int i = 0; i < adjacentMatrix.length; i++) {
            int [] startCoords = getCoordinates(adjacentMatrix.length, i);
            distances.put(i, euclideanDistance(startCoords, targetCoords));
        }

        return distances;
    }


    private static Double manhattanDistance(int[] startCoords, int[] targetCoords) {
        return (double) (Math.abs(startCoords[0] - targetCoords[0]) + Math.abs(startCoords[1] - targetCoords[1]));
    }


    private static Double euclideanDistance(int[] startCoords, int[] targetCoords) {
        return Math.sqrt(Math.pow(startCoords[0] - targetCoords[0], 2) + Math.pow(startCoords[1] - targetCoords[1], 2));
    }


    private static int[] getCoordinates(int size, int node) {
        int dimensionSize = (int) Math.sqrt(size);
        int row = node / dimensionSize;
        int col = node % dimensionSize;
        return new int[]{row, col};
    }
}