package _2BusinessTier;

import _3PresentationTier.Scan;
import _1DataTier.ListaPersonas;
import _1DataTier.ListaRegalos;
import _1DataTier.Persona;
import _1DataTier.Regalo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CRUD {

    private static final String path = "proyectoXML/xml/ListaNavidad.xml";

    public static void createZero(ListaPersonas lp, int type){

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        File f;

        Element rootElement;

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
        ListaPersonas listaPersonas = null;
        ListaRegalos listaRegalos;

        Persona persona;
        Regalo regalo;

        File file;

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
            file = new File(path);
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);

            rootElement = document.getDocumentElement(); // Esto te da el nodo raíz, <listaNavidad>
            presupuesto_total = Double.parseDouble(rootElement.getAttribute("presupuesto"));

            listaPersonas = new ListaPersonas(presupuesto_total);

            nodeListP = document.getElementsByTagName("persona");

            for (int i = 0; i < nodeListP.getLength(); i++) {
                nodeP = nodeListP.item(i);

                if (nodeP.getNodeType() == Node.ELEMENT_NODE) {
                    elementP = (Element) nodeP;

                    idp = Integer.parseInt(elementP.getAttribute("idp"));
                    alias = elementP.getAttribute("alias");
                    presupuesto = Double.parseDouble(elementP.getAttribute("presupuesto"));

                    listaRegalos = new ListaRegalos();

                    nodeListR = elementP.getElementsByTagName("regalo");

                    for (int j = 0; j < nodeListR.getLength(); j++) {
                        nodeR = nodeListR.item(j);

                        if (nodeR.getNodeType() == Node.ELEMENT_NODE) {
                            elementR = (Element) nodeR;

                            idr = Integer.parseInt(elementR.getAttribute("idr"));
                            precio = Double.parseDouble(elementR.getElementsByTagName("precio").item(0).getTextContent());
                            item = elementR.getElementsByTagName("item").item(0).getTextContent();

                            regalo = new Regalo(idr, precio, item);

                            listaRegalos.add(regalo);
                        }
                    }

                    persona = new Persona(idp, alias, presupuesto, listaRegalos);

                    listaPersonas.add(persona);
                }
            }

            // Impresión para comprobar resultados
            for(Persona p : listaPersonas.getListaPersonas()){
                System.out.println("Presupuesto total: " + listaPersonas.getPresupuesto());
                System.out.println("=========================================");
                System.out.println("ID Persona: " + p.getIdp());
                System.out.println("Alias: " + p.getAlias());
                System.out.println("Presupuesto: " + p.getPresupuesto());
                System.out.println("Regalos:");
                for (Regalo r : p.getListaRegalos().getListaRegalos()){
                    System.out.println("ID Regalo: " + r.getIdr());
                    System.out.println("Precio: " + r.getPrecio());
                    System.out.println("Item: " + r.getItem());
                }
                System.out.println(" ");
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        if (listaPersonas == null) {
            throw new FileNotFoundException();
        } else {
            return listaPersonas;
        }
    }
}
