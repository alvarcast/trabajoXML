package __DT;

import java.util.ArrayList;

public class ListaRegalos {

    private final ArrayList<Regalo> listaRegalos = new ArrayList<Regalo>();

    public ListaRegalos() {
    }

    public ArrayList<Regalo> getListaRegalos() {
        return listaRegalos;
    }

    public void add(Regalo r) {
        listaRegalos.add(r);
    }

    public void rm(Regalo r) {
        listaRegalos.remove(r);
    }

    public Regalo find(int n) {
        Regalo r = null;
        int c = 0;
        boolean found = false;

        while (c < listaRegalos.size() && !found) {
            r = listaRegalos.get(c);
            if (r.getIdr() == n) {
                found = true;
            } else {
                c++;
            }
        }
        return r;
    }
}
