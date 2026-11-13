public class ConvertCentimeterToInch {
    private static Double cmMeasure;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a measure to convert !");
            System.exit(1);
        }

        try {
            cmMeasure = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input measure is invalid !");
            System.exit(2);
        }

        double inchMeasure = centimeterToInch(cmMeasure);
        System.out.println(String.format("%4.2f centimeters = %4.2f inches", cmMeasure, inchMeasure));

        System.out.println("-- end --");
        System.exit(0);
    }

    private static double centimeterToInch(double cm) {
        return cm / 2.54;
    }
}
