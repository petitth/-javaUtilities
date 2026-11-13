package edu.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    private static final String REGEX_ONLY_NUMERIC                  = "[0-9]*";
    private static final String REGEX_ONLY_ALPHANUMERIC             = "^[a-zA-Z0-9]*$";
    private static final String REGEX_ONLY_NUMERIC_AND_ALPHANUMERIC = "^([A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]*$";
    private static final String REGEX_ONLY_REPEATING_CHAR           = "[STR]*";

    private RegexUtil() {}

    /**
     * Does an expression respect a regex expression ?
     * @param expression expression to check
     * @param regex regex value
     * @return true if regex is respected, false otherwise
     */
    public static boolean checkRegexExpression(String expression, String regex) {
        int flags = Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(expression);
        return matcher.matches();
    }

    /**
     * Does an expression only contain numeric characters ?
     * @param expression the expression to check
     * @return true if the expression only contains numeric characters, false otherwise
     */
    public static boolean isOnlyNumeric(String expression) {
        return checkRegexExpression(expression, REGEX_ONLY_NUMERIC);
    }

    /**
     * Does an expression only contain alphanumeric characters ?
     * @param expression the expression to check
     * @return true if the expression only contains alphanumeric characters, false otherwise
     */
    public static boolean isOnlyAlphaNumeric(String expression) {
        return checkRegexExpression(expression, REGEX_ONLY_ALPHANUMERIC);
    }

    /**
     * Does an expression only contain alphanumeric and numeric characters (at least one of each type) ?
     * @param expression the expression to check
     * @return true if the expression only contains alphanumeric and numeric characters, false otherwise
     */
    public static boolean isOnlyNumericAndAlphanumeric(String expression) {
        return checkRegexExpression(expression, REGEX_ONLY_NUMERIC_AND_ALPHANUMERIC);
    }

    /**
     * Does an expression contain only a repeating character ?
     * @param expression the expression to check
     * @return true if the expression contains only a repeating character, false otherwise
     */
    public static boolean containsOnlyRepeatingString(String expression, String repeatingStr) {
        return checkRegexExpression(expression, REGEX_ONLY_REPEATING_CHAR.replaceAll("STR", repeatingStr));
    }

}
