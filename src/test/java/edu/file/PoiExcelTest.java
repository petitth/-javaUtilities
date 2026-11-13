package edu.file;

import edu.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PoiExcelTest {

    @TempDir
    Path tempDir;

    private String tempFolder;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        tempFolder = tempDir.toString() + File.separator + "poi-excel";
        Files.createDirectories(Paths.get(tempFolder));
    }

    @Test
    public void excelExportWithXSSF() throws IOException {
        byte[] excel = PoiExcelUtil.excelExportWithXSSF(TestUtil.getEmployeeList());

        Path path = Paths.get(tempFolder + File.separator + "employees.xlsx");
        Files.write(path, excel, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void excelExportWithHSSF() throws IOException {
        byte[] excel = PoiExcelUtil.excelExportWithHSSF(TestUtil.getEmployeeList());

        Path path = Paths.get(tempFolder + File.separator + "employees.xls");
        Files.write(path, excel, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void excelExportWithHSSFNoData() throws IOException {
        byte[] excel = PoiExcelUtil.excelExportWithHSSF(new ArrayList<>());

        Path path = Paths.get(tempFolder + File.separator + "employees.xls");
        Files.write(path, excel, CREATE);
        assertTrue(path.toFile().exists());
    }

    @Test
    public void createDocumentWithDatesForOneYear() throws IOException {
        byte[] excel = PoiExcelUtil.createDocumentWithDates(2023);
        Path path = Paths.get(tempFolder + File.separator + "doc2023.xls");
        Files.write(path, excel, CREATE);
        assertTrue(path.toFile().exists());
    }
}