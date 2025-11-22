package model;

public record ResultadoAlgoritmoModel (
    String Algoritmo,
    String Heuristica,
    int Origem,
    int Destino,
    String Caminho,
    int Custo,
    int NosExpandidos,
    long TempoExecucao
) 
{
    @Override
    public String toString() 
    {
        return String.format(
            "Algoritmo: %s\n" +
            "Heurística: %s\n" +
            "Origem: %d\n" +
            "Destino: %d\n" +
            "Caminho: %s\n" +
            "Custo: %d\n" +
            "Nós Expandidos: %d\n" +
            "Tempo de Execução: %d ms\n",
            Algoritmo,
            Heuristica == null ? "N/A" : Heuristica,
            Origem,
            Destino,
            Caminho,
            Custo,
            NosExpandidos,
            TempoExecucao
        );
    }

    public void GeraArquivoResultado(String nomeArquivo) {
        var conteudo = this.toString();
        try 
        {
            var dir = java.nio.file.Paths.get("src", "main", "resources", "resultado");
            java.nio.file.Files.createDirectories(dir);
            java.nio.file.Files.writeString(
                dir.resolve(nomeArquivo),
                conteudo,
                java.nio.charset.StandardCharsets.UTF_8,
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (java.io.IOException e) {
            System.err.println("Erro ao gerar o arquivo de resultado: " + e.getMessage());
        }
    }
}