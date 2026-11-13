package edu.chart;

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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpeedCounterTest {
    @TempDir
    Path tempDir;

    private static final Logger LOG = LoggerFactory.getLogger(SpeedCounterTest.class);
    private final int CHART_WIDTH = 800;
    private final int CHART_HEIGHT = 600;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        Files.createDirectories(Paths.get(tempDir.toString()));
    }

    @Test
    public void createSpeedCounter() throws IOException {
        SpeedCounter counter = new SpeedCounter();
        counter.setValue(120);
        counter.setBackgroundColor(new Color(119, 181, 254));
        String filePath = tempDir + File.separator + "speed-counter.png";

        // create chart
        counter.saveChartAsPNG(filePath, 350, 350);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

}
