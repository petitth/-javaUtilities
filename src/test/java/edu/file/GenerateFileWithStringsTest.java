package edu.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenerateFileWithStringsTest {

    @TempDir
    Path tempDir;

    private static final Logger LOG = LoggerFactory.getLogger(GenerateFileWithStringsTest.class);
    private static final int NB_ELTS = 100;
    private static final String ELT_TEXT = "foo";
    private static final String FOLDER = "sandbox";
    private static final DecimalFormat df = new DecimalFormat("000");

    @BeforeEach
    public void setup() throws IOException {
        // prepare a temp folder
        Files.createDirectories(Paths.get(tempDir.toString() + File.separator + FOLDER));
    }

    @Test
    public void createFileWithStrings() throws IOException {
        String filePath = tempDir.toString() + File.separator + FOLDER + File.separator + "foobar.txt";
        StringBuilder sb = new StringBuilder();
        for (int i=1; i<=NB_ELTS; i++) {
            sb.append(ELT_TEXT);
            sb.append(df.format(i));
            sb.append("\n");
        }

        Files.write(Paths.get(filePath), sb.toString().getBytes(UTF_8), CREATE);
        LOG.debug("done creating file in {}", filePath);
        assertTrue(new File(filePath).exists());
    }

}
