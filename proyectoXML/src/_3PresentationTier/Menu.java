package _3PresentationTier;

import _2BusinessTier.CRUD;
import _1DataTier.ListaPersonas;

import java.io.FileNotFoundException;

public class Menu {

    public static void menu() throws FileNotFoundException {
        ListaPersonas lp = CRUD.read();
    }
}
