package edu.chart;

import edu.common.TestData;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieChartTest {
    @TempDir
    Path tempDir;

    private static final Logger LOG = LoggerFactory.getLogger(PieChartTest.class);
    private final int CHART_WIDTH = 800;
    private final int CHART_HEIGHT = 600;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        Files.createDirectories(Paths.get(tempDir.toString()));
    }

    @Test
    public void createPieChart() throws IOException {
        String chartTitle = "List of the seven continents - current population";
        List<Map<String, Number>> content = TestData.getWorldPopulationByContinentMetricList();
        PieChart chart = new PieChart(chartTitle, content);
        String filePath = tempDir + File.separator + "pie-chart.png";

        // create chart
        chart.saveChartAsPNG(filePath, CHART_WIDTH, CHART_HEIGHT);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createPieChartWithByteArray() throws IOException {
        String chartTitle = "List of the seven continents - current population";
        List<Map<String, Number>> content = TestData.getWorldPopulationByContinentMetricList();
        PieChart chart = new PieChart(chartTitle, content);
        String filePath = tempDir + File.separator + "pie-chart-v2.png";

        // create chart
        byte[] chartContent = chart.getChartAsPNG(CHART_WIDTH, CHART_HEIGHT);
        Files.write(Paths.get(filePath), chartContent);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createPieChartWithCustomColorsAndByteArray() throws IOException {
        String filePath = tempDir + File.separator + "pie-chart-custom-colors.png";
        String chartTitle = "List of the seven continents - current population";
        List<Map<String, Number>> content = TestData.getWorldPopulationByContinentMetricList();
        PieChart chart = new PieChart(chartTitle, content);
        chart.setTransparentLabels(true);
        chart.setUseLegacyTheme(true);
        chart.setIncludeLegend(true);
        chart.addColor("Asia", new Color(255, 204, 1));
        chart.addColor("Africa", new Color(240, 125, 0));
        chart.addColor("Europe", new Color(0, 105, 179));
        chart.addColor("North America", new Color(0 ,150, 63));
        chart.addColor("South america", new Color(183, 21, 130));
        chart.addColor("Australia", new Color(228, 6 ,19));
        chart.addColor("Antarctica", new Color(33, 187, 239));

        // create chart
        byte[] chartContent = chart.getChartAsPNG(CHART_WIDTH, CHART_HEIGHT);
        Files.write(Paths.get(filePath), chartContent);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createPieChart3D() throws IOException {
        String chartTitle = "List of the seven continents - current population";
        List<Map<String, Number>> content = TestData.getWorldPopulationByContinentMetricList();
        PieChart chart = new PieChart(chartTitle, content,true);
        String filePath = tempDir + File.separator + "pie-chart-3d.png";

        // create chart
        chart.saveChartAsPNG(filePath, CHART_WIDTH, CHART_HEIGHT);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createPieChart3DWithCustomLabels() throws IOException {
        String chartTitle = "List of the seven continents - current population";
        List<Map<String, Number>> content = TestData.getWorldPopulationByContinentMetricList();
        PieChart chart = new PieChart(chartTitle, content,true);
        chart.setTransparentLabels(true);
        String filePath = tempDir + File.separator + "pie-chart-3d-custom-labels.png";

        // create chart
        chart.saveChartAsPNG(filePath, CHART_WIDTH, CHART_HEIGHT);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createPieChartCustomLabelColor() throws IOException {
        String chartTitle = "List of the seven continents - current population";
        List<Map<String, Number>> content = TestData.getWorldPopulationByContinentMetricList();
        PieChart chart = new PieChart(chartTitle, content);
        chart.setTransparentLabels(true);
        String filePath = tempDir + File.separator + "pie-chart-custom-label-color.png";

        // create chart
        chart.saveChartAsPNG(filePath, CHART_WIDTH, CHART_HEIGHT);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

}
