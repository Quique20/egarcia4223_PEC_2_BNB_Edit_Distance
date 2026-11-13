import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import Classes.Node;

public class App {

    private static int _cb = 1; // coste borrado
    private static int _ci = 1; // coste inserción
    private static int _cs = 1; // coste sustitución
    private static int _cc = 1; // coste intercambio


    public static void main(String[] args) throws Exception {
        String X = "sacos";
        String Y = "caso";

        Node[] pec = resolverDistanciaEdicion(X, Y, _cb, _ci, _cs,_cc);

        System.out.println(pec[0].getHistorialTransf());
    }

    public static Node[] resolverDistanciaEdicion(String X, String Y, int cb, int ci, int cs, int cc){
        int n = X.length();
        int m = Y.length();

        // Montículo de mínimos
        PriorityQueue<Node> monticulo = new PriorityQueue<Node>();

        // Nodo inicial
        int estOptRaiz = Node.calcularEstOpt(n, m, 0, cb, ci);
        Node raiz = new Node(0,0,0,estOptRaiz,new ArrayList<String>());
        monticulo.add(raiz);


        // Cota global
        int cotaGlobal = (n * cb) + (m * ci); // Borra todo en X e inserta en Y
        Node[] mejorSolucion = new Node[1];


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
                cotaGlobal = ProcesarHijo(nodoHijo, n,m,monticulo,cotaGlobal,mejorSolucion);
            
            // Caso B, hay que comprobar las soluciones
            }else{
                // Borrar
                if(i < n){
                    Node hijoBorrado = CrearHijo(actual, i + 1, j, actual.getCosteT() + cb, "Borrado", n, m, cb, ci);
                    cotaGlobal = ProcesarHijo(hijoBorrado, n , m , monticulo, cotaGlobal, mejorSolucion);
                }
                // Insertar
                if (j < m){
                    Node hijoInsertado = CrearHijo(actual, i, j + 1, actual.getCosteT() + ci, "Insertado", n, m, cb, ci);
                    cotaGlobal = ProcesarHijo(hijoInsertado, n, m, monticulo, cotaGlobal, mejorSolucion);
                }
                // Sustitución
                if (i < n && j < m && X.charAt(i) != Y.charAt(j)){
                    Node hijoSust = CrearHijo(actual, i + 1, j + 1, actual.getCosteT() + cs, "Sustitución", n, m, cb, ci);
                    cotaGlobal = ProcesarHijo(hijoSust, n, m, monticulo, cotaGlobal, mejorSolucion);
                }
                // Intercambio TODO
                // Hay que retocar el bucle ya que los límites no siempre son buenos (ahora mismo funciona si se quiere hacer un salto de las adyacentes y las siguientes)
                /*
                for(int count = 0; count < Integer.max(n, m); count++){
                    if ((i + (count + 1) < n && j + (count + 1) < m && X.charAt(i) == Y.charAt(j + (count + 1)) && X.charAt(i + (count + 1)) == Y.charAt(j))){
                        Node hijoIntercambio = CrearHijo(actual, i + (count + 2), j + (count + 2), actual.getCosteT() + cc, "Intercambio "+count, n, m, cb, ci);
                        cotaGlobal = ProcesarHijo(hijoIntercambio, n, m, monticulo, cotaGlobal, mejorSolucion);
                    }
                }
                
                if ((i + 1 < n && j + 1 < m && X.charAt(i) == Y.charAt(j + 1) && X.charAt(i + 1) == Y.charAt(j))){
                    Node hijoIntercambio = CrearHijo(actual, i + 2, j + 2, actual.getCosteT() + cc, "Intercambio", n, m, cb, ci);
                    cotaGlobal = ProcesarHijo(hijoIntercambio, n, m, monticulo, cotaGlobal, mejorSolucion);
                } 
                if(i + 2 < n && j + 2 < m && X.charAt(i) == Y.charAt(j + 2) && X.charAt(i + 2) == Y.charAt(j)){
                    Node hijoIntercambio = CrearHijo(actual, i + 3, j + 3, actual.getCosteT(), "Intercambio +3", n, m, cb, ci);
                    cotaGlobal = ProcesarHijo(hijoIntercambio, n, m, monticulo, cotaGlobal, mejorSolucion);
                }*/


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

        return new Node(nuevoI, nuevoJ, nuevoCoste, estOpt, nuevoHistorial);

    }

    private static int ProcesarHijo(Node hijo, int n, int m, PriorityQueue<Node> monticulo, int cotaGlobal, Node[] mejorSolucion){
        // Si ha llegado al final de ambos
        if(hijo.getI() == n && hijo.getJ() == m){
            // Si el coste es menor que el actual, lo actualiza
            if(hijo.getCosteT() < cotaGlobal){
                cotaGlobal = hijo.getCosteT();
                mejorSolucion[0] = hijo;
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