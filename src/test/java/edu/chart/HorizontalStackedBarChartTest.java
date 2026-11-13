package edu.chart;

import edu.chart.common.StackedBarContent;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HorizontalStackedBarChartTest {
    @TempDir
    Path tempDir;

    private static final Logger LOG = LoggerFactory.getLogger(HorizontalStackedBarChartTest.class);
    private final int CHART_WIDTH = 800;
    private final int CHART_HEIGHT = 600;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        Files.createDirectories(Paths.get(tempDir.toString()));
    }

    @Test
    public void createStackedBarChart() throws IOException {
        String chartTitle = "Population Chart Demo";
        String domainAxisLabel = "Age Group";
        String rangeAxisLabel = "Population (millions)";
        List<StackedBarContent> data = new ArrayList<>();
        data.add(new StackedBarContent("Male", "70+", -6.0));
        data.add(new StackedBarContent("Male", "60-69", -8.0));
        data.add(new StackedBarContent("Male", "50-59", -11.0));
        data.add(new StackedBarContent("Male", "40-49", -13.0));
        data.add(new StackedBarContent("Male", "30-39", -14.0));
        data.add(new StackedBarContent("Male", "20-29", -15.0));
        data.add(new StackedBarContent("Male", "10-19", -19.0));
        data.add(new StackedBarContent("Male", "0-9", -21.0));
        data.add(new StackedBarContent("Female", "70+", 10.0));
        data.add(new StackedBarContent("Female", "60-69", 12.0));
        data.add(new StackedBarContent("Female", "50-59", 13.0));
        data.add(new StackedBarContent("Female", "40-49", 14.0));
        data.add(new StackedBarContent("Female", "30-39", 15.0));
        data.add(new StackedBarContent("Female", "20-29", 17.0));
        data.add(new StackedBarContent("Female", "10-19", 19.0));
        data.add(new StackedBarContent("Female", "0-9", 20.0));

        HorizontalStackedBarChart chart = new HorizontalStackedBarChart(chartTitle, domainAxisLabel, rangeAxisLabel, data);
        String filePath = tempDir + File.separator + "horizontal-stacked-chart.png";
        chart.saveChartAsPNG(filePath, CHART_WIDTH, CHART_HEIGHT);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createStackedBarChartWithByteArray() throws IOException {
        String chartTitle = "Population Chart Demo";
        String domainAxisLabel = "Age Group";
        String rangeAxisLabel = "Population (millions)";
        List<StackedBarContent> data = new ArrayList<>();
        data.add(new StackedBarContent("Male", "70+", -6.0));
        data.add(new StackedBarContent("Male", "60-69", -8.0));
        data.add(new StackedBarContent("Male", "50-59", -11.0));
        data.add(new StackedBarContent("Male", "40-49", -13.0));
        data.add(new StackedBarContent("Male", "30-39", -14.0));
        data.add(new StackedBarContent("Male", "20-29", -15.0));
        data.add(new StackedBarContent("Male", "10-19", -19.0));
        data.add(new StackedBarContent("Male", "0-9", -21.0));
        data.add(new StackedBarContent("Female", "70+", 10.0));
        data.add(new StackedBarContent("Female", "60-69", 12.0));
        data.add(new StackedBarContent("Female", "50-59", 13.0));
        data.add(new StackedBarContent("Female", "40-49", 14.0));
        data.add(new StackedBarContent("Female", "30-39", 15.0));
        data.add(new StackedBarContent("Female", "20-29", 17.0));
        data.add(new StackedBarContent("Female", "10-19", 19.0));
        data.add(new StackedBarContent("Female", "0-9", 20.0));

        HorizontalStackedBarChart chart = new HorizontalStackedBarChart(chartTitle, domainAxisLabel, rangeAxisLabel, data);
        String filePath = tempDir + File.separator + "horizontal-stacked-chart-v2.png";
        byte[] chartContent = chart.getChartAsPNG(CHART_WIDTH, CHART_HEIGHT);
        Files.write(Paths.get(filePath), chartContent);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createStackedBarChartNo3dWithByteArray() throws IOException {
        String chartTitle = "Population Chart Demo";
        String domainAxisLabel = "Age Group";
        String rangeAxisLabel = "Population (millions)";
        List<StackedBarContent> data = new ArrayList<>();
        data.add(new StackedBarContent("Male", "70+", -6.0));
        data.add(new StackedBarContent("Male", "60-69", -8.0));
        data.add(new StackedBarContent("Male", "50-59", -11.0));
        data.add(new StackedBarContent("Male", "40-49", -13.0));
        data.add(new StackedBarContent("Male", "30-39", -14.0));
        data.add(new StackedBarContent("Male", "20-29", -15.0));
        data.add(new StackedBarContent("Male", "10-19", -19.0));
        data.add(new StackedBarContent("Male", "0-9", -21.0));
        data.add(new StackedBarContent("Female", "70+", 10.0));
        data.add(new StackedBarContent("Female", "60-69", 12.0));
        data.add(new StackedBarContent("Female", "50-59", 13.0));
        data.add(new StackedBarContent("Female", "40-49", 14.0));
        data.add(new StackedBarContent("Female", "30-39", 15.0));
        data.add(new StackedBarContent("Female", "20-29", 17.0));
        data.add(new StackedBarContent("Female", "10-19", 19.0));
        data.add(new StackedBarContent("Female", "0-9", 20.0));

        HorizontalStackedBarChart chart = new HorizontalStackedBarChart(chartTitle, domainAxisLabel, rangeAxisLabel, data);
        chart.setUseLegacyTheme(true);
        chart.setMoveRangeAxisLocation(true);
        chart.addColor(new Color(0, 105, 179));
        chart.addColor(new Color(183, 21, 130));

        String filePath = tempDir + File.separator + "horizontal-stacked-chart-no-3d.png";
        byte[] chartContent = chart.getChartAsPNG(CHART_WIDTH, CHART_HEIGHT);
        Files.write(Paths.get(filePath), chartContent);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }
}
