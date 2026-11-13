package edu.chart;

import edu.chart.common.AbstractChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChart extends AbstractChart {

    private final boolean is3D;
    private final List<Map<String, Number>> chartContent;
    private final List<Map<String, Color>> chartColors;
    private boolean transparentLabels;


    public PieChart(String chartTitle, List<Map<String, Number>> chartContent) {
        this.is3D = false;
        this.chartTitle = chartTitle;
        this.chartContent = chartContent;
        this.chartColors = new ArrayList<>();
        this.transparentLabels = false;
    }

    public PieChart(String chartTitle, List<Map<String, Number>> chartContent, boolean is3D) {
        this.is3D = is3D;
        this.chartTitle = chartTitle;
        this.chartContent = chartContent;
        this.chartColors = new ArrayList<>();
        this.transparentLabels = false;
    }

    public void addColor(String value, Color color) {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put(value, color);
        chartColors.add(colorMap);
    }

    @Override
    protected JFreeChart createChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map<String, Number> map : chartContent) {
            for (Map.Entry<String, Number> entry : map.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
        }

        String labelFormat = "{0} = {2}"; // 1 instead of 2 removes the percentage sign
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(labelFormat);

        if (useLegacyTheme) {
            // legacy theme has a white background instead of gray
            ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        }

        JFreeChart chart = null;
        if (is3D) {
            chart = ChartFactory.createPieChart3D(
                    chartTitle,             // chart title
                    dataset,                // data
                    includeLegend,          // include legend
                    true,
                    true
            );

            PiePlot3D plot = (PiePlot3D) chart.getPlot();
            plot.setLabelGenerator(labelGenerator);
            plot.setForegroundAlpha(0.5f);  // half transparent effect
            configureTransparentLabels(plot);

        } else {
            chart = ChartFactory.createPieChart(
                    chartTitle,             // chart title
                    dataset,                // data
                    includeLegend,          // include legend
                    true,
                    true
            );

            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setLabelGenerator(labelGenerator);
            configureTransparentLabels(plot);
        }

        // use custom colors if defined
        for (Map<String, Color> map : chartColors) {
            for (Map.Entry<String, Color> entry : map.entrySet()) {
                ((PiePlot) chart.getPlot()).setSectionPaint(entry.getKey(), entry.getValue());
            }
        }

        return chart;
    }

    private void configureTransparentLabels(PiePlot plot) {
        if (transparentLabels) {
            if (useLegacyTheme) {
                plot.setLabelBackgroundPaint(COLOR_LEGACY);
            } else {
                plot.setLabelBackgroundPaint(COLOR_DEFAULT);
            }

            plot.setLabelOutlinePaint(null); // label border
            plot.setLabelShadowPaint(null);  // label shadow
        }
    }

    public void setTransparentLabels(boolean transparentLabels) {
        this.transparentLabels = transparentLabels;
    }

}