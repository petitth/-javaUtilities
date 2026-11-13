package edu.chart;

import edu.chart.common.AbstractChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;
import java.util.Map;

public class BarChart extends AbstractChart {

    private final List<Map<String, Number>> chartContent;
    private final String axisLabel;
    private final String categoryLabel;

    public BarChart(String chartTitle, List<Map<String, Number>> chartContent, String axisLabel, String categoryLabel) {
        this.chartTitle = chartTitle;
        this.chartContent = chartContent;
        this.axisLabel = axisLabel;
        this.categoryLabel = categoryLabel;
    }

    @Override
    protected JFreeChart createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map<String, Number> map : chartContent) {
            for (Map.Entry<String, Number> entry : map.entrySet()) {
                dataset.setValue(entry.getValue(), entry.getKey(), "");
            }
        }

        return ChartFactory.createBarChart(
                    chartTitle,
                    categoryLabel,
                    axisLabel,
                    dataset,
                    PlotOrientation.VERTICAL,
                    includeLegend,
                    true,
                    false);
    }

}