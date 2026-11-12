import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import Classes.Node;

public class App {

    private int _cb; // coste borrado
    private int _ci; // coste inserción
    private int _cs; // coste sustitución
    private int _cc; // coste intercambio


    public static void main(String[] args) throws Exception {
        
    }

    public static Node resolverDistanciaEdicion(String X, String Y, int cb, int ci, int cs, int cc){
        int n = X.length();
        int m = Y.length();

        // Montículo de mínimos
        PriorityQueue<Node> monticulo = new PriorityQueue<Node>();

        // Nodo inicial
        int estOptRaiz = Node.calcularEstOpt(n, m, 0, cb, ci);
        Node raiz = new Node(0,0,0,estOptRaiz,new ArrayList<String>());
        monticulo.add(raiz);


        // Cota global
        int cotaGlobal = 999999999; // ver si dejar esto o algo dinámico con los costes
        Node mejorSolucion = null;


        // Bucle
        while (!monticulo.isEmpty() && monticulo.peek().getEstOpt() <= cotaGlobal){
            // Extraer la cima
            Node actual = monticulo.poll();

            int i = actual.getI();
            int j = actual.getJ();

            //#region Casos

            // Caso A, son iguales no hay que hacer más
            if(i < n && j < m && X.charAt(i) == Y.charAt(j)){
                Node nodoHijo = CrearHijo(actual,i + 1, j + 1, actual.getCosteT(),"Coincidencia",n,m,cb,ci);
                ProcesarHijo(nodoHijo, n,m,monticulo,cotaGlobal,mejorSolucion);
            
            // Caso B, hay que comprobar las soluciones
            }else{

            }

            //#endregion
        }


        return mejorSolucion;


    }

    private static Node CrearHijo(Node padre, int nuevoI, int nuevoJ, int nuevoCoste, String descOp, int n, int m, int cb, int ci){
        List<String> nuevoHistorial = new ArrayList<>(padre.getHistorialTransf());
        nuevoHistorial.add(descOp);

        int lonX = n - nuevoI;
        int lonY = m - nuevoJ;

        int estOpt = Node.calcularEstOpt(lonX, lonY, nuevoCoste, cb, ci);

        return new Node(ci, nuevoJ, nuevoCoste, estOpt, nuevoHistorial);

    }

    private static int ProcesarHijo(Node hijo, int n, int m, PriorityQueue<Node> monticulo, int cotaGlobal, Node mejorSolucion){
        // Si ha llegado al final de ambos
        if(hijo.getI() == n && hijo.getJ() == m){
            // Si el coste es menor que el actual, lo actualiza
            if(hijo.getCosteT() < cotaGlobal){
                cotaGlobal = hijo.getCosteT();
                mejorSolucion = hijo;
            }
        // Si es solución parcial y su estimación es menor o igual se añade
        }else if(hijo.getEstOpt() <= cotaGlobal){
            monticulo.add(hijo);
        }

        return  cotaGlobal;
    }

}

/*
    n = longitud x
    m = longitud y
    Condiciones de ramificación

    1.Match (i < n && j < m && X[i] == Y[j])
        nuevoI = i + 1;
        nuevoJ = j + 1;
        nuevoCosteT = costeT;
    
    2.Borrado (i < n) Quedan caracteres en X
        nuevoI = i + 1;
        nuevoJ = j;
        nuevoCosteT = costeT + cb
    
    3. Inserción (j < m) Quedan caracteres en Y
        nuevoI = i;
        nuevoJ = j + 1;
        nuevoCosteT = costeT + ci;
    
    4. Sustitución (i < n && j < m && X[i] != Y[j])
        nuevoI = i + 1;
        nuevoJ = j + 1;
        nuevoCosteT = costeT + cs;

    5. Intercambio ((i + 1 < n && j + 1 < m && X[i] == Y[j + 1] && X[i + i] == Y[j]) [Al menos dos caracteres restantes y los adyacentes iguales]
        nuevoI = i + 2;
        nuevoJ = j + 2;
        nuevoCosteT = costeT + cc;

    Poda
    estOptHijo = calcularEstOpt(n - nuevoI, m - nuevoJ, nuevoCosteT,cb,ci)
    Si estOptHijo <= cotaGlobal se inserta en el montículo de mínimos

*/