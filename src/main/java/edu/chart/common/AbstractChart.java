package edu.chart.common;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public abstract class AbstractChart {

    protected final Color COLOR_LEGACY = new Color(255, 255, 255);  // white
    protected final Color COLOR_DEFAULT = new Color(192, 192, 192); // gray

    protected String chartTitle;
    protected boolean useLegacyTheme = false;
    protected boolean includeLegend = true;


    protected abstract JFreeChart createChart();

    public void saveChartAsPNG(String filePath, int width, int height) throws IOException {
        JFreeChart chart = createChart();
        ChartUtils.saveChartAsPNG(new File(filePath), chart, width, height);
    }

    public byte[] getChartAsPNG(int width, int height) throws IOException {
        JFreeChart chart = createChart();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(baos, chart, width, height);
        return baos.toByteArray();
    }

    public void setUseLegacyTheme(boolean useLegacyTheme) {
        this.useLegacyTheme = useLegacyTheme;
    }

    public void setIncludeLegend(boolean includeLegend) {
        this.includeLegend = includeLegend;
    }
}