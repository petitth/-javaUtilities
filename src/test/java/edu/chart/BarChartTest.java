package edu.chart;

import edu.common.TestData;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BarChartTest {
    @TempDir
    Path tempDir;

    private static final Logger LOG = LoggerFactory.getLogger(BarChartTest.class);
    private final int CHART_WIDTH = 800;
    private final int CHART_HEIGHT = 600;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        Files.createDirectories(Paths.get(tempDir.toString()));
    }

    @Test
    public void createBarChart() throws IOException {
        String chartTitle = "List of the seven continents - current population";
        String axisLabel = "World population share (%)";
        String categoryLabel = "Continents";
        List<Map<String, Number>> content = TestData.getWorldPopulationByContinentMetricList();
        BarChart chart = new BarChart(chartTitle, content, axisLabel, categoryLabel);
        String filePath = tempDir + File.separator + "bar-chart.png";

        // create chart
        chart.saveChartAsPNG(filePath, CHART_WIDTH, CHART_HEIGHT);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createBarChartWithByteArray() throws IOException {
        String chartTitle = "List of the seven continents - current population";
        String axisLabel = "World population share (%)";
        String categoryLabel = "Continents";
        List<Map<String, Number>> content = TestData.getWorldPopulationByContinentMetricList();
        BarChart chart = new BarChart(chartTitle, content, axisLabel, categoryLabel);
        String filePath = tempDir + File.separator + "bar-chart-v2.png";

        // create chart
        byte[] chartContent = chart.getChartAsPNG(CHART_WIDTH, CHART_HEIGHT);
        Files.write(Paths.get(filePath), chartContent);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

}
