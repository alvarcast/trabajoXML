package _2BusinessTier;

import _3PresentationTier.Menu;

import java.io.FileNotFoundException;

public class Controller {

    // Captura todos los errores que se hayan podido escapar
    public static void start(){
        try {
            Menu.menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
