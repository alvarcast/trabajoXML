package _1DataTier;

// Clase persona para los nodos persona
public class Persona {

    private final int idp;
    private String alias;
    private double presupuesto;
    private ListaRegalos listaRegalos;

    public Persona(int idp, String alias, double presupuesto, ListaRegalos listaRegalos) {
        this.idp = idp;
        this.alias = alias;
        this.presupuesto = presupuesto;
        this.listaRegalos = listaRegalos;
    }

    public int getIdp() {
        return idp;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ListaRegalos getListaRegalos() {
        return listaRegalos;
    }

    public void setListaRegalos(ListaRegalos listaRegalos) {
        this.listaRegalos = listaRegalos;
    }
}
