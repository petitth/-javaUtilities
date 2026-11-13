public class ConvertMileToKilometer {
    private static Double mileDistance;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a distance to convert !");
            System.exit(1);
        }

        try {
            mileDistance = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input distance is invalid !");
            System.exit(2);
        }

        double kmDistance = mileToKilometer(mileDistance);
        System.out.println(String.format("%4.1f miles = %4.1f kilometers", mileDistance, kmDistance));

        System.out.println("-- end --");
        System.exit(0);
    }

    private static double mileToKilometer(double miles) {
        return miles * 1.609344;
    }
}
