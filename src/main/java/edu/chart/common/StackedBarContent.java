package edu.chart.common;

public class StackedBarContent {

    private final String domainValue;
    private final double rangeValue;
    private final String legend;

    public StackedBarContent(String legend, String domainValue, double rangeValue) {
        this.domainValue = domainValue;
        this.rangeValue = rangeValue;
        this.legend = legend;
    }

    public String getDomainValue() {
        return domainValue;
    }

    public double getRangeValue() {
        return rangeValue;
    }

    public String getLegend() {
        return legend;
    }
}
