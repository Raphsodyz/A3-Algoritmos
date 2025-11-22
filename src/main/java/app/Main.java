package app;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algoritmos.AStar;
import algoritmos.BFS;
import algoritmos.DFS;
import algoritmos.Dijkstra;
import algoritmos.GreedyBestFirst;
import algoritmos.heuristica.Heuristic;

public class Main{
    public static void main(String[] args) throws IOException {
        //Valida os argumentos informados na execução do programa
        ValidaArgumentosExecucao(args);
        var streamArquivo = ObtemStreamArquivo(args[0]);

        //Lê a matriz do arquivo informado
        int[][] matrizSelecionada = LeMatrizDoArquivo(streamArquivo);

        //Obtem lista de adjacência a partir da matriz lida
        Map<Integer, List<Integer>> listaAdjacencia = ConverteMatrizParaListaAdjacencia(matrizSelecionada);
        int verticeInicial = Integer.parseInt(args[1]);
        int verticeFinal = Integer.parseInt(args[2]);

        //DFS
        var resultadoDfs = DFS.run(listaAdjacencia, verticeInicial);
        System.out.println("--------------------------------- DFS ---------------------------------");
        System.out.println(resultadoDfs.toString());
        
        //BFS
        var resultadoBfs = BFS.run(listaAdjacencia, verticeInicial);
        System.out.println("--------------------------------- BFS ---------------------------------");
        System.out.println(resultadoBfs.toString());

        //Djikstra
        System.out.println("--------------------------------- Djikstra ---------------------------------");
        var djikstra = new Dijkstra(matrizSelecionada);
        var resultadoDjikstra = djikstra.run(verticeInicial);
        System.out.println(resultadoDjikstra.toString());

        //AStar
        System.out.println("--------------------------------- AStar - Manhattan ---------------------------------");
        var aStarManhattan = new AStar(matrizSelecionada, verticeInicial, verticeFinal, Heuristic.HeuristicType.MANHATTAN);
        var resultadoAStarManhattan = aStarManhattan.run(Heuristic.HeuristicType.MANHATTAN);
        System.out.println(resultadoAStarManhattan.toString());

        System.out.println("--------------------------------- AStar - Euclidiana ---------------------------------");
        var aStarEuclidiana = new AStar(matrizSelecionada, verticeInicial, verticeFinal, Heuristic.HeuristicType.EUCLIDEAN);
        var resultadoAStarEuclidiana = aStarEuclidiana.run(Heuristic.HeuristicType.EUCLIDEAN);
        System.out.println(resultadoAStarEuclidiana.toString());

        //Greedy Best First Search
        System.out.println("--------------------------------- GBS - Manhattan ---------------------------------");
        var greedyBestFirstSearchManhattan = new GreedyBestFirst(matrizSelecionada, verticeInicial, verticeFinal, Heuristic.HeuristicType.MANHATTAN);
        var resultadoGBSManhattan = greedyBestFirstSearchManhattan.run(Heuristic.HeuristicType.MANHATTAN);
        System.out.println(resultadoGBSManhattan.toString());

        System.out.println("--------------------------------- GBS - Euclidiana ---------------------------------");
        var greedyBestFirstSearchEuclidiana = new GreedyBestFirst(matrizSelecionada, verticeInicial, verticeFinal, Heuristic.HeuristicType.EUCLIDEAN);
        var resultadoGBSEuclidiana = greedyBestFirstSearchEuclidiana.run(Heuristic.HeuristicType.EUCLIDEAN);
        System.out.println(resultadoGBSEuclidiana.toString());

        //Gera arquivos de resultado
        resultadoDfs.GeraArquivoResultado(args[0] + ".dfs.txt");
        resultadoBfs.GeraArquivoResultado(args[0] + ".bfs.txt");
        resultadoDjikstra.GeraArquivoResultado(args[0] + ".djikstra.txt");
        resultadoAStarManhattan.GeraArquivoResultado(args[0] + ".a.manhattan.txt");
        resultadoAStarEuclidiana.GeraArquivoResultado(args[0] + ".a.euclidiana.txt");
        resultadoGBSManhattan.GeraArquivoResultado(args[0] + ".gbs.manhattan.txt");
        resultadoGBSEuclidiana.GeraArquivoResultado(args[0] + ".gbs.euclidiana.txt");
    }

    private static void ValidaArgumentosExecucao(String[] args) {
        if (args.length == 0)
            System.err.println("Atenção! É necessário informar a matriz de entrada, vértice de origem e destino.");

        if (args.length < 3)
            System.err.println("Atenção! Não foram informados todos os valores necessários para executar o programa. É necessário informar o nome da matriz de entrada, inteiro do vértice de origem e inteiro do vértice de destino.");

        if (!args[1].matches("\\d+") || !args[2].matches("\\d+"))
            System.err.println("Atenção! Os valores do vértice de origem e destino devem ser inteiros e conter somente números.");
    }

    private static InputStream ObtemStreamArquivo(String nomeArquivo){
        var arquivo = Main.class.getResourceAsStream("/matrizes/" + nomeArquivo);
        
        if (arquivo == null)
            System.err.println("Atenção! O arquivo informado não existe.");

        return arquivo;
    }

    private static int[][] LeMatrizDoArquivo(InputStream stream) throws IOException {
        List<String> lines = new java.io.BufferedReader(new java.io.InputStreamReader(stream))
            .lines()
            .toList();

        int linhas = lines.size();
        int[][] matrix = null;

        for (int i = 0; i < linhas; i++) {
            String[] values = lines.get(i).trim().split("\\s+");
            
            if (matrix == null) {
                int colunas = values.length;
                matrix = new int[linhas][colunas];
            }

            for (int j = 0; j < values.length; j++) {
                matrix[i][j] = Integer.parseInt(values[j]);
            }
        }

        return matrix;
    }

    private static Map<Integer, List<Integer>> ConverteMatrizParaListaAdjacencia(int[][] matriz) {
        Map<Integer, List<Integer>> listaAdjacencia = new HashMap<>();

        int n = matriz.length;

        for (int i = 0; i < n; i++) {
            List<Integer> neighbors = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != 0)
                    neighbors.add(j);
            }

            listaAdjacencia.put(i, neighbors);
        }

        return listaAdjacencia;
    }
}