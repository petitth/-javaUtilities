public class ConvertYardToMeter {
    private static Double yardDistance;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a distance to convert !");
            System.exit(1);
        }

        try {
            yardDistance = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input distance is invalid !");
            System.exit(2);
        }

        double meterDistance = yardToMeter(yardDistance);
        System.out.println(String.format("%4.1f yards = %4.2f meters", yardDistance, meterDistance));

        System.out.println("-- end --");
        System.exit(0);
    }

    private static double yardToMeter(double yard) {
        return yard * 0.9144;
    }
}
