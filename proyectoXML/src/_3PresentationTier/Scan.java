package _3PresentationTier;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Scan {

    //Metodo para pedir un int
    public static int scanInt(String txt){
        Scanner scn = new Scanner(System.in);
        boolean out = false;
        int data = 0;

        do {
            System.out.println(txt);

            try {
                data = scn.nextInt();
                if (data < 0){
                    System.err.println("Por favor, introduzca un valor positivo");
                } else {
                    out = true;
                }
            } catch (InputMismatchException ex){
                System.err.println("Valor inválido, inténtelo de nuevo");
            }

            scn.nextLine();

        } while (!out);

        return data;
    }

    //Metodo para pedir un int con un rango
    public static int scanInt(String txt, int min, int max){
        Scanner scn = new Scanner(System.in);
        boolean out = false;
        int data = 0;

        do {
            System.out.println(txt);

            try {
                data = scn.nextInt();
                if (data < min || data > max){
                    System.err.println("Por favor, introduzca un valor positivo");
                } else {
                    out = true;
                }
            } catch (InputMismatchException ex){
                System.err.println("Valor inválido, inténtelo de nuevo");
            }

            scn.nextLine();

        } while (!out);

        return data;
    }

    //Metodo para pedir un double
    public static double scanDouble(String txt){
        Scanner scn = new Scanner(System.in);
        boolean out = false;
        double data = 0;

        do {
            System.out.println(txt);

            try {
                data = scn.nextDouble();
                if (data < 0){
                    System.err.println("Por favor, introduzca un valor positivo");
                } else {
                    out = true;
                }
            } catch (InputMismatchException ex){
                System.err.println("Valor inválido, inténtelo de nuevo");
            }

            scn.nextLine();

        } while (!out);

        return data;
    }

    //Metodo para pedir una cadena
    public static String scanText(String txt){
        Scanner scn = new Scanner(System.in);
        String data = "";

        while (data.isEmpty()){
            System.out.println(txt);
            data = scn.nextLine();

            if (data.isEmpty()){
                System.err.println("Por favor, rellene la cadena");
            }
        }

        return data;
    }

    //Metodo que espera hasta que se dé cualquier valor para continuar (enter)
    public static void waitForInput(){
        Scanner scn = new Scanner(System.in);

        System.out.println(">>> Enter para continuar <<<");
        scn.nextLine();
    }
}
