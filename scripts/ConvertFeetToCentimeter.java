public class ConvertFeetToCentimeter {
    private static Double feetMeasure;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a measure to convert !");
            System.exit(1);
        }

        try {
            feetMeasure = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input measure is invalid !");
            System.exit(2);
        }

        double centimeterMeasure = feetToCentimeter(feetMeasure);
        System.out.println(String.format("%4.2f feet = %4.2f centimeters", feetMeasure, centimeterMeasure));

        System.out.println("-- end --");
        System.exit(0);
    }

    private static double feetToCentimeter(double feet) {
        return feet * 30.48;
    }
}
