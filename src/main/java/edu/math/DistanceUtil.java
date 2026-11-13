package edu.math;

public class DistanceUtil {

    private DistanceUtil() { }

    public static double centimeterToFeet(double cm) {
        return cm / 30.48;
    }

    public static double centimeterToInch(double cm) {
        return cm / 2.54;
    }

    public static double feetToCentimeter(double feet) {
        return feet * 30.48;
    }

    public static double inchToCentimeter(double inch) {
        return inch * 2.54;
    }

    public static double kilometerToMile(double kilometer) {
        return kilometer * 0.6213711922;
    }

    public static double mileToKilometer(double mile) {
        return mile * 1.609344;
    }

    public static double yardToMeter(double yard) {
        return yard * 0.9144;
    }

    public static double meterToYard(double meter) {
        return meter * 1.0936132983;
    }

}