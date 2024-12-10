package _2BusinessTier;

import _3PresentationTier.Menu;

import java.io.FileNotFoundException;

public class Controller {

    public static void start(){
        try {
            Menu.menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
