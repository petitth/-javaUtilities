public class ConvertInchToCentimeter {

    private static Double inchMeasure;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a measure to convert !");
            System.exit(1);
        }

        try {
            inchMeasure = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input measure is invalid !");
            System.exit(2);
        }

        double centimeterMeasure = inchToCentimeter(inchMeasure);
        System.out.println(String.format("%4.2f inches = %4.2f centimeters", inchMeasure, centimeterMeasure));

        System.out.println("-- end --");
        System.exit(0);
    }

    private static double inchToCentimeter(double inch) {
        return inch * 2.54;
    }
}
