
public class Main {
    public static void main(String[] args) {
        In in = new In("Dados/LA_Eurelizada.txt");

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        int[] indegree = new int[G.V()];
        int[] outdegree = new int[G.V()];
        boolean balanceado = true;

        for (int v = 0; v < G.V(); v++) {
            outdegree[v] = G.outdegree(v);
            for (DirectedEdge e : G.adj(v)) {
                indegree[e.to()]++;
            }
        }

        StdOut.println("=== Verificação de Graus ===");
        for (int v = 0; v < G.V(); v++) {
            char letra = (char) ('a' + v); // Converte 0 em 'a', 1 em 'b'...
            StdOut.printf("Vértice %c (%d): Entrada=%d, Saída=%d\n", letra, v, indegree[v], outdegree[v]);
            if (indegree[v] != outdegree[v]) balanceado = false;
        }

        if (!balanceado) {
            StdOut.println("\nAVISO: O grafo não está balanceado! O método de Hierholzer pode falhar.");
        } else {
            StdOut.println("\nGrafo balanceado. Executando Hierholzer...");
        }

        Digraph simpleG = new Digraph(G.V());
        for (DirectedEdge e : G.edges()) {
            simpleG.addEdge(e.from(), e.to());
        }

        DirectedEulerianCycle euler = new DirectedEulerianCycle(simpleG);

        if (euler.hasEulerianCycle()) {
            StdOut.println("\n=== Circuito Euleriano Encontrado ===");
            for (int v : euler.cycle()) {
                StdOut.print((char)('a' + v) + " ");
            }

            double custoTotal = 0;
            for (DirectedEdge e : G.edges()) custoTotal += e.weight();
            StdOut.printf("\n\nCusto Total do Circuito: %.2f\n", custoTotal);
        } else {
            StdOut.println("\nErro: Circuito não encontrado. Verifique se o grafo é fortemente conexo.");
        }
    }
}