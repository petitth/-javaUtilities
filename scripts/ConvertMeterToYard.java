public class ConvertMeterToYard {
    private static Double meterDistance;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a distance to convert !");
            System.exit(1);
        }

        try {
            meterDistance = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input distance is invalid !");
            System.exit(2);
        }

        double yardDistance = meterToYard(meterDistance);
        System.out.println(String.format("%4.1f meters = %4.2f yards", meterDistance, yardDistance));

        System.out.println("-- end --");
        System.exit(0);
    }

    public static double meterToYard(double meter) {
        return meter * 1.0936132983;
    }
}
