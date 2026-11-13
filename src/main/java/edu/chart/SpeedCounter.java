package edu.chart;

import edu.chart.common.AbstractChart;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SpeedCounter extends AbstractChart {

    private final DefaultValueDataset dataset;
    private Color backgroundColor;

    private static final double DEFAULT_VALUE_DATASET = 0D; // default 0 km/h
    private static final double	RANGE_LOWER = 0;			// from 0 km/h
    private static final double RANGE_UPPER = 220;			// to 220 km/h
    private static final Color OUTLINE_PAINT = new Color(206,206,206); 	// argent
    private static final Color TICK_LABEL_PAINT = new Color(255,255,255); 	// blanc
    private static final Color NEEDLE_PAINT = new Color(150,0,24);			// carmin
    private static final Color BACKGROUND_COLOR = new Color(48,48,48); 	// anthracite

    public SpeedCounter() {
        this.backgroundColor = null;
        this.chartTitle = null;
        this.dataset = new DefaultValueDataset(DEFAULT_VALUE_DATASET);
    }

    @Override
    protected JFreeChart createChart() {
        CustomMeterPlot plot = new CustomMeterPlot(dataset);
        plot.setDialOutlinePaint(OUTLINE_PAINT); // border painting
        plot.setUnits("Km/h");
        plot.setTickLabelsVisible(true);
        plot.setDialShape(DialShape.CIRCLE); // draw whole circle
        plot.setValuePaint(NEEDLE_PAINT);
        plot.setRange(new Range(RANGE_LOWER, RANGE_UPPER));
        plot.setTickLabelPaint(TICK_LABEL_PAINT);
        plot.setNeedlePaint(NEEDLE_PAINT);
        plot.setDialBackgroundPaint(BACKGROUND_COLOR);
        JFreeChart chart = new JFreeChart(chartTitle, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(backgroundColor);
        return chart;
    }

    public void setValue(double xValue) {
        dataset.setValue(xValue);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Extension of MeterPlot with customization.
     * This class shows tick label for every tick as oposed to MeterPlot.
     * @author Abharadia
     * @version Apr 30, 2012 9:47:54 PM
     * @see https://github.com/anilbharadia/jFreeChart-Examples/blob/master/src/CustomMeterPlot.java
     */
    static class CustomMeterPlot extends MeterPlot {

        CustomMeterPlot(ValueDataset dataset) {
            super(dataset);
        }

        @Override
        protected void drawTick(Graphics2D g2, Rectangle2D meterArea, double value) {
            drawTick(g2, meterArea, value, true);
        }
    }
}