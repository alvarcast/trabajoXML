package _1DataTier;

import java.util.ArrayList;

// Clase para guardar los nodos persona en una lista
public class ListaPersonas {

    private final ArrayList<Persona> listaPersonas = new ArrayList<Persona>();

    // Incluye el presupuesto total ya que es un atributo de la lista de navidad (lista personas)
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
