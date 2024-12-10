package _2BusinessTier;

import _3PresentationTier.Menu;

import java.io.FileNotFoundException;

public class Controller {

    public static void start(){
        try {
            Menu.menu();
        } catch (FileNotFoundException e) {
            System.err.println("El fichero no se ha encontrado o tiene un formato incorrecto");
        }
    }
}
