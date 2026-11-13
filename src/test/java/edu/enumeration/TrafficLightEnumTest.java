package edu.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrafficLightEnumTest {

    @Test
    public void basic() {
        TrafficLightEnum trafficLightEnum = TrafficLightEnum.GREEN;
        assertEquals(3, trafficLightEnum.getValue());

        trafficLightEnum = TrafficLightEnum.ORANGE;
        assertEquals(2, trafficLightEnum.getValue());

        trafficLightEnum = TrafficLightEnum.RED;
        assertEquals(1, trafficLightEnum.getValue());

        trafficLightEnum = TrafficLightEnum.BLINK;
        assertEquals(0, trafficLightEnum.getValue());
    }

}