package edu.file;

import edu.common.TestData;
import edu.database.SqliteUtil;
import edu.exception.SqliteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardOpenOption.CREATE;
import static org.junit.jupiter.api.Assertions.*;

public class PdfboxTableDocumentTest {

    private static final Logger LOG = LoggerFactory.getLogger(PdfboxTableDocumentTest.class);

    @TempDir
    Path tempDir;

    private static String tempFolder;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        tempFolder = tempDir.toString();
        Files.createDirectories(Paths.get(tempFolder));
    }

    @Test
    public void pdfboxDocumentNoColor() throws IOException {
        // create pdf
        PdfboxTableDocument document = new PdfboxTableDocument();
        document.addTableColumn("CustomerID", "ID", 60);
        document.addTableColumn("ContactName", "Name", 150);
        document.addTableColumn("City", "City", 100);
        document.addTableColumn("Country", "Country", 100);
        document.addTableRows(TestData.getEmployees());
        document.setDocumentAuthor("John Smith");
        document.setDocumentTitle("Employees List");
        document.setDocumentKeywords("foo, bar");
        byte[] pdf = document.build();

        Path path = Paths.get(tempFolder + File.separator + "employees.pdf");
        Files.write(path, pdf, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void pdfboxDocumentWithDefaultColors() throws IOException, SqliteException {
        // create db containing customers table and retrieve its content
        String dbPath = tempFolder + File.separator + "customers.sqlite";
        String sqlCreateCustomers = FileUtil.getFileContent("." + File.separator + "data" + File.separator + "sqlite-customers.sql");
        SqliteUtil.update(dbPath, sqlCreateCustomers);
        List<Map<String, Object>> listCustomers = SqliteUtil.selectToMap(dbPath,"select * from customers order by CustomerID");
        assertEquals(91, listCustomers.size());

        // create pdf
        PdfboxTableDocument document = new PdfboxTableDocument();
        document.addTableColumn("CustomerID", "ID", 60);
        document.addTableColumn("ContactName", "Name", 150);
        document.addTableColumn("City", "City", 100);
        document.addTableColumn("Country", "Country", 100);
        document.addTableRows(listCustomers);
        document.setDocumentAuthor("John Smith");
        document.setDocumentTitle("Employees List");
        document.setDocumentKeywords("foo, bar");
        document.setTableUseColors(true);
        byte[] pdf = document.build();

        Path path = Paths.get(tempFolder + File.separator + "employees.pdf");
        Files.write(path, pdf, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void pdfboxDocumentWithCustomColors() throws IOException {
        // create pdf
        PdfboxTableDocument document = new PdfboxTableDocument();
        document.addTableColumn("CustomerID", "ID", 60);
        document.addTableColumn("ContactName", "Name", 150);
        document.addTableColumn("City", "City", 100);
        document.addTableColumn("Country", "Country", 100);
        document.addTableRows(TestData.getEmployees());
        document.setDocumentAuthor("John Smith");
        document.setDocumentTitle("Employees List");
        document.setDocumentKeywords("foo, bar");
        document.setTableUseColors(true);
        document.setTableHeaderBackgroundColor(new Color(76, 129, 190));
        document.setTableRowsOddBackgroundColor(new Color(218, 230, 242)); // light
        document.setTableRowsEvenBackgroundColor(new Color(186, 206, 230)); // darker
        byte[] pdf = document.build();

        Path path = Paths.get(tempFolder + File.separator + "employees.pdf");
        Files.write(path, pdf, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void pdfboxDocumentWithCustomColorsForText() throws IOException {
        // create pdf
        PdfboxTableDocument document = new PdfboxTableDocument();
        document.addTableColumn("CustomerID", "ID", 60);
        document.addTableColumn("ContactName", "Name", 150);
        document.addTableColumn("City", "City", 100);
        document.addTableColumn("Country", "Country", 100);
        document.addTableRows(TestData.getEmployees());
        document.setDocumentAuthor("John Smith");
        document.setDocumentTitle("Employees List");
        document.setDocumentKeywords("foo, bar");
        document.setTableUseColors(true);
        document.setTableHeaderTextColor(new Color(255, 255, 255));
        document.setTableRowsTextColor(new Color(255, 0, 0));
        document.setTableHeaderBackgroundColor(new Color(76, 129, 190));
        document.setTableRowsOddBackgroundColor(new Color(218, 230, 242)); // light
        document.setTableRowsEvenBackgroundColor(new Color(186, 206, 230)); // darker
        byte[] pdf = document.build();

        Path path = Paths.get(tempFolder + File.separator + "employees.pdf");
        Files.write(path, pdf, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void pdfboxDocumentAddRecordsOneByOne() throws IOException {
        PdfboxTableDocument document = new PdfboxTableDocument();
        document.addTableColumn("CustomerID", "ID", 60);
        document.addTableColumn("ContactName", "Name", 150);
        document.addTableColumn("City", "City", 100);
        document.addTableColumn("Country", "Country", 100);
        document.addTableRow(TestData.getEmployees().get(0));
        document.addTableRow(TestData.getEmployees().get(1));
        document.addTableRow(TestData.getEmployees().get(2));
        document.addTableRow(TestData.getEmployees().get(3));
        document.addTableRow(TestData.getEmployees().get(4));
        document.setDocumentAuthor("John Smith");
        document.setDocumentTitle("Employees List");
        document.setDocumentKeywords("foo, bar");
        document.setTableDisplayCounterInFirstColumn(false);
        byte[] pdf = document.build();

        Path path = Paths.get(tempFolder + File.separator + "employees.pdf");
        Files.write(path, pdf, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void pdfboxDocumentNoCounter() throws IOException {
        PdfboxTableDocument document = new PdfboxTableDocument();
        document.addTableColumn("CustomerID", "ID", 60);
        document.addTableColumn("ContactName", "Name", 150);
        document.addTableColumn("City", "City", 100);
        document.addTableColumn("Country", "Country", 100);
        document.addTableRows(TestData.getEmployees());
        document.setDocumentAuthor("John Smith");
        document.setDocumentTitle("Employees List");
        document.setDocumentKeywords("foo, bar");
        document.setTableDisplayCounterInFirstColumn(false);
        assertFalse(document.isTableDisplayCounterInFirstColumn());
        byte[] pdf = document.build();

        Path path = Paths.get(tempFolder + File.separator + "employees.pdf");
        Files.write(path, pdf, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void pdfboxDocumentNoColumnsAndCounter() {
        try {
            PdfboxTableDocument document = new PdfboxTableDocument();
            document.addTableRows(TestData.getEmployees());
            byte[] pdf = document.build();
            fail("it cannot work");

        } catch(IOException ex) {
            assertTrue(ex.getMessage().contains("no columns have been defined for the table"));
        }
    }

    @Test
    public void pdfboxDocumentNoColumnsAndNoCounter() {
        try {
            PdfboxTableDocument document = new PdfboxTableDocument();
            document.setTableDisplayCounterInFirstColumn(false);
            document.addTableRows(TestData.getEmployees());
            byte[] pdf = document.build();
            fail("it cannot work");

        } catch(IOException ex) {
            assertTrue(ex.getMessage().contains("no columns have been defined for the table"));
        }
    }

    @Test
    public void pdfboxDocumentNoRows() {
        try {
            PdfboxTableDocument document = new PdfboxTableDocument();
            document.addTableColumn("CustomerID", "ID", 60);
            document.addTableColumn("ContactName", "Name", 150);
            document.addTableColumn("City", "City", 100);
            document.addTableColumn("Country", "Country", 100);
            byte[] pdf = document.build();
            fail("it cannot work");

        } catch(IOException ex) {
            assertTrue(ex.getMessage().contains("no rows have been defined for the table"));
        }
    }

    @Test
    public void pdfboxDocumentMaxWidth() {
        try {
            PdfboxTableDocument document = new PdfboxTableDocument();
            document.addTableColumn("CustomerID", "ID", 200);
            document.addTableColumn("ContactName", "Name", 200);
            document.addTableColumn("City", "City", 200);
            document.addTableColumn("Country", "Country", 200);
            document.addTableRows(TestData.getEmployees());
            byte[] pdf = document.build();
            fail("it cannot work");

        } catch(IOException ex) {
            LOG.error(ex.getMessage());
            assertTrue(ex.getMessage().contains("table width have crossed maximum value"));
        }
    }

}