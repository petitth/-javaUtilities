package edu.file;

import edu.exception.XPathException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class XpathParserTest {

    private static final Logger LOG = LoggerFactory.getLogger(XpathParserTest.class);

    @TempDir
    Path tempDir;

    private String tempFolder;


    @Test
    public void getNodeList() throws XPathException {
        XPathParser parser = new XPathParser("./src/test/resources/books.xml");
        NodeList nodeList = parser.getNodeList("//book[year>2003]/title/text()");
        assertNotNull(nodeList);
        assertEquals(2, nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            LOG.info("{}) title = '{}'", i+1, nodeList.item(i).getNodeValue());
        }
    }

    @Test
    public void getNodeList2() throws XPathException {
        XPathParser parser = new XPathParser("./src/test/resources/books.xml");
        NodeList nodeList = parser.getNodeList("//book[year>2003]");
        assertNotNull(nodeList);
        assertEquals(2, nodeList.getLength());
        for (int i=0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            LOG.info("{}) id = {} - category = '{}' ", i+1, node.getAttributes().getNamedItem("id").getNodeValue(), node.getAttributes().getNamedItem("category").getNodeValue());
            LOG.info("\t title = '{}'", node.getChildNodes().item(1).getTextContent());
        }
    }

    @Test
    public void failInstantiatingParser() throws IOException {
        tempFolder = tempDir.toString() + File.separator + "xpath";
        Files.createDirectories(Paths.get(tempFolder));
        try {
            XPathParser parser = new XPathParser(tempFolder + File.separator + "foo.xml");
            fail("it cannot work");
        } catch(XPathException ex) {
            assertTrue(ex.getMessage().contains("No such file or directory"));
        }
    }

    @Test
    public void failXpathExpression() {
        try {
            XPathParser parser = new XPathParser("./src/test/resources/books.xml");
            NodeList nodeList = parser.getNodeList("alert('foo')");
            fail("it cannot work");

        } catch (XPathException ex) {
            assertTrue(ex.getMessage().contains("Could not find function: alert"));
        }
    }

}