import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Entrenamientos {

    private ArrayList<Entrenamiento> entrenamientoArrayList = new ArrayList<Entrenamiento>();

    public Entrenamientos() {}

    public Entrenamientos (String path) {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        NodeList nodeList;
        File f;
        Node node;
        Element element;

        int id;
        String nombre;
        int duracion;
        String nivel;

        try {
            f = new File(path);
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(f);

            nodeList = document.getElementsByTagName("entrenamiento");

            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);

                if (node.getNodeType() == node.ELEMENT_NODE){
                    element = (Element) node;

                    id = Integer.parseInt(element.getAttribute("id"));
                    nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                    duracion = Integer.parseInt(element.getElementsByTagName("duracion").item(0).getTextContent());
                    nivel = element.getElementsByTagName("nivel").item(0).getTextContent();

                    entrenamientoArrayList.add(new Entrenamiento(id, nombre, duracion, nivel));
                }
            }

            for(Entrenamiento e : entrenamientoArrayList){
                System.out.println("Entrenamiento " + e.getId() + ":");
                System.out.println("Nombre: " + e.getNombre());
                System.out.println("Duración: " + e.getDuracion());
                System.out.println("Nivel: " + e.getNivel());
                System.out.println(" ");
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public Entrenamientos(ArrayList<Entrenamiento> entrenamientoArrayList) {
        this.entrenamientoArrayList = entrenamientoArrayList;
    }

    public ArrayList<Entrenamiento> getEntrenamientoArrayList() {
        return entrenamientoArrayList;
    }

    public void setEntrenamientoArrayList(ArrayList<Entrenamiento> entrenamientoArrayList) {
        this.entrenamientoArrayList = entrenamientoArrayList;
    }

    public void add(Entrenamiento e){
        entrenamientoArrayList.add(e);
    }

    public void exportar(Entrenamientos entrenamientos) {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        File f;

        try {
            f = new File("xml/entrenamientosCopy.xml");
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            Element root = document.createElement("entrenamientos");

            // Crear un nuevo elemento "entrenamiento"
            for (Entrenamiento entrenamiento : entrenamientos.getEntrenamientoArrayList()){
                Element nuevoEntrenamiento = document.createElement("entrenamiento");
                nuevoEntrenamiento.setAttribute("id", String.valueOf(entrenamiento.getId()));

                Element nombre = document.createElement("nombre");
                nombre.setTextContent(entrenamiento.getNombre());

                Element duracion = document.createElement("duracion");
                duracion.setTextContent(String.valueOf(entrenamiento.getDuracion()));

                Element nivel = document.createElement("nivel");
                nivel.setTextContent(entrenamiento.getNivel());

                nuevoEntrenamiento.appendChild(nombre);
                nuevoEntrenamiento.appendChild(duracion);
                nuevoEntrenamiento.appendChild(nivel);

                root.appendChild(nuevoEntrenamiento);
            }

            document.appendChild(root);

            // Guardar los cambios en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(f);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
