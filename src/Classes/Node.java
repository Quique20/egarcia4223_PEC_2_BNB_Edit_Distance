package Classes;

import java.util.List;

public class Node{
    // Variables básicas
    private int i; // Posición actual en la cadena X
    private int j; // Posición actual en la cadena Y
    private String cadenaActual; // cadena actual tras las transformaciones
    private int longitud; // longitud actual

    // Variables de coste (por definir ecuación óptima de la cota)
    private int costeT; // Coste total actual
    private int estOpt; // Coste de estimación optimista

    // Historial para trazas
    private List<String> historialTransf;

    // Constructor base con todo
    public Node(int i, int j, String cadenaActual, int longitud, int costeT, int estOpt, List<String> historialTransf) {
        this.i = i;
        this.j = j;
        this.cadenaActual = cadenaActual;
        this.longitud = longitud;
        this.costeT = costeT;
        this.estOpt = estOpt;
        this.historialTransf = historialTransf;
    }

    public int compareTo(Node otro){
        if(this.estOpt != otro.estOpt){
            return Integer.compare(this.estOpt, otro.estOpt);
        }
        // En el caso que ambas estimaciones sean iguales
        // se devuelve el que tenga un menor coste total acumulado
        return Integer.compare(this.costeT, otro.costeT);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public String getCadenaActual() {
        return cadenaActual;
    }

    public void setCadenaActual(String cadenaActual) {
        this.cadenaActual = cadenaActual;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public int getCosteT() {
        return costeT;
    }

    public void setCosteT(int costeT) {
        this.costeT = costeT;
    }

    public int getEstOpt() {
        return estOpt;
    }

    public void setEstOpt(int estOpt) {
        this.estOpt = estOpt;
    }

    public List<String> getHistorialTransf() {
        return historialTransf;
    }

    public void setHistorialTransf(List<String> historialTransf) {
        this.historialTransf = historialTransf;
    }

}