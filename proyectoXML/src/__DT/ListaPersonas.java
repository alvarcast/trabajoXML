package __DT;

import java.util.ArrayList;

public class ListaPersonas {

    private final ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
    private double presupuesto;

    public ListaPersonas (double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ArrayList<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void add(Persona p){
        listaPersonas.add(p);
    }

    public void rm(Persona p){
        listaPersonas.remove(p);
    }

    public Persona find(int n){
        Persona p = null;
        int c = 0;
        boolean found = false;

        while (c < listaPersonas.size() && !found){
            p = listaPersonas.get(c);
            if (p.getIdp() == n){
                found = true;
            } else {
                c++;
            }
        }
        
        return p;
    }
}
