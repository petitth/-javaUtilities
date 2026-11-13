package edu.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemperatureUtilTest {
    @Test
    public void celsiusToFahrenheit() {
        assertEquals(32, TemperatureUtil.celsiusToFahrenheit(0));
        assertEquals(104, TemperatureUtil.celsiusToFahrenheit(40));
        assertEquals(50, TemperatureUtil.celsiusToFahrenheit(10));
        assertEquals(68, TemperatureUtil.celsiusToFahrenheit(20));
        assertEquals(98.6, TemperatureUtil.celsiusToFahrenheit(37));
    }

    @Test
    public void fahrenheitToCelsius() {
        assertEquals(0, TemperatureUtil.fahrenheitToCelsius(32));
        assertEquals(40, TemperatureUtil.fahrenheitToCelsius(104));
        assertEquals(10, TemperatureUtil.fahrenheitToCelsius(50));
        assertEquals(20, TemperatureUtil.fahrenheitToCelsius(68));
        assertEquals(37, TemperatureUtil.fahrenheitToCelsius(98.6));
    }
}