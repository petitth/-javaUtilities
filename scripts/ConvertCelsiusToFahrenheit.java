public class ConvertCelsiusToFahrenheit {

    private static Double celsiusTemperature;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a temperature to convert !");
            System.exit(1);
        }

        try {
            celsiusTemperature = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input temperature is invalid !");
            System.exit(2);
        }

        double fahrenheitTemperature = celsiusToFahrenheit(celsiusTemperature);
        System.out.println(String.format("%4.1f celsius = %4.1f fahrenheit", celsiusTemperature, fahrenheitTemperature));

        System.out.println("-- end --");
        System.exit(0);
    }

    private static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }
}