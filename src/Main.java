import java.io.File;

public class Main {
    public static void main(String[] args) {
        String[] caminhosPossiveis = {
                "Dados/LA_Eurelizada.txt",
                "src/Dados/LA_Eurelizada.txt",
                "./Dados/LA_Eurelizada.txt"
        };

        String caminhoFinal = "";
        for (String p : caminhosPossiveis) {
            File f = new File(p);
            if (f.exists()) {
                caminhoFinal = p;
                break;
            }
        }

        if (caminhoFinal.isEmpty()) {
            System.out.println("ERRO: Arquivo nao encontrado!");
            return;
        }

        // Tenta ler o arquivo
        try {
            In in = new In(caminhoFinal);
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

            System.out.println("=== GRAFO CARREGADO ===");
            System.out.println(G);

            executarCiclo(G);
        } catch (Exception e) {
            System.out.println("Erro na leitura do grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void executarCiclo(EdgeWeightedDigraph G) {
        Digraph eulerG = new Digraph(G.V());
        for (DirectedEdge e : G.edges()) {
            eulerG.addEdge(e.from(), e.to());
        }

        DirectedEulerianCycle euler = new DirectedEulerianCycle(eulerG);

        if (euler.hasEulerianCycle()) {
            System.out.println("\nCiclo encontrado:");
            for (int v : euler.cycle()) {
                System.out.print(v + " ");
            }
        } else {
            System.out.println("\nO grafo nao e Euleriano (Graus desequilibrados).");
        }
    }
}