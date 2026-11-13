package edu.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class XmlTest {

    private static final Logger LOG = LoggerFactory.getLogger(XmlTest.class);

    @TempDir
    Path tempDir;

    private String tempFolder;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        tempFolder = tempDir.toString() + File.separator + "xml-test";
        Files.createDirectories(Paths.get(tempFolder));
    }

    @Test
    public void parseXmlWithSax() throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("./src/test/resources/menu.xml"));

            LOG.debug("xml version = {}", document.getXmlVersion());
            assertEquals("1.0", document.getXmlVersion());
            LOG.debug("encoding = {}", document.getXmlEncoding());
            assertEquals("UTF-8", document.getXmlEncoding());
            LOG.debug("---------------------------------------");

            Element rootElement = document.getDocumentElement();
            LOG.debug("root element = {}", rootElement.getNodeName());
            assertEquals("breakfast_menu", rootElement.getNodeName());

            NodeList rootNodes = rootElement.getChildNodes();
            for (int i=0; i<rootNodes.getLength(); i++) {
                if(rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Node menuNode = rootNodes.item(i);
                    Element menuElt = (Element) menuNode;
                    // child nodes
                    Element name = (Element) menuElt.getElementsByTagName("name").item(0);
                    Element price = (Element) menuElt.getElementsByTagName("price").item(0);
                    Element desc = (Element) menuElt.getElementsByTagName("description").item(0);
                    // display node + child nodes
                    LOG.debug("> node name = {} - id = {}", menuNode.getNodeName(), menuElt.getAttribute("id"));
                    LOG.debug("\t- name = {}", name.getTextContent());
                    LOG.debug("\t- price = {}", price.getTextContent());
                    LOG.debug("\t- description = {}", desc.getTextContent());
                }
            }

        } catch(ParserConfigurationException | SAXException xmlEx) {
            LOG.error("an error occurred while parsing xml", xmlEx);
            fail("an error occurred while parsing xml");
        }
    }

    @Test
    public void createXmlWithOneNode() throws IOException {
        try {
            // init
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // create root
            Element rootElement = document.createElement("person_list");
            document.appendChild(rootElement);

            // create one element
            Element person = document.createElement("person");
            rootElement.appendChild(person);
            Element firstName = document.createElement("firstName");
            firstName.appendChild(document.createTextNode("Bob"));
            person.appendChild(firstName);
            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode("Razovsky"));
            person.appendChild(lastName);

            // write xml to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult outputXml = new StreamResult(new File(tempFolder + File.separator + "persons.xml"));
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, outputXml);

            // display xml
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            LOG.debug("xml : \n{}", writer.toString());

            // validate it
            InputSource is = new InputSource(new StringReader(writer.toString()));
            Document newDocument = builder.parse(is);
            assertEquals("1.0", newDocument.getXmlVersion());
            assertEquals("UTF-8", newDocument.getXmlEncoding());
            Element rootNewDoc = newDocument.getDocumentElement();
            assertEquals("person_list", rootNewDoc.getNodeName());
            int nbElts = 0;
            NodeList rootNewNodes = rootNewDoc.getChildNodes();
            for (int i=0; i<rootNewNodes.getLength(); i++) {
                if(rootNewNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    nbElts++;

                    Node personNode = rootNewNodes.item(i);
                    Element personElt = (Element) personNode;
                    // child nodes
                    Element eltFirstName = (Element) personElt.getElementsByTagName("firstName").item(0);
                    assertEquals("Bob", eltFirstName.getTextContent());
                    Element eltLastName = (Element) personElt.getElementsByTagName("lastName").item(0);
                    assertEquals("Razovsky", eltLastName.getTextContent());
                }
            }
            assertEquals(1, nbElts);

        } catch(ParserConfigurationException | TransformerException | SAXException xmlEx) {
            LOG.error("an error occurred while processing xml", xmlEx);
            fail("an error occurred while processing xml");
        }
    }
}
