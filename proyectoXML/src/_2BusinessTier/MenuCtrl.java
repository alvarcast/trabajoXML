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
            alias = Scan.scanText("Alias: ", 20);
            presupuesto = Scan.scanDouble("Presupuesto para " + alias + ":");

            countR = Scan.scanInt("¿Cuántos regalos quieres añadir a " + alias + "?");

            listaRegalos = new ListaRegalos();

            for (int j = 0; j < countR; j++) {
                System.out.println("Regalo " + (j + 1) + ":");
                System.out.println(" ");

                idr = j + 1;
                item = Scan.scanText("Item: ", 20);
                precio = Scan.scanDouble("Precio: ");

                regalo = new Regalo(idr, precio, item);
                listaRegalos.add(regalo);
            }

            persona = new Persona(idp, alias, presupuesto, listaRegalos);
            listaPersonas.add(persona);
        }

        CRUD.write(listaPersonas);

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
            System.out.println("=== Nueva persona " + (i + 1) + " ===");

            idp = listaPersonas.getListaPersonas().getLast().getIdp() + 1;
            alias = Scan.scanText("Alias: ", 20);
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
                System.out.println(" ");
            }

            persona = new Persona(idp, alias, presupuesto, listaRegalos);
            listaPersonas.add(persona);
        }

        CRUD.write(listaPersonas);
    }

    public static ListaRegalos createNewRegalo(String alias){
        ListaRegalos listaRegalos = new ListaRegalos();

        Regalo regalo;

        int idr;
        double precio;
        String item;

        int countR;

        countR = Scan.scanInt("¿Cuántas regalos quieres agregar a " + alias);

        for (int i = 0; i < countR; i++){
            System.out.println("=== Nuevo regalo " + (i + 1) + " ===");

            idr = i + 1;
            item = Scan.scanText("Item: ", 20);
            precio = Scan.scanDouble("Precio: ");

            regalo = new Regalo(idr, precio, item);

            listaRegalos.add(regalo);
        }

        System.out.println(" ");

        return listaRegalos;
    }

    public static void addRegalo (ListaPersonas listaPersonas) {
        ListaRegalos listaRegalos;

        int pid;

        for (int i = 0; i < listaPersonas.getListaPersonas().size(); i++) {
            System.out.println((i + 1) + ". " + listaPersonas.getListaPersonas().get(i).getAlias());
        }

        System.out.println(" ");
        System.out.println("¿A qué persona quieres añadir un regalo?");

        pid = Scan.scanInt("", 1, listaPersonas.getListaPersonas().size()) - 1;

        listaRegalos = createNewRegalo(listaPersonas.getListaPersonas().get(pid).getAlias());

        for (Regalo regalo : listaRegalos.getListaRegalos()){
            regalo.setIdr(listaPersonas.getListaPersonas().get(pid).getListaRegalos().getListaRegalos().getLast().getIdr() + 1);
            listaPersonas.getListaPersonas().get(pid).getListaRegalos().add(regalo);
        }

        CRUD.write(listaPersonas);
    }

    public static void editPersona(ListaPersonas listaPersonas){
        int pid;
        int opcion;

        String newVal;

        for (int i = 0; i < listaPersonas.getListaPersonas().size(); i++) {
            System.out.println((i + 1) + ". " + listaPersonas.getListaPersonas().get(i).getAlias());
            System.out.printf("Presupuesto: %s \n", listaPersonas.getListaPersonas().get(i).getPresupuesto());
            System.out.println(" ");
        }

        System.out.println("¿Qué persona quieres editar?");

        pid = Scan.scanInt("", 1, listaPersonas.getListaPersonas().size()) - 1;

        System.out.printf("¿Qué quieres editar de %s? \n", listaPersonas.getListaPersonas().get(pid).getAlias());
        opcion = Scan.scanInt("""
                1. Alias
                2. Presupuesto
                """, 1, 2
        );

        switch (opcion) {
            case 1 -> {
                newVal = Scan.scanText("Nuevo alias: ", 20);
                listaPersonas.getListaPersonas().get(pid).setAlias(newVal);
            }
            case 2 -> {
                newVal = String.valueOf(Scan.scanDouble("Nuevo presupuesto: "));
                listaPersonas.getListaPersonas().get(pid).setPresupuesto(Double.parseDouble(newVal));
            }
        }

        CRUD.write(listaPersonas);
    }

    public static void editRegalo(ListaPersonas listaPersonas){
        int pid;
        int rid;

        int opcion;
        int contador;

        String newVal;

        for (int i = 0; i < listaPersonas.getListaPersonas().size(); i++) {
            contador = 1;
            System.out.println("=== " + (i + 1) + ". " + listaPersonas.getListaPersonas().get(i).getAlias() + " ===");
            for (Regalo regalo : listaPersonas.getListaPersonas().get(i).getListaRegalos().getListaRegalos()) {
                System.out.printf(contador + "." +" %s: %.2f€ \n", regalo.getItem(), regalo.getPrecio());
                contador++;
            }
            System.out.println(" ");
        }

        System.out.println("¿De qué persona quieres editar sus regalos ?");

        pid = Scan.scanInt("", 1, listaPersonas.getListaPersonas().size()) - 1;

        System.out.printf("¿Qué regalo quieres editar de %s? \n", listaPersonas.getListaPersonas().get(pid).getAlias());
        rid = Scan.scanInt("",
                1, listaPersonas.getListaPersonas().get(pid).getListaRegalos().getListaRegalos().size()
        ) - 1;

        System.out.printf("¿Y qué quieres editar de '%s'? \n",
                listaPersonas.getListaPersonas().get(pid).getListaRegalos().getListaRegalos().get(rid).getItem()
        );

        opcion = Scan.scanInt("""
                1. Cambiar nombre del item
                2. Precio
                """, 1, 2
        );

        switch (opcion) {
            case 1 -> {
                newVal = Scan.scanText("Nuevo nombre del item: ", 20);
                listaPersonas.getListaPersonas().get(pid).getListaRegalos().getListaRegalos().get(rid).setItem(newVal);
            }
            case 2 -> {
                newVal = String.valueOf(Scan.scanDouble("Nuevo precio: "));
                listaPersonas.getListaPersonas().get(pid).getListaRegalos().getListaRegalos().get(rid).setPrecio(Double.parseDouble(newVal));
            }
        }

        CRUD.write(listaPersonas);
    }

    public static void editPresupuestoGeneral (ListaPersonas listaPersonas) {
        double newVal;

        newVal = Scan.scanDouble("Nuevo presupuesto general: ");

        listaPersonas.setPresupuesto(newVal);

        CRUD.write(listaPersonas);
    }

    public static void deletePersona(ListaPersonas listaPersonas){
        int pid;
        int opcion;

        for (int i = 0; i < listaPersonas.getListaPersonas().size(); i++) {
            System.out.println((i + 1) + ". " + listaPersonas.getListaPersonas().get(i).getAlias());
            System.out.printf("Presupuesto: %s \n", listaPersonas.getListaPersonas().get(i).getPresupuesto());
        }

        System.out.println("¿Qué persona quieres eliminar?");

        pid = Scan.scanInt("", 1, listaPersonas.getListaPersonas().size()) - 1;

        System.out.printf("¿Estas seguro de que quieres eliminar a %s? \n", listaPersonas.getListaPersonas().get(pid).getAlias());
        opcion = Scan.scanInt("""
                1. Sí
                2. Cancelar
                """, 1, 2
        );

        if (opcion == 1) {
            listaPersonas.getListaPersonas().remove(pid);
            CRUD.write(listaPersonas);
        }
    }

    public static void deleteRegalo (ListaPersonas listaPersonas) {
        int pid;
        int rid;

        int opcion;

        for (int i = 0; i < listaPersonas.getListaPersonas().size(); i++) {
            System.out.println((i + 1) + ". " + listaPersonas.getListaPersonas().get(i).getAlias());
            for (Regalo regalo : listaPersonas.getListaPersonas().get(i).getListaRegalos().getListaRegalos()) {
                System.out.printf("· %s: %.2f€ \n", regalo.getItem(), regalo.getPrecio());
            }
        }

        System.out.println("¿De qué persona quieres eliminar un regalos ?");

        pid = Scan.scanInt("", 1, listaPersonas.getListaPersonas().size()) - 1;

        System.out.printf("¿Qué regalo quieres eliminar de %s? \n", listaPersonas.getListaPersonas().get(pid).getAlias());
        rid = Scan.scanInt("",
                1, listaPersonas.getListaPersonas().get(pid).getListaRegalos().getListaRegalos().size()
        ) - 1;

        System.out.printf("¿Estas seguro de que quieres eliminar el regalo '%s' de %s \n?",
                listaPersonas.getListaPersonas().get(pid).getListaRegalos().getListaRegalos().get(rid).getItem(),
                listaPersonas.getListaPersonas().get(pid).getAlias()
        );

        opcion = Scan.scanInt("""
                1. Sí
                2. Cancelar
                """, 1, 2
        );

        if (opcion == 1) {
            listaPersonas.getListaPersonas().get(pid).getListaRegalos().getListaRegalos().remove(rid);
            CRUD.write(listaPersonas);
        }
    }
}
