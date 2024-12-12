package _3PresentationTier;

import _2BusinessTier.CRUD;
import _1DataTier.ListaPersonas;
import _2BusinessTier.GetStats;
import _2BusinessTier.MenuCtrl;

import java.io.IOException;

public class Menu {

    public static void menu() {
        ListaPersonas listaPersonas;

        int opcion;

        String warn;

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

        if (listaPersonas == null) {
            listaPersonas = new ListaPersonas();
        }

        do {
            if (GetStats.checkPresupuesto(listaPersonas, true)) {
                warn = "!!!";
            } else {
                warn = "";
            }

            System.out.printf("""
                ¿Qué quieres hacer?
                1. Añadir algo
                2. Editar algo
                3. Borrar algo
                4. Mostrar lista
                5. Ver estadísticas del xml
                6. Avisos %s
                7. Salir
                """, warn
            );

            opcion = Scan.scanInt("", 1, 7);

            switch (opcion) {
                case 1 -> addMenu(listaPersonas);
                case 2 -> editMenu(listaPersonas);
                case 3 -> deleteMenu(listaPersonas);
                case 4 -> Printer.printList(listaPersonas);
                case 5 -> GetStats.nodeStats(listaPersonas);
                case 6 -> GetStats.checkPresupuesto(listaPersonas, false);
                case 7 -> System.out.println("Saliendo del programa...");
            }
        } while (opcion != 7);
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

    public static void editMenu(ListaPersonas listaPersonas){
        int opcion;

        opcion = Scan.scanInt("""
                ¿Qué quieres hacer?
                1. Editar persona
                2. Editar regalo
                3. Editar presupuesto total
                4. Atrás
                """, 1, 4
        );

        switch (opcion) {
            case 1 -> MenuCtrl.editPersona(listaPersonas);
            case 2 -> MenuCtrl.editRegalo(listaPersonas);
            case 3 -> MenuCtrl.editPresupuestoGeneral(listaPersonas);
        }
    }

    public static void deleteMenu(ListaPersonas listaPersonas){
        int opcion;

        opcion = Scan.scanInt("""
                ¿Qué quieres hacer?
                1. Borrar persona
                2. Borrar regalo
                3. Atrás
                """, 1, 3
        );

        switch (opcion) {
            case 1 -> MenuCtrl.deletePersona(listaPersonas);
            case 2 -> MenuCtrl.deleteRegalo(listaPersonas);
        }
    }
}
