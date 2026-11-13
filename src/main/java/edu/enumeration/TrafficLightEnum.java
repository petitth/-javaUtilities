package edu.enumeration;

public enum TrafficLightEnum {

    BLINK(0),
    RED(1),
    ORANGE(2),
    GREEN(3);

    private final int value;

    TrafficLightEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}