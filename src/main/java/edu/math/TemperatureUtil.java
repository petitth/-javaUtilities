package edu.math;

public class TemperatureUtil {
    private TemperatureUtil() { }

    /**
     * Convert a temperature in Celsius degrees to Fahrenheit degrees.
     * @param celsius Celsius temperature
     * @return converted temperature in Fahrenheit degrees
     */
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }

    /**
     * Convert a temperature in Fahrenheit degrees to Celsius degrees.
     * @param fahrenheit Fahrenheit temperature
     * @return converted temperature in Celsius degrees
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
}
