package _BT;

import PT.Scan;
import __DT.ListaPersonas;
import __DT.ListaRegalos;
import __DT.Persona;
import __DT.Regalo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CRUD {

    private static final String path = "proyectoXML/xml/ListaNavidad.xml";

    public static void create(ListaPersonas lp, int type){

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        File f;

        Element rootElement;

        // ¿Es mejor tener todas las variables arriba o mejor solo debajo del type que se vaya a usar?
        // ¿Hay menuCtrl?

        double presupuesto_total;

        int idp;
        String alias;
        double presupuesto;

        int idr;
        double precio;
        String item;

        try {
            f = new File(path);
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            rootElement = document.getDocumentElement();

            if (rootElement == null) {
                presupuesto_total = Scan.scanDouble("Presupuesto total: ");
                // De cero...
            } else if (type == 1) {
                alias = Scan.scanText("Alias: ");
                presupuesto = Scan.scanDouble("Presupuesto: ");
                // Añadir persona...
            } else if (type == 2) {
                item = Scan.scanText("Item: ");
                precio = Scan.scanDouble("Precio: ");
                // Añadir regalo...
            }

            // Check entrenamientos

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ListaPersonas read() throws FileNotFoundException{
        ListaPersonas lp = null;
        ListaRegalos lr;

        Persona p;
        Regalo r;

        File f;

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;

        NodeList nodeListP;
        NodeList nodeListR;

        Node nodeP;
        Node nodeR;

        Element rootElement;
        Element elementP;
        Element elementR;

        double presupuesto_total;

        int idp;
        String alias;
        double presupuesto;

        int idr;
        double precio;
        String item;

        try {
            f = new File(path);
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(f);

            rootElement = document.getDocumentElement(); // Esto te da el nodo raíz, <listaNavidad>
            presupuesto_total = Double.parseDouble(rootElement.getAttribute("presupuesto"));

            lp = new ListaPersonas(presupuesto_total);

            nodeListP = document.getElementsByTagName("persona");

            for (int i = 0; i < nodeListP.getLength(); i++) {
                nodeP = nodeListP.item(i);

                if (nodeP.getNodeType() == Node.ELEMENT_NODE) {
                    elementP = (Element) nodeP;

                    idp = Integer.parseInt(elementP.getAttribute("idp"));
                    alias = elementP.getAttribute("alias");
                    presupuesto = Double.parseDouble(elementP.getAttribute("presupuesto"));

                    lr = new ListaRegalos();

                    nodeListR = elementP.getElementsByTagName("regalo");

                    for (int j = 0; j < nodeListR.getLength(); j++) {
                        nodeR = nodeListR.item(j);

                        if (nodeR.getNodeType() == Node.ELEMENT_NODE) {
                            elementR = (Element) nodeR;

                            idr = Integer.parseInt(elementR.getAttribute("idr"));
                            precio = Double.parseDouble(elementR.getElementsByTagName("precio").item(0).getTextContent());
                            item = elementR.getElementsByTagName("item").item(0).getTextContent();

                            r = new Regalo(idr, precio, item);

                            lr.add(r);
                        }
                    }

                    p = new Persona(idp, alias, presupuesto, lr);

                    lp.add(p);
                }
            }

            for(Persona persona : lp.getListaPersonas()){
                System.out.println("Presupuesto total: " + lp.getPresupuesto());
                System.out.println("=========================================");
                System.out.println("ID Persona: " + persona.getIdp());
                System.out.println("Alias: " + persona.getAlias());
                System.out.println("Presupuesto: " + persona.getPresupuesto());
                System.out.println("Regalos:");
                for (Regalo regalo : persona.getListaRegalos().getListaRegalos()){
                    System.out.println("ID Regalo: " + regalo.getIdr());
                    System.out.println("Precio: " + regalo.getPrecio());
                    System.out.println("Item: " + regalo.getItem());
                }
                System.out.println(" ");
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        if (lp == null) {
            throw new FileNotFoundException();
        } else {
            return lp;
        }
    }
}
