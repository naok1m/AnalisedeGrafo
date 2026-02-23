import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AnaliseGraficos {

    // Método para exportar a distribuição de graus para um CSV
    public static void gerarCsvDistribuicao(Digraph G, String nomeArquivo) {
        // Arrays para armazenar a frequência de cada grau (o tamanho máximo do grau é V-1)
        int[] freqIn = new int[G.V()];
        int[] freqOut = new int[G.V()];

        // Conta a frequência: para cada vértice, incrementamos o contador do seu respectivo grau
        for (int v = 0; v < G.V(); v++) {
            freqIn[G.indegree(v)]++;
            freqOut[G.outdegree(v)]++;
        }

        // Escreve os dados no arquivo CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("Grau,Frequencia_Entrada,Frequencia_Saida");

            for (int k = 0; k < G.V(); k++) {
                // Filtra para salvar apenas graus que realmente aparecem na rede, reduzindo o tamanho do arquivo
                if (freqIn[k] > 0 || freqOut[k] > 0) {
                    writer.println(k + "," + freqIn[k] + "," + freqOut[k]);
                }
            }
            System.out.println("Arquivo " + nomeArquivo + " gerado com sucesso! Abra no Excel para gerar o gráfico.");
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }
}