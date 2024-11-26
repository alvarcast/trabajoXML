public class Main {
    public static void main(String[] args) {
        //Entrenamientos entrenamientos = new Entrenamientos("xml/entrenamientos.xml");

        Entrenamiento entrenamiento1 = new Entrenamiento(1,"HIIT Avanzado", 40, "Duro");
        Entrenamiento entrenamiento2 = new Entrenamiento(2,"Yoga", 40, "Fácil");
        Entrenamiento entrenamiento3 = new Entrenamiento(3,"Pilates", 40, "Intermedio");
        Entrenamiento entrenamiento4 = new Entrenamiento(4,"Plancha", 5, "Intermedio");

        Entrenamientos entrenamientos = new Entrenamientos();
        entrenamientos.add(entrenamiento1);
        entrenamientos.add(entrenamiento2);
        entrenamientos.add(entrenamiento3);
        entrenamientos.add(entrenamiento4);

        entrenamientos.exportar(entrenamientos);
    }
}