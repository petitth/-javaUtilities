package edu.math;

public class WeightUtil {

    private WeightUtil() { }

    /**
     * Convert a weight in kilograms to pounds.
     * @param kilogram kilograms weight
     * @return converted weight to pounds
     */
    public static double kilogramToPound(double kilogram) {
        return kilogram / 0.45359237;
    }

    /**
     * Convert a weight in pounds to kilograms.
     * @param pound pounds weight
     * @return converted weight to kilograms
     */
    public static double poundToKilogram(double pound) {
        return pound * 0.45359237;
    }

}