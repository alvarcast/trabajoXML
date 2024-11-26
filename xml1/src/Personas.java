import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Personas {
    public static List<Persona> leerTodos(String path){
        List<Persona> personaList = new ArrayList<>();

        int id;
        String nombre;

        try {
            File f = new File(path);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(f);

            NodeList nodeList = document.getElementsByTagName("persona");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == node.ELEMENT_NODE){
                    Element element = (Element) node;

                    id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                    nombre = element.getElementsByTagName("nombre").item(0).getTextContent();

                    personaList.add(new Persona(id, nombre));
                }
            }

            for(Persona p : personaList){
                System.out.println("Persona " + p.getId() + ": " + p.getNombre());
                System.out.println(" ");
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return personaList;
    }
}
