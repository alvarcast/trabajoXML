package _2BusinessTier;

import _1DataTier.ListaPersonas;
import _1DataTier.ListaRegalos;
import _1DataTier.Persona;
import _1DataTier.Regalo;
import _3PresentationTier.Scan;

import java.util.ArrayList;

public class GetStats {

    // Función que comprueba si los precios / presupuestos se pasan de dinero y escribe los avisos correspondientes
    // También se puede simplemente saber si hay errores sin imprimirlos
    public static boolean checkPresupuesto (ListaPersonas listaPersonas, boolean noPrint) {
        // Listas de personas que se pasan de precios y esos precios
        ArrayList<Persona> conRegalosPasados = new ArrayList<Persona>();
        ArrayList<Double> listaPreciosTotalesPasados = new ArrayList<Double>();

        // Usado para asignarle un valor de conRegalosPasados en un for loop
        Persona p;

        // Sumatorios totales
        double sumPresumuestos = 0;
        double sumPrecioRegalos = 0;

        // Mini sumPrecioRegalos (Para cada regalo específico)
        double miniSPR;

        boolean pasado;

        boolean warnings = false;

        // Recorre la lista de personas
        for (Persona persona : listaPersonas.getListaPersonas()) {

            // Reinicia pasado (los que se han pasado) y miniSPR
            miniSPR = 0;
            pasado = false;

            // Suma todos los presupuestos
            sumPresumuestos += persona.getPresupuesto();

            // Recorre la lista de regalos
            for (Regalo regalo : persona.getListaRegalos().getListaRegalos()) {
                // Suma todos los precios de los regalos
                sumPrecioRegalos += regalo.getPrecio();
                miniSPR += regalo.getPrecio();

                // Comprueba si los regalos de una persona específica se pasin de su presupuesto
                if (miniSPR > persona.getPresupuesto() && !pasado) {
                    conRegalosPasados.add(persona);
                    pasado = true;
                }
            }

            // Si se ha pasado añade ese dinero a la lista
            if (pasado) {
                listaPreciosTotalesPasados.add(miniSPR);
            }
        }

        // Si la suma de todos los presupuestos se pasan del presupuesto total:
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

        // Si la suma del precio de todos los regalos se pasan del presupuesto total:
        if (sumPrecioRegalos > listaPersonas.getPresupuesto()) {
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

        // Si la suma del precio de los regalos de una persona específica se pasan del presupuesto de esa persona:
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

        // Si no hay warnings
        if (!warnings) {
            if (!noPrint) {
                System.err.println("No hay nada que reportar");
            }
        }

        if (!noPrint) {
            System.out.println(" ");
        }

        // Devuelve warnings
        return warnings;
    }

    // Método para contar los nodos, los niveles, y los nodos x nivel
    public static void nodeStats (ListaPersonas listaPersonas) {
        int countNodos = 1; // El número mínimo de nodos es 1 (el raíz)
        int countNiveles = 1; // El número mínimo de niveles es 1 (el raíz)

        // Contadores
        int countNodosDos = 0;
        int countNodosTres = 0;
        int countNodosCuatro = 0;

        // Si la lista de personas no esta vacía:
        if (!listaPersonas.getListaPersonas().isEmpty()) {
            for (Persona persona : listaPersonas.getListaPersonas()) {
                // Por cada persona + 1 nodo total y + 1 nodo en el nivel 2
                countNodos++;
                countNodosDos++;

                // Solo suma una vez el nivel por cada persona
                if (countNiveles < 2) {
                    countNiveles ++;
                }

                for (Regalo regalo : persona.getListaRegalos().getListaRegalos()) {
                    // Por cada regalo + 3 nodos total, + 1 nodo en el nivel 3 y + 2 nodos en el nivel 4
                    countNodos += 3;
                    countNodosCuatro += 2;
                    countNodosTres++;

                    // Solo suma una vez el nivel por cada regalo (+2 porque los regalos no pueden estar vacíos)
                    if (countNiveles < 4) {
                        countNiveles +=2;
                    }
                }
            }

            System.out.printf("- El documento tiene %s nivel/es \n", countNiveles);
            System.out.printf("- El nivel 1 tiene %s nodo/s (total de nodo/s + raíz) \n", countNodos);

            // Si hay como mínimo 2 niveles
            if (countNiveles >= 2) {
                System.out.printf("- El nivel 2 tiene %s nodo/s \n", countNodosDos);
            }

            // Si hay como mínimo 4 niveles (no puede haber 3 sin 4)
            if (countNiveles == 4) {
                System.out.printf("""
                    · El nivel 3 tiene %s nodo/s
                    · El nivel 4 tiene %s nodo/s
                    """, countNodosTres, countNodosCuatro
                );
            }
        } else {
            // Este será siempre el resultado si la lista de personas esta vacía
            System.out.printf("- El documento solo tiene %s nivel (raíz) \n", countNiveles);
            System.out.println("- El documento solo tiene 1 nodo (raíz)");
        }

        System.out.println(" ");
        Scan.waitForInput();
    }

}
