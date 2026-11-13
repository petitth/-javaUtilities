package edu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightUtilTest {

    @Test
    public void kilogramToPound() {
        assertEquals(2.204623, MathUtil.round(WeightUtil.kilogramToPound(1), 6));
        assertEquals(4.409245, MathUtil.round(WeightUtil.kilogramToPound(2), 6));
        assertEquals(110.231131, MathUtil.round(WeightUtil.kilogramToPound(50), 6));
    }

    @Test
    public void poundToKilogram() {
        assertEquals(0.453592, MathUtil.round(WeightUtil.poundToKilogram(1), 6));
        assertEquals(0.907185, MathUtil.round(WeightUtil.poundToKilogram(2), 6));
        assertEquals(22.679619, MathUtil.round(WeightUtil.poundToKilogram(50), 6));
    }

}
