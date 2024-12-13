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

    // Variable estática de la ruta, para poder acceder a ella siempre
    private static final String path = "proyectoXML/xml/ListaNavidad.xml";

    // Función que lee el fichero y lo guarda en una lista de personas (listaNavidad)
    public static ListaPersonas read() throws IOException {
        // Lista de personas y lista de regalos
        ListaPersonas listaPersonas = null;
        ListaRegalos listaRegalos;

        // Persona y regalo
        Persona persona;
        Regalo regalo;

        // Fichero
        File file;

        // Document y documentbuilders
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;

        // Listas de nodos
        NodeList nodeListP;
        NodeList nodeListR;

        // Nodos
        Node nodeP;
        Node nodeR;

        // Elementos de los nodos
        Element root;
        Element elementP;
        Element elementR;

        // Atributo presupuesto total
        double presupuesto_total;

        // Atributos de persona
        int idp;
        String alias;
        double presupuesto;

        // Atributo y elementos de regalo
        int idr;
        double precio;
        String item;

        // Intenta construir el documento existente (fichero)
        try {
            file = new File(path);
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);

            // Lista de nodos persona
            nodeListP = document.getElementsByTagName("persona");

            // Si hay nodos persona:
            if (nodeListP.getLength() > 0) {
                root = document.getDocumentElement(); // Esto te da el nodo raíz, <listaNavidad>

                presupuesto_total = Double.parseDouble(root.getAttribute("presupuesto_total"));

                // Añade el presupuesto a lista personas
                listaPersonas = new ListaPersonas(presupuesto_total);

                // Recorre el nodelist de personas
                for (int i = 0; i < nodeListP.getLength(); i++) {
                    nodeP = nodeListP.item(i);

                    // Si el tipo coincide:
                    if (nodeP.getNodeType() == Node.ELEMENT_NODE) {
                        elementP = (Element) nodeP;

                        idp = Integer.parseInt(elementP.getAttribute("idp"));
                        alias = elementP.getAttribute("alias");
                        presupuesto = Double.parseDouble(elementP.getAttribute("presupuesto"));

                        listaRegalos = new ListaRegalos();

                        // Lo mismo con regalos
                        nodeListR = elementP.getElementsByTagName("regalo");

                        // Recorre los elementos regalo dentro de cada persona
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

                        // Lo añade todo a una nueva persona y la mete en la lista de personas
                        persona = new Persona(idp, alias, presupuesto, listaRegalos);
                        listaPersonas.add(persona);
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        // Finalmente devuelve la lista
        return listaPersonas;
    }

    // Método para reescribie el fichero xml con una lista de personas
    public static void write (ListaPersonas listaPersonas) {
        // Document y documentbuilders
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document = null;

        // Transformers para concatenar todo bien
        TransformerFactory transformerFactory;
        Transformer transformer;

        // Para configurar la transformación
        DOMSource source;
        StreamResult result;

        // Fichero
        File file = null;

        // Elementos nuevos de los nodos persona y root
        Element root;
        Element newPersona;
        Element newRegalo;

        // Elementos nuevos de los nodos regalo
        Element newItem;
        Element newPrecio;

        // Intenta construir el documento (fichero)
        try {
            file = new File(path);
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            root = document.createElement("listaRegalos");

            root.setAttribute("presupuesto_total", String.valueOf(listaPersonas.getPresupuesto()));

            // Vuelve a crear los elementos persona
            for (Persona persona : listaPersonas.getListaPersonas()) {
                newPersona = document.createElement("persona");
                newPersona.setAttribute("idp", String.valueOf(persona.getIdp()));
                newPersona.setAttribute("alias", persona.getAlias());
                newPersona.setAttribute("presupuesto", String.valueOf(persona.getPresupuesto()));

                // Vuelve a crear los elementos regalo
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

                // Mete todo dentro de root (listaNavidad)
                root.appendChild(newPersona);
            }

            // Y el root en el documento
            document.appendChild(root);

            // Guardar los cambios en el archivo XML con formato de indentación
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();

            // Configurar la transformación para que use la indentación
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Ejecutar los cambios
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
