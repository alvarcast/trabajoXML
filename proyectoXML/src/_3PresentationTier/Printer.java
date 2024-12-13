package _3PresentationTier;

import _1DataTier.ListaPersonas;
import _1DataTier.Persona;
import _1DataTier.Regalo;

public class Printer {

    public static void printList(ListaPersonas listaPersonas) {

        // Imprime todas las cosas del xml si la lista de personas no esta vacia
        if (!listaPersonas.getListaPersonas().isEmpty()) {
            System.out.println("=========================================");
            System.out.println("Presupuesto total: " + listaPersonas.getPresupuesto());
            System.out.println("=========================================");
            System.out.println(" ");

            for(Persona persona : listaPersonas.getListaPersonas()){
                System.out.println(">>> ID Persona: " + persona.getIdp() + " <<<");
                System.out.println("Alias: " + persona.getAlias());
                System.out.printf("Presupuesto: %.2f \n", persona.getPresupuesto());
                System.out.println("Regalos:");
                for (Regalo regalo : persona.getListaRegalos().getListaRegalos()){
                    System.out.println(">> ID Regalo: " + regalo.getIdr() + " <<");
                    System.out.printf("Precio: %.2f \n", regalo.getPrecio());
                    System.out.println("Item: " + regalo.getItem());
                }
                System.out.println(" ");
            }
            Scan.waitForInput();
        } else {
            System.err.println("No hay personas que mostrar");
            System.out.println(" ");
        }
    }

    // Imprime todas las personas y sus presupuestos
    public static void printPersonas (ListaPersonas listaPersonas) {
        for (int i = 0; i < listaPersonas.getListaPersonas().size(); i++) {
            System.out.println((i + 1) + ". " + listaPersonas.getListaPersonas().get(i).getAlias());
            System.out.printf("- Presupuesto: %s \n", listaPersonas.getListaPersonas().get(i).getPresupuesto());
            System.out.println(" ");
        }
    }

    // Imprime todas las personas y sus presupuestos, junto con los regalos y sus precion
    public static void printPersonasRegalos (ListaPersonas listaPersonas) {
        int contador;

        for (int i = 0; i < listaPersonas.getListaPersonas().size(); i++) {
            contador = 1;
            System.out.println("=== " + (i + 1) + ". " + listaPersonas.getListaPersonas().get(i).getAlias() + " ===");
            System.out.printf("- Presupuesto: %s \n", listaPersonas.getListaPersonas().get(i).getPresupuesto());
            for (Regalo regalo : listaPersonas.getListaPersonas().get(i).getListaRegalos().getListaRegalos()) {
                System.out.printf(contador + "." +" %s: %.2f€ \n", regalo.getItem(), regalo.getPrecio());
                contador++;
            }
            System.out.println(" ");
        }
    }
}
