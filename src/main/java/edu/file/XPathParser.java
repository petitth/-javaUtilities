package edu.file;

import edu.exception.XPathException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class XPathParser {

    private Document xmlDocument;

    private XPathParser() { }

    public XPathParser(String xmlFilePath) throws XPathException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setXIncludeAware(false);
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            this.xmlDocument = builder.parse(xmlFilePath);
        } catch(ParserConfigurationException | IOException | SAXException ex) {
            throw new XPathException(ex.getMessage());
        }
    }

    public NodeList getNodeList(String expression) throws XPathException {
        try {
            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();
            XPathExpression expr = xpath.compile(expression);
            Object result = expr.evaluate(xmlDocument, XPathConstants.NODESET);
            return (NodeList) result;

        } catch(XPathExpressionException ex) {
            throw new XPathException(ex.getMessage());
        }
    }
}
