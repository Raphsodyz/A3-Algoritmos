package algoritmos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.ResultadoAlgoritmoModel;

public class DFS {
    //Ajustado para não usar recursão como estava no template. Estava dando StackOverflow no grafo de 64x64.
    public static ResultadoAlgoritmoModel run(Map<Integer, List<Integer>> graph, int start) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> stack = new ArrayList<>();
        StringBuilder caminho = new StringBuilder();
        int custo = 0;

        stack.add(start);

        long inicioExecucao = System.nanoTime();
        while (!stack.isEmpty()) {
            int node = stack.remove(stack.size() - 1);

            if (!visited.contains(node)) {
                visited.add(node);
                caminho.append(node).append(" -> ");
                custo++;

                List<Integer> neighbors = graph.getOrDefault(node, List.of());
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int next = neighbors.get(i);
                    
                    if (!visited.contains(next)) 
                        stack.add(next);
                }
            }
        }

        if (caminho.length() >= 4) {
            caminho.setLength(caminho.length() - 4);
        }

        long fimExecucao = System.nanoTime();
        return new ResultadoAlgoritmoModel("DFS", "", start, 0, caminho.toString(), custo, visited.size(), fimExecucao - inicioExecucao);
    }
}
