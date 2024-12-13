package _1DataTier;

import java.util.ArrayList;

// Clase para guardar los nodos regalo de cada persona en una lista
public class ListaRegalos {

    private final ArrayList<Regalo> listaRegalos = new ArrayList<Regalo>();

    public ListaRegalos() {}

    public ArrayList<Regalo> getListaRegalos() {
        return listaRegalos;
    }

    public void add(Regalo r) {
        listaRegalos.add(r);
    }

}
