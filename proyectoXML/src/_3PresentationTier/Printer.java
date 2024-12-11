package _3PresentationTier;

import _1DataTier.ListaPersonas;
import _1DataTier.Persona;
import _1DataTier.Regalo;

public class Printer {

    public static void printList(ListaPersonas listaPersonas) {

        System.out.println("=========================================");
        System.out.println("Presupuesto total: " + listaPersonas.getPresupuesto());
        System.out.println("=========================================");
        System.out.println(" ");

        for(Persona persona : listaPersonas.getListaPersonas()){
            System.out.println("ID Persona: " + persona.getIdp());
            System.out.println("Alias: " + persona.getAlias());
            System.out.printf("Presupuesto: %.2f", persona.getPresupuesto());
            System.out.println("Regalos:");
            for (Regalo regalo : persona.getListaRegalos().getListaRegalos()){
                System.out.println("ID Regalo: " + regalo.getIdr());
                System.out.printf("Precio: %.2f", regalo.getPrecio());
                System.out.println("Item: " + regalo.getItem());
            }
            System.out.println(" ");
        }
    }
}
