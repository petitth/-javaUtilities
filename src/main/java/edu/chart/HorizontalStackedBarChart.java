package edu.chart;

import edu.chart.common.AbstractChart;
import edu.chart.common.StackedBarContent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.general.DefaultKeyedValues2DDataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HorizontalStackedBarChart extends AbstractChart {

    private final List<StackedBarContent> chartContent;
    private final List<Color> chartColors;
    private final String domainAxisLabel;
    private final String rangeAxisLabel;

    private boolean moveRangeAxisLocation = false;

    public HorizontalStackedBarChart(String chartTitle, String domainAxisLabel, String rangeAxisLabel, List<StackedBarContent> chartContent) {
        this.chartTitle = chartTitle;
        this.chartContent = chartContent;
        this.domainAxisLabel = domainAxisLabel;
        this.rangeAxisLabel = rangeAxisLabel;
        this.chartColors = new ArrayList<>();
    }

    public void addColor(Color color) {
        chartColors.add(color);
    }

    @Override
    protected JFreeChart createChart() {
        DefaultKeyedValues2DDataset dataset = new DefaultKeyedValues2DDataset();
        for (StackedBarContent content : chartContent) {
            dataset.addValue(content.getRangeValue(), content.getLegend(), content.getDomainValue());
        }

        if (useLegacyTheme) {
            // legacy theme has a white background instead of gray AND DOES NOT DISPLAY IN 3D
            ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        }

        JFreeChart chart = ChartFactory.createStackedBarChart(
                chartTitle,
                domainAxisLabel,
                rangeAxisLabel,
                dataset,                        // data
                PlotOrientation.HORIZONTAL,
                includeLegend,
                true,                    // tooltips
                false                           // urls
        );

        // use custom colors if defined
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        for (int i=0; i<chartColors.size(); i++) {
            Color color = chartColors.get(i);
            renderer.setSeriesPaint(i, color);
        }

        if (moveRangeAxisLocation) {
            plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        }

        return chart;
    }

    public void setMoveRangeAxisLocation(boolean moveRangeAxisLocation) {
        this.moveRangeAxisLocation = moveRangeAxisLocation;
    }
}
