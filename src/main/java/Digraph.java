import java.util.ArrayList;
import java.util.List;

public class Digraph {

    private final int V;                 // número de vértices
    private int E;                       // número de arestas
    private List<Integer>[] adj;         // listas de adjacência
    private int[] indegree;              // grau de entrada

    @SuppressWarnings("unchecked")
    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (List<Integer>[]) new ArrayList[V];
        indegree = new int[V];

        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        indegree[w]++;
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int outdegree(int v) {
        return adj[v].size();
    }

    public int indegree(int v) {
        return indegree[v];
    }
}