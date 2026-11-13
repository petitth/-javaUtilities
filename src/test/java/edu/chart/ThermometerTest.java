package edu.chart;

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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThermometerTest {
    @TempDir
    Path tempDir;

    private static final Logger LOG = LoggerFactory.getLogger(ThermometerTest.class);
    private final int CHART_WIDTH = 800;
    private final int CHART_HEIGHT = 600;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        Files.createDirectories(Paths.get(tempDir.toString()));
    }

    @Test
    public void createThermometer() throws IOException {
        Thermometer thermometer = new Thermometer("Thermometer", 25D);
        String filePath = tempDir + File.separator + "thermometer.png";

        // create chart
        thermometer.saveChartAsPNG(filePath, 350, 350);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createThermometerWithByteArray() throws IOException {
        Thermometer thermometer = new Thermometer("Thermometer", 25D);
        String filePath = tempDir + File.separator + "thermometer-v2.png";

        // create chart
        byte[] chartContent = thermometer.getChartAsPNG(350,350);
        Files.write(Paths.get(filePath), chartContent);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

}
