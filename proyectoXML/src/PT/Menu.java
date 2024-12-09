package PT;

import _BT.CRUD;
import __DT.ListaPersonas;

import java.io.FileNotFoundException;

public class Menu {

    public static void menu() throws FileNotFoundException {
        ListaPersonas lp = CRUD.read();
    }
}
