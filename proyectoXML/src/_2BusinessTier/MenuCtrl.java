package _2BusinessTier;

import _1DataTier.ListaPersonas;
import _1DataTier.ListaRegalos;
import _1DataTier.Persona;
import _1DataTier.Regalo;
import _3PresentationTier.Scan;

public class MenuCtrl {

    public static ListaPersonas createNew() {

        ListaPersonas listaPersonas;
        ListaRegalos listaRegalos;

        Persona persona;
        Regalo regalo;

        double presupuesto_total;

        int idp;
        String alias;
        double presupuesto;

        int idr;
        double precio;
        String item;

        int countP;
        int countR;

        presupuesto_total = Scan.scanDouble("Presupuesto total de todos los regalos: ");

        listaPersonas = new ListaPersonas(presupuesto_total);

        countP = Scan.scanInt("¿Cuántas personas quieres agregar?");

        for (int i = 0; i < countP; i++) {
            System.out.println("Persona " + (i + 1) + ":");
            System.out.println(" ");

            idp = i + 1;
            alias = Scan.scanText("Alias: ");
            presupuesto = Scan.scanDouble("Presupuesto para " + alias + ":");

            countR = Scan.scanInt("¿Cuántos regalos quieres añadir a " + alias + "?");

            listaRegalos = new ListaRegalos();

            for (int j = 0; j < countR; j++) {
                System.out.println("Regalo " + (j + 1) + ":");
                System.out.println(" ");

                idr = j + 1;
                item = Scan.scanText("Item: ");
                precio = Scan.scanDouble("Precio: ");

                regalo = new Regalo(idr, precio, item);
                listaRegalos.add(regalo);
            }

            persona = new Persona(idp, alias, presupuesto, listaRegalos);
            listaPersonas.add(persona);
        }

        CRUD.newChristmasList(listaPersonas);

        return listaPersonas;
    }

    public static void createNewPersona(ListaPersonas listaPersonas){
        ListaRegalos listaRegalos;

        Persona persona;

        int idp;
        String alias;
        double presupuesto;

        int countP;

        int opcion;

        countP = Scan.scanInt("¿Cuántas personas quieres agregar?");

        for (int i = 0; i < countP; i++) {
            System.out.println("Persona " + (i + 1) + ":");
            System.out.println(" ");

            idp = i + 1;
            alias = Scan.scanText("Alias: ");
            presupuesto = Scan.scanDouble("Presupuesto para " + alias + ":");

            opcion = Scan.scanInt(String.format("""
                    ¿Quiere añadir algún regalo a %s?
                    1. Sí
                    2. No
                    """, alias), 1, 2
            );

            if (opcion == 1) {
                listaRegalos = createNewRegalo(alias);
            } else {
                System.out.printf("No se añadiran regalos a %s", alias);
                listaRegalos = new ListaRegalos();
            }

            persona = new Persona(idp, alias, presupuesto, listaRegalos);
            listaPersonas.add(persona);

            System.out.println(" ");
        }
    }

    public static ListaRegalos createNewRegalo(String alias){
        return null;
    }
}
