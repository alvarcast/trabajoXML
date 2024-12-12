package _1DataTier;

import java.util.ArrayList;

public class ListaPersonas {

    private final ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
    private double presupuesto;

    public ListaPersonas () {}

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

}
