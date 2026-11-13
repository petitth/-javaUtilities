package edu.chart;

import edu.chart.common.AbstractChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;
import java.util.Map;

public class LineChart extends AbstractChart {

    private final DefaultCategoryDataset dataset;
    private final String xTitle;
    private final String yTitle;

    public LineChart(String chartTitle, String xTitle, String yTitle) {
        this.chartTitle = chartTitle;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.dataset = new DefaultCategoryDataset();
    }

    public void addLine(String legend, List<Map<String, Number>> lineContent) {
        for (Map<String, Number> map : lineContent) {
            for (Map.Entry<String, Number> entry : map.entrySet()) {
                dataset.addValue(entry.getValue(), legend, entry.getKey());
            }
        }
    }

    @Override
    protected JFreeChart createChart() {
        return ChartFactory.createLineChart(
                chartTitle,	                // chart title
                xTitle,	                    // domain axis label
                yTitle,	                    // range axis label
                dataset,                    // data
                PlotOrientation.VERTICAL,	// orientation
                includeLegend,              // include legend
                true,	                    // tooltips
                false	                    // urls
        );
    }

}