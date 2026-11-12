package Classes;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{
    // Variables básicas
    private int i; // Posición actual en la cadena X
    private int j; // Posición actual en la cadena Y

    // Variables de coste 
    private int costeT; // Coste total actual
    private int estOpt; // Coste de estimación optimista

    // Historial para trazas
    private List<String> historialTransf;

    // Constructor base con todo
    public Node(int i, int j,  int costeT, int estOpt, List<String> historialTransf) {
        this.i = i;
        this.j = j;
        this.costeT = costeT;
        this.estOpt = estOpt;
        this.historialTransf = new ArrayList<String>();
    }

    @Override 
    public int compareTo(Node otro){
        if(this.estOpt != otro.estOpt){
            return Integer.compare(this.estOpt, otro.estOpt);
        }
        // En el caso que ambas estimaciones sean iguales
        // se devuelve el que tenga un menor coste total acumulado
        return Integer.compare(this.costeT, otro.costeT);
    }

    // Método para calcular la estimación optimista 
    public static int calcularEstOpt(int longX, int longY, int costeT, int cb, int ci){
        int estimacion = 0;
        // Si la cadena X es menor deberemos insertar si o si aunque todas coincidan
        if(longX < longY){
            estimacion = costeT + ci * (longY - longX);
        // Si la cadena X es mayor o igual deberemos borrar (si es igual el borrado dará 0 por lo que el coste es irrelevante)
        }else{
            estimacion = costeT + cb * (longX - longY);
        }

        return estimacion;
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

    public void addHistorialTransf(String operacion){
        this.historialTransf.add(operacion);
    }

    public void removeHistorialTransf(String opeString){
        for (String actual : historialTransf) {
            if (opeString.equals(opeString)) {
                this.historialTransf.remove(actual);
            }
        }
    }

}