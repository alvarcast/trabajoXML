package _3PresentationTier;

import _2BusinessTier.CRUD;
import _1DataTier.ListaPersonas;
import _2BusinessTier.MenuCtrl;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Menu {

    public static void menu() throws Exception {
        System.out.println("¡Bienvenido al programa para hacer listas de regalos!");
        System.out.println(" ");

        ListaPersonas lp;

        try {
            lp = CRUD.read();
        } catch (IOException e) {
            System.out.println("Parece que es la primera vez que usas el programa. Vamos a añadir algunas cosas (Puedes poner 0 si no quieres poner nada de momento):");
            System.out.println(" ");
            lp = MenuCtrl.createNew();
        }
    }
}
