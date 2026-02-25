import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnalisadorSlashdot {

    // Método para calcular a densidade
    public static double calcularDensidade(Digraph G) {
        long V = G.V();
        long E = G.E();
        if (V <= 1) return 0.0;

        return (double) E / (V * (V - 1));
    }

    public static void main(String[] args) {
        String caminhoArquivo = "C:\\Users\\thiago\\Área de Trabalho\\GraphSlashdot\\src\\main\\java\\dataset.txt";

        int maiorId = 0;

        System.out.println("Iniciando a leitura do dataset...");

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {

                if (linha.startsWith("#") || linha.trim().isEmpty()) continue;

                String[] partes = linha.trim().split("\\s+");
                if (partes.length >= 2) {
                    int origem = Integer.parseInt(partes[0]);
                    int destino = Integer.parseInt(partes[1]);


                    maiorId = Math.max(maiorId, Math.max(origem, destino));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo na primeira passagem: " + e.getMessage());
            return;
        }

        int numVertices = maiorId + 1;
        Digraph G = new Digraph(numVertices);
        System.out.println("Grafo instanciado com " + numVertices + " vértices.");


        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("#") || linha.trim().isEmpty()) continue;

                String[] partes = linha.trim().split("\\s+");
                if (partes.length >= 3) {
                    int origem = Integer.parseInt(partes[0]);
                    int destino = Integer.parseInt(partes[1]);
                    // int sinal = Integer.parseInt(partes[2]); // Lemos o sinal, mas a classe Digraph não o utiliza

                    G.addEdge(origem, destino);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo na segunda passagem: " + e.getMessage());
            return;
        }

        System.out.println("Leitura concluída com sucesso!\n");


        System.out.println("=== MÉTRICAS GLOBAIS ===");
        System.out.println("Número de Vértices (V): " + G.V());
        System.out.println("Número de Arestas (E): " + G.E());

        // Densidade: D = E / (V * (V - 1))
        System.out.printf("Densidade do Grafo: %.8f\n", calcularDensidade(G));

        int maxOutDegree = 0;
        int noMaxOut = -1;

        int maxInDegree = 0;
        int noMaxIn = -1;

        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) > maxOutDegree) {
                maxOutDegree = G.outdegree(v);
                noMaxOut = v;
            }
            if (G.indegree(v) > maxInDegree) {
                maxInDegree = G.indegree(v);
                noMaxIn = v;
            }
        }

        System.out.println("\n=== DESTAQUES DA REDE ===");
        System.out.println("Usuário que mais avaliou os outros (Maior Out-degree): Vértice " + noMaxOut + " com " + maxOutDegree + " arestas saindo.");
        System.out.println("Usuário mais avaliado pela rede (Maior In-degree): Vértice " + noMaxIn + " com " + maxInDegree + " arestas chegando.");
        AnaliseGraficos.gerarCsvDistribuicao(G, "distribuicao_graus.csv");
    }

}