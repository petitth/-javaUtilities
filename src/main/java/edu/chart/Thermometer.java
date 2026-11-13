package edu.chart;

import edu.chart.common.AbstractChart;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.chart.ui.GradientPaintTransformType;
import org.jfree.chart.ui.StandardGradientPaintTransformer;
import org.jfree.data.general.DefaultValueDataset;

import java.awt.*;

/**
 * This chart uses a dial plot to create a thermometer, based Dial Demo 1.
 */
public class Thermometer extends AbstractChart {

    private final DefaultValueDataset dataset;

    private static final double RANGE_LOWER = -40D;		    // lowest temperature
    private static final double RANGE_UPPER = 60D;			// highest temperature
    private static final double MAJOR_TICK_INCREMENT = 10D;
    private static final int    MINOR_TICK_COUNT = 4;
    private static final Color  POINTER_COLOR = Color.GRAY;
    private static final String TEXT_ANNOTATION = "Temperature";

    public Thermometer(String chartTitle, double chartValue) {
        this.chartTitle = chartTitle;
        this.dataset = new DefaultValueDataset(chartValue);
    }

    @Override
    protected JFreeChart createChart() {
        DialPlot dialplot = new DialPlot();
        dialplot.setDataset(dataset);
        dialplot.setDialFrame(new StandardDialFrame());
        dialplot.setBackground(new DialBackground());

        // annotation at the bottom of chart
        DialTextAnnotation dialtextannotation = new DialTextAnnotation(TEXT_ANNOTATION);
        dialtextannotation.setFont(new Font("Dialog", 1, 14));
        dialtextannotation.setRadius(0.69999999999999996D);
        dialplot.addLayer(dialtextannotation);

        // current value displayed below pointer
        DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
        dialplot.addLayer(dialvalueindicator);

        // chart content
        StandardDialScale standardDialScale = new StandardDialScale(RANGE_LOWER, RANGE_UPPER, -120D, -300D, MAJOR_TICK_INCREMENT, MINOR_TICK_COUNT);
        standardDialScale.setTickRadius(0.88D);
        standardDialScale.setTickLabelOffset(0.14999999999999999D);
        standardDialScale.setTickLabelFont(new Font("Dialog", 0, 14));
        standardDialScale.setTickLabelPaint(Color.black);
        dialplot.addScale(0, standardDialScale);
        dialplot.addPointer(new org.jfree.chart.plot.dial.DialPointer.Pin());
        DialCap dialcap = new DialCap();
        dialplot.setCap(dialcap);

        // inner part of the chart with green/orange/red
        StandardDialRange dialRangeGreen = new StandardDialRange(-40D, 10D, Color.green);
        dialRangeGreen.setInnerRadius(0.52000000000000002D);
        dialRangeGreen.setOuterRadius(0.55000000000000004D);
        StandardDialRange dialRangeOrange = new StandardDialRange(10D, 40D, Color.orange);
        dialRangeOrange.setInnerRadius(0.52000000000000002D);
        dialRangeOrange.setOuterRadius(0.55000000000000004D);
        StandardDialRange dialRangeRed = new StandardDialRange(40D, 60D, Color.red);
        dialRangeRed.setInnerRadius(0.52000000000000002D);
        dialRangeRed.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(dialRangeGreen);
        dialplot.addLayer(dialRangeOrange);
        dialplot.addLayer(dialRangeRed);

        // background color
        GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
        DialBackground dialbackground = new DialBackground(gradientpaint);
        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);
        dialplot.removePointer(0);

        // the needle (called pointer)
        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer();
        pointer.setFillPaint(POINTER_COLOR);
        dialplot.addPointer(pointer);

        return new JFreeChart(chartTitle, dialplot);
    }

}