package _2BusinessTier;

import _1DataTier.ListaPersonas;
import _1DataTier.Persona;
import _1DataTier.Regalo;

import java.util.ArrayList;

public class GetStats {

    public static void getStats () {

    }

    public static void checkPresupuesto (ListaPersonas listaPersonas) {
        ArrayList<Persona> conRegalosPasados = new ArrayList<Persona>();
        ArrayList<Double> listaPreciosTotalesPasados = new ArrayList<Double>();

        Persona p;

        double sumPresumuestos = 0;
        double sumPrecioRegalos = 0;
        double miniSPR;

        boolean pasado = false;

        for (Persona persona : listaPersonas.getListaPersonas()) {

            miniSPR = 0;
            pasado = false;

            sumPresumuestos += persona.getPresupuesto();

            for (Regalo regalo : persona.getListaRegalos().getListaRegalos()) {
                sumPrecioRegalos += regalo.getPrecio();
                miniSPR += regalo.getPrecio();

                if (miniSPR > persona.getPresupuesto() && !pasado) {
                    conRegalosPasados.add(persona);
                    pasado = true;
                }
            }

            if (pasado) {
                listaPreciosTotalesPasados.add(miniSPR);
            }
        }

        if (sumPresumuestos > listaPersonas.getPresupuesto()) {
            System.err.printf("""
                    >>> Aviso: la suma de todos los presupuestos es mayor al presupuesto total
                    - Presupuesto total: %.2f€
                    - Suma de presupuestos: %.2f€
                    - La suma se pasa por %.2f€
                    
                    """, listaPersonas.getPresupuesto(), sumPresumuestos, (sumPresumuestos - listaPersonas.getPresupuesto())
            );
        }

        if (sumPresumuestos > listaPersonas.getPresupuesto()) {
            System.err.printf("""
                    >>> Aviso: El precio total de todos los regalos se pasa del presupuesto total
                    - Presupuesto total: %.2f€
                    - Total del precio de todos los regalos: %.2f€
                    - El total de los precios de los regalos se pasa por %.2f€
                    
                    """, listaPersonas.getPresupuesto(), sumPrecioRegalos, (sumPrecioRegalos - listaPersonas.getPresupuesto())
            );
        }

        if (pasado) {
            System.err.println(">>> Aviso: La suma de los precios de los regalos de las siguientes personas se pasan de su presupuesto:");
            for (int i = 0; i < conRegalosPasados.size(); i++) {
                p = conRegalosPasados.get(i);
                System.err.printf("""
                        << Regalos de %s >>
                        - Presupuesto: %.2f€
                        - Total del precio de todos los regalos de %s: %.2f€
                        - El total de los precios de los regalos se pasa por %.2f€
                        """, p.getAlias(), p.getPresupuesto(), p.getAlias(), listaPreciosTotalesPasados.get(i), (listaPreciosTotalesPasados.get(i) - p.getPresupuesto())
                );
            }
        }

        System.out.println(" ");
    }

}
