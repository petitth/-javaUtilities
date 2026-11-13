public class ConvertFahrenheitToCelsius {

    private static Double fahrenheitTemperature;

    public static void main(String[] args) {
        System.out.println("-- begin --");
        if (args.length == 0) {
            System.out.println("Please enter a temperature to convert !");
            System.exit(1);
        }

        try {
            fahrenheitTemperature = Double.valueOf(args[0]);
        } catch(NumberFormatException numEx) {
            System.out.println("Input temperature is invalid !");
            System.exit(2);
        }

        double celsiusTemperature = fahrenheitToCelsius(fahrenheitTemperature);
        System.out.println(String.format("%4.1f fahrenheit = %4.1f celsius", fahrenheitTemperature, celsiusTemperature));

        System.out.println("-- end --");
        System.exit(0);
    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
}