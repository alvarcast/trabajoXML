package _2BusinessTier;

import _1DataTier.ListaPersonas;
import _1DataTier.ListaRegalos;
import _1DataTier.Persona;
import _1DataTier.Regalo;
import _3PresentationTier.Scan;

import java.util.ArrayList;

public class GetStats {

    // No funciona correctamente (concretamente la última comprobación)
    public static boolean checkPresupuesto (ListaPersonas listaPersonas, boolean noPrint) {
        ArrayList<Persona> conRegalosPasados = new ArrayList<Persona>();
        ArrayList<Double> listaPreciosTotalesPasados = new ArrayList<Double>();

        Persona p;

        double sumPresumuestos = 0;
        double sumPrecioRegalos = 0;
        double miniSPR;

        boolean pasado;

        boolean warnings = false;

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
            if (!noPrint) {
                System.err.printf("""
                    >>> Aviso: la suma de todos los presupuestos es mayor al presupuesto total
                    - Presupuesto total: %.2f€
                    - Suma de presupuestos: %.2f€
                    - La suma se pasa por %.2f€
                    > Considere rebajar los presupuestos individuales o aumentar el presupuesto total
                    
                    """, listaPersonas.getPresupuesto(), sumPresumuestos, (sumPresumuestos - listaPersonas.getPresupuesto())
                );
            }
            warnings = true;
        }

        if (sumPresumuestos > listaPersonas.getPresupuesto()) {
            if (!noPrint) {
                System.err.printf("""
                    >>> Aviso: El precio total de todos los regalos se pasa del presupuesto total
                    - Presupuesto total: %.2f€
                    - Total del precio de todos los regalos: %.2f€
                    - El total de los precios de los regalos se pasa por %.2f€
                    > Considere rebajar el precio de los regalos o aumentar el presupuesto total
                    
                    """, listaPersonas.getPresupuesto(), sumPrecioRegalos, (sumPrecioRegalos - listaPersonas.getPresupuesto())
                );
            }
        }

        if (!conRegalosPasados.isEmpty()) {
            if (!noPrint) {
                System.err.println(">>> Aviso: La suma de los precios de los regalos de las siguientes personas se pasan de su presupuesto:");
                for (int i = 0; i < conRegalosPasados.size(); i++) {
                    p = conRegalosPasados.get(i);
                    System.err.printf("""
                        << Regalos de %s >>
                        - Presupuesto: %.2f€
                        - Total del precio de todos los regalos de %s: %.2f€
                        - El total de los precios de los regalos se pasa por %.2f€
                        > Considere rebajar el precio de los regalos de %s o aumentar su presupuesto
                        """, p.getAlias(), p.getPresupuesto(), p.getAlias(), listaPreciosTotalesPasados.get(i), (listaPreciosTotalesPasados.get(i) - p.getPresupuesto()), p.getAlias()
                    );
                }
            }
            warnings = true;
        }

        if (!warnings) {
            if (!noPrint) {
                System.err.println("No hay nada que reportar");
            }
        }

        if (!noPrint) {
            System.out.println(" ");
        }

        return warnings;
    }

    public static void nodeStats (ListaPersonas listaPersonas) {
        int countNodos = 1; // El número mínimo de nodos es 1 (el raíz)
        int countNiveles = 1; // El número mínimo de niveles es 1 (el raíz)

        int countNodosDos = 0;
        int countNodosTres = 0;
        int countNodosCuatro = 0;

        if (!listaPersonas.getListaPersonas().isEmpty()) {
            for (Persona persona : listaPersonas.getListaPersonas()) {
                countNodos++;
                countNodosDos++;

                if (countNiveles < 2) {
                    countNiveles ++;
                }

                for (Regalo regalo : persona.getListaRegalos().getListaRegalos()) {
                    countNodos += 3;
                    countNodosCuatro += 2;
                    countNodosTres++;

                    if (countNiveles < 4) {
                        countNiveles +=2;
                    }
                }
            }

            System.out.printf("- El documento tiene %s nivel/es \n", countNiveles);
            System.out.printf("- El nivel 1 tiene %s nodo/s (total de nodo/s + raíz) \n", countNodos);

            if (countNiveles >= 2) {
                System.out.printf("- El nivel 2 tiene %s nodo/s \n", countNodosDos);
            }

            if (countNiveles == 4) {
                System.out.printf("""
                    · El nivel 3 tiene %s nodo/s
                    · El nivel 4 tiene %s nodo/s
                    """, countNodosTres, countNodosCuatro
                );
            }
        } else {
            System.out.printf("- El documento solo tiene %s nivel (raíz) \n", countNiveles);
            System.out.println("- El documento solo tiene 1 nodo (raíz)");
        }

        System.out.println(" ");
        Scan.waitForInput();
    }

}
