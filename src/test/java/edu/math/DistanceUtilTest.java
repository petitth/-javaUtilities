package edu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceUtilTest {

    @Test
    public void kilometerToMile() {
        assertEquals(0, DistanceUtil.kilometerToMile(0));
        assertEquals(0.621371, MathUtil.round(DistanceUtil.kilometerToMile(1), 6));
        assertEquals(62.137119, MathUtil.round(DistanceUtil.kilometerToMile(100), 6));
    }

    @Test
    public void mileToKilometer() {
        assertEquals(0, DistanceUtil.mileToKilometer(0));
        assertEquals(1.609344, MathUtil.round(DistanceUtil.mileToKilometer(1), 6));
        assertEquals(160.9344, MathUtil.round(DistanceUtil.mileToKilometer(100), 6));
    }

    @Test
    public void centimeterToFeet() {
        assertEquals(0, DistanceUtil.centimeterToFeet(0));
        assertEquals(0.032808, MathUtil.round(DistanceUtil.centimeterToFeet(1), 6));
        assertEquals(3.280840, MathUtil.round(DistanceUtil.centimeterToFeet(100), 6));
    }

    @Test
    public void feetToCentimeter() {
        assertEquals(0, DistanceUtil.feetToCentimeter(0));
        assertEquals(30.48, MathUtil.round(DistanceUtil.feetToCentimeter(1), 6));
        assertEquals(304.8, MathUtil.round(DistanceUtil.feetToCentimeter(10), 6));
    }

    @Test
    public void centimeterToInch() {
        assertEquals(0, DistanceUtil.centimeterToInch(0));
        assertEquals(0.393701, MathUtil.round(DistanceUtil.centimeterToInch(1), 6));
        assertEquals(3.937008, MathUtil.round(DistanceUtil.centimeterToInch(10), 6));
    }

    @Test
    public void inchToCentimeter() {
        assertEquals(0, DistanceUtil.inchToCentimeter(0));
        assertEquals(2.54, MathUtil.round(DistanceUtil.inchToCentimeter(1), 6));
        assertEquals(25.4, MathUtil.round(DistanceUtil.inchToCentimeter(10), 6));
    }

    @Test
    public void yardToMeter() {
        assertEquals(0, DistanceUtil.yardToMeter(0));
        assertEquals(0.9144, MathUtil.round(DistanceUtil.yardToMeter(1), 6));
        assertEquals(9.144, MathUtil.round(DistanceUtil.yardToMeter(10), 6));
    }

    @Test
    public void meterToYard() {
        assertEquals(0, DistanceUtil.meterToYard(0));
        assertEquals(1.093613, MathUtil.round(DistanceUtil.meterToYard(1), 6));
        assertEquals(10.936133, MathUtil.round(DistanceUtil.meterToYard(10), 6));
    }

}