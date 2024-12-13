package _1DataTier;

// Clase regalo para los nodos regalo
public class Regalo {

    private int idr;
    private double precio;
    private String item;

    public Regalo(int idr, double precio, String item) {
        this.idr = idr;
        this.precio = precio;
        this.item = item;
    }

    public int getIdr() {
        return idr;
    }

    public void setIdr(int idr) {
        this.idr = idr;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
