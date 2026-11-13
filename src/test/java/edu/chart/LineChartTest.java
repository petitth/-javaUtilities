package edu.chart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static edu.common.TestData.createMap;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LineChartTest {
    @TempDir
    Path tempDir;

    private static final Logger LOG = LoggerFactory.getLogger(LineChartTest.class);
    private final int CHART_WIDTH = 800;
    private final int CHART_HEIGHT = 600;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        Files.createDirectories(Paths.get(tempDir.toString()));
    }

    @Test
    public void createLineChart() throws IOException {
        // chart content
        List<Map<String, Number>> listBelgium = new ArrayList<>();
        listBelgium.add(createMap("2019", BigDecimal.valueOf(11431406)));
        listBelgium.add(createMap("2020", BigDecimal.valueOf(11492641)));

        List<Map<String, Number>> listWallonia = new ArrayList<>();
        listWallonia.add(createMap("2019", BigDecimal.valueOf(3633795)));
        listWallonia.add(createMap("2020", BigDecimal.valueOf(3645243)));

        List<Map<String, Number>> listFlanders = new ArrayList<>();
        listFlanders.add(createMap("2019", BigDecimal.valueOf(6589069)));
        listFlanders.add(createMap("2020", BigDecimal.valueOf(6629143)));

        String chartTitle = "Belgian population";
        String xTitle = "Year";
        String yTitle = "People";

        LineChart lineChart = new LineChart(chartTitle, xTitle, yTitle);
        lineChart.addLine("Belgium", listBelgium);
        lineChart.addLine("Wallonia", listWallonia);
        lineChart.addLine("Flanders", listFlanders);

        // create chart
        String filePath = tempDir + File.separator + "line-chart.png";
        lineChart.saveChartAsPNG(filePath, CHART_WIDTH, CHART_HEIGHT);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void createLineChartWithByteArray() throws IOException {
        // chart content
        List<Map<String, Number>> listBelgium = new ArrayList<>();
        listBelgium.add(createMap("2019", BigDecimal.valueOf(11431406)));
        listBelgium.add(createMap("2020", BigDecimal.valueOf(11492641)));

        List<Map<String, Number>> listWallonia = new ArrayList<>();
        listWallonia.add(createMap("2019", BigDecimal.valueOf(3633795)));
        listWallonia.add(createMap("2020", BigDecimal.valueOf(3645243)));

        List<Map<String, Number>> listFlanders = new ArrayList<>();
        listFlanders.add(createMap("2019", BigDecimal.valueOf(6589069)));
        listFlanders.add(createMap("2020", BigDecimal.valueOf(6629143)));

        String chartTitle = "Belgian population";
        String xTitle = "Year";
        String yTitle = "People";

        LineChart lineChart = new LineChart(chartTitle, xTitle, yTitle);
        lineChart.addLine("Belgium", listBelgium);
        lineChart.addLine("Wallonia", listWallonia);
        lineChart.addLine("Flanders", listFlanders);

        // create chart
        String filePath = tempDir + File.separator + "line-chart-v2.png";
        byte[] chartContent = lineChart.getChartAsPNG(CHART_WIDTH, CHART_HEIGHT);
        Files.write(Paths.get(filePath), chartContent);
        LOG.debug("{} created", filePath);

        // check that file exist
        assertTrue(new File(filePath).exists());
    }

}
