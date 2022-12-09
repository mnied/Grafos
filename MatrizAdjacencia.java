import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class MatrizAdjacencia {
    private int[][] grafo;
    private Map<String, Integer> vertices;

    public MatrizAdjacencia(int[][] grafo, String vertices[]) {
        this.grafo = grafo;
        this.vertices = new HashMap<>();
        
        for (int i = 0; i < vertices.length; i++) {
            this.vertices.put(vertices[i], i);
        }
    }

    public static class Vertex implements Comparable<Vertex> {
        private final int vertex;
        private final int cost;
      
        public Vertex(int vertex, int cost) {
          this.vertex = vertex;
          this.cost = cost;
        }
    
        public int getVertex() {
          return vertex;
        }
      
        public int getCost() {
          return cost;
        }
      
        @Override
        public int compareTo(Vertex other) {
          return Integer.compare(cost, other.cost);
        }
    }

    public List<String> obtemVerticeMaiorGrau() {
        List<String> vertice = new ArrayList<>();
        int maior = 0;
        
        for (String rotulo: vertices.keySet()) {
            int grau = obtemGrau(rotulo);

            if (maior == 0 || grau > maior) {
                vertice.clear();
                maior = grau;
                vertice.add(rotulo);
            } else if (grau == maior) {
                vertice.add(rotulo);
            }
        }
        
        return vertice;
    }

    public List<String> obtemVerticeMenorGrau(MatrizAdjacencia matriz) {
        List<String> vertice = new ArrayList<>();
        int menor = obtemGrau(matriz.obtemVerticeMaiorGrau().get(0));
        
        for (String rotulo: vertices.keySet()) {
            int grau = obtemGrau(rotulo);

            if (grau < menor) {
                vertice.clear();
                menor = grau;
                vertice.add(rotulo);
            }else if (grau == menor) {
                vertice.add(rotulo);
            }
        }
        
        return vertice;
    }

    public List<String> obtemVizinhos(String vi) {
        int i = vertices.get(vi);
        List<String> vizinhos = new ArrayList<>();

        for (String vj: vertices.keySet()) {
            int j = vertices.get(vj);
            
            if (grafo[i][j] > 0) {
                vizinhos.add(vj);
            }
        }

        return vizinhos;
    }

    public void buscaEmProfundidadeRecursiva() {
        String vertice = vertices.keySet().iterator().next();
        List<String> visitados = new ArrayList<>();
        
        System.out.print("Busca em profundidade recursiva: [ ");
        
        this.buscaEmProfundidade(vertice, visitados);

        System.out.println("]");
    }

    public void buscaEmProfundidade(String vertice, List<String> visitados) {
        if (!visitados.contains(vertice)) {
            System.out.print(vertice + " ");
            visitados.add(vertice);
            
            List<String> vizinhos = obtemVizinhos(vertice);
            
            for (String vizinho : vizinhos) {
                buscaEmProfundidade(vizinho, visitados);
            }
        }
    }

    public void buscaEmProfundidadeIterativa() {
        String vertice = vertices.keySet().iterator().next();
        Stack<String> naoVisitados = new Stack<>();
        List<String> visitados = new ArrayList<>();

        naoVisitados.push(vertice);

        System.out.print("Busca em profundidade iterativa: [ ");
        
        while (!naoVisitados.isEmpty()) {
            String vi = naoVisitados.pop();

            if (!visitados.contains(vi)) {
                visitados.add(vi);
                System.out.print(vi + " ");

                List<String> vizinhos = obtemVizinhos(vi);
                Collections.reverse(vizinhos);
                
                vizinhos.forEach(vj -> naoVisitados.push(vj));
            }
        }

        System.out.println("]");
    }

    public int obtemGrau(String vertice) {
        int i = vertices.get(vertice);
        int grau = 0;
        
        for (int j = 0; j < grafo.length; j++) {
            if (grafo[i][j] > 0 || grafo[j][i] > 0) {
                grau++;
            }
        }

        return grau;
    }

    public static void menorCaminho(int[][] grafo, int estado1, int estado2) {
        Queue<Integer> queue = new LinkedList<>();
        int[] caminho = new int[grafo.length];
        boolean[] visited = new boolean[grafo.length];
        visited[estado1] = true;
        
        queue.add(estado1);        
        while (!queue.isEmpty()) {
            int state = queue.remove();
            
            for (int i = 0; i < grafo[state].length; i++) {
                if (grafo[state][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                    caminho[i] = state;
                    
                    if (i == estado2) {
                        break;
                    }
                }
            }
        }

        System.out.print("Menor caminho entre dois estados: ");
        int state = estado2;
        while (state != estado1) {
            System.out.print(state + " -> ");
            state = caminho[state];
        }
        System.out.println(estado1);
    }

    public static void todosOsCaminhos(int[][] grafo, int estado1, int estado2) {
        Stack<Integer> stack = new Stack<>();
        int[] path = new int[grafo.length];
        boolean[] visited = new boolean[grafo.length];
        visited[estado1] = true;
        
        stack.push(estado1);
        while (!stack.isEmpty()) {
            int state = stack.pop();
            
            for (int i = 0; i < grafo[state].length; i++) {
                if (grafo[state][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    stack.push(i);
                    path[i] = state;
                    
                    if (i == estado2) {
                        System.out.print("Caminho possível: ");
                        state = estado2;
                        while (state != estado1) {
                            System.out.print(state + " -> ");
                            state = path[state];
                        }
                        System.out.println(estado1);
                        
                        stack.pop();
                        visited[i] = false;
                    }
                }
            }
        }
    }

    public static void main(String args[]) {
        int grafo[][] = {
        //   AC AM RR RO PA AP TO MA PI CE RN PE PB AL SE BA MT GO MS DF MG ES RJ SP PR SC RS  
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//AC
            { 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//AM
            { 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//RR
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//RO
            { 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//PA
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//AP
            { 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},//TO
            { 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//MA
            { 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//PI
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//CE
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//RN
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//PE
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//PB
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//AL
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//SE
            { 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},//BA
            { 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},//MT
            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0},//GO
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0},//MS
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},//DF
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0},//MG
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},//ES
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0},//RJ
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0},//SP
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0},//PR
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},//SC
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0} //RS
        };

        String vertices[] = {"AC", "AM", "RR", "RO", "PA", "AP", "TO",
                             "MA", "PI", "CE", "RN", "PE", "PB",
                             "AL", "SE", "BA", "MT", "GO", "MS",
                             "DF", "MG", "ES", "RJ", "SP", "PR",
                             "SC", "RS"};

        MatrizAdjacencia matriz = new MatrizAdjacencia(grafo, vertices);
        
        List<String> maior = matriz.obtemVerticeMaiorGrau();
        int grau = matriz.obtemGrau(maior.get(0));
        
        if (maior.size() > 1) {
            System.out.println("Estados com mais vizinhos: " + maior.toString()  + ", com " + grau + " vizinho(s) cada.");
        }else{
            System.out.println("Estado com mais vizinhos: " + maior.toString()  + ", com " + grau + " vizinho(s).");
        }

        List<String> menor = matriz.obtemVerticeMenorGrau(matriz);
        grau = matriz.obtemGrau(menor.get(0));
        
        if (menor.size() > 1) {
            System.out.println("------------------------------------------------");
            System.out.println("Estados com menos vizinhos: " + menor.toString()  + ", com " + grau + " vizinho(s) cada.");
        }else{
            System.out.println("------------------------------------------------");
            System.out.println("Estado com menos vizinhos: " + menor.toString()  + ", com " + grau + " vizinho(s).");
        }

        System.out.println("------------------------------------------------");
        menorCaminho(grafo, 3, 22);

        System.out.println("------------------------------------------------");
        todosOsCaminhos(grafo, 2, 17);
    }
}