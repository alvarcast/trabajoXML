package _3PresentationTier;

import _2BusinessTier.CRUD;
import _1DataTier.ListaPersonas;
import _2BusinessTier.GetStats;
import _2BusinessTier.MenuCtrl;

import java.io.IOException;

public class Menu {

    public static void menu() throws Exception {
        ListaPersonas listaPersonas;

        int opcion;

        System.out.println("¡Bienvenido al programa para hacer listas de regalos!");
        System.out.println(" ");

        try {
            listaPersonas = CRUD.read();
        } catch (IOException e) {
            System.out.println("Parece que es la primera vez que usas el programa. Vamos a añadir algunas cosas (Puedes poner 0 si no quieres poner nada de momento):");
            System.out.println(" ");
            listaPersonas = MenuCtrl.createNew();
            System.out.println("Lista de navidad creada correctamente");
            System.out.println(" ");
        }

        do {
            GetStats.checkPresupuesto(listaPersonas);

            opcion = Scan.scanInt("""
                ¿Qué quieres hacer?
                1. Añadir algo
                2. Editar algo
                3. Borrar algo
                4. Mostrar lista
                5. Ver estadísticas del xml
                6. Salir
                """, 1, 6
            );

            switch (opcion) {
                case 1 -> addMenu(listaPersonas);
                case 2 -> {}
                case 3 -> {}
                case 4 -> {}
                case 5 -> Printer.printList(listaPersonas);
                case 6 -> System.out.println("Saliendo del programa...");
            }
        } while (opcion != 6);
    }

    public static void addMenu(ListaPersonas listaPersonas){
        int opcion;

        opcion = Scan.scanInt("""
                ¿Qué quieres hacer?
                1. Añadir persona
                2. Añadir regalo
                3. Atrás
                """, 1, 3
        );

        switch (opcion) {
            case 1 -> MenuCtrl.createNewPersona(listaPersonas);
            case 2 -> MenuCtrl.addRegalo(listaPersonas);
        }
    }
}
