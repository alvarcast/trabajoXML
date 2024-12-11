package _2BusinessTier;

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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class CRUD {

    private static final String path = "proyectoXML/xml/ListaNavidad.xml";

    public static ListaPersonas read() throws IOException {
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

        Element root;
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

            nodeListP = document.getElementsByTagName("persona");

            if (nodeListP.getLength() > 0) {
                root = document.getDocumentElement(); // Esto te da el nodo raíz, <listaNavidad>

                presupuesto_total = Double.parseDouble(root.getAttribute("presupuesto_total"));

                listaPersonas = new ListaPersonas(presupuesto_total);

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
            }

            /*
            if (listaPersonas != null) {
                System.out.println("=========================================");
                System.out.println("Presupuesto total: " + listaPersonas.getPresupuesto());
                System.out.println("=========================================");
                System.out.println(" ");

                for(Persona p : listaPersonas.getListaPersonas()){
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
            }
             */

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        return listaPersonas;
    }

    public static void write (ListaPersonas listaPersonas) {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;

        TransformerFactory transformerFactory;
        Transformer transformer;

        DOMSource source;
        StreamResult result;

        File file;

        Element root;
        Element newPersona;
        Element newRegalo;

        Element newItem;
        Element newPrecio;

        try {
            file = new File(path);
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            root = document.createElement("listaRegalos");


            root.setAttribute("presupuesto_total", String.valueOf(listaPersonas.getPresupuesto()));


            // Crear un nuevo elemento "persona"
            for (Persona persona : listaPersonas.getListaPersonas()) {
                newPersona = document.createElement("persona");
                newPersona.setAttribute("idp", String.valueOf(persona.getIdp()));
                newPersona.setAttribute("alias", persona.getAlias());
                newPersona.setAttribute("presupuesto", String.valueOf(persona.getPresupuesto()));

                for (Regalo regalo : persona.getListaRegalos().getListaRegalos()) {
                    newRegalo = document.createElement("regalo");
                    newRegalo.setAttribute("idr", String.valueOf(regalo.getIdr()));

                    newItem = document.createElement("item");
                    newItem.setTextContent(regalo.getItem());

                    newPrecio = document.createElement("precio");
                    newPrecio.setTextContent(String.valueOf(regalo.getPrecio()));

                    newRegalo.appendChild(newItem);
                    newRegalo.appendChild(newPrecio);

                    newPersona.appendChild(newRegalo);
                }

                root.appendChild(newPersona);
            }

            document.appendChild(root);

            // Guardar los cambios en el archivo XML con formato de indentación
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();

            // Configurar la transformación para que use la indentación
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            source = new DOMSource(document);
            result = new StreamResult(file);
            transformer.transform(source, result);

            System.out.println(">>> Cambios escritos correctamente <<<");
            System.out.println(" ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
