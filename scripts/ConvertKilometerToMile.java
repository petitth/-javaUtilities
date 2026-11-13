public class ConvertKilometerToMile {
    private static Double kmDistance;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a distance to convert !");
            System.exit(1);
        }

        try {
            kmDistance = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input distance is invalid !");
            System.exit(2);
        }

        double mileDistance = kilometerToMile(kmDistance);
        System.out.println(String.format("%4.1f kilometers = %4.1f miles", kmDistance, mileDistance));

        System.out.println("-- end --");
        System.exit(0);
    }

    private static double kilometerToMile(double kilometers) {
        return kilometers * 0.6213711922;
    }
}
