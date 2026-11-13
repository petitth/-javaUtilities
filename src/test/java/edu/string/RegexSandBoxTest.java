package edu.string;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexSandBoxTest {
    @Test
    public void replaceA() {
        String html = "rgb(31, 73, 125)this is my message";
        String result = html;
        result = result.replaceAll("rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\)","");
        assertEquals("this is my message", result);
        assertEquals("rgb(31, 73, 125)this is my message", html);
    }

    @Test
    public void replaceB() {
        String html = "\"hello\" rgb(31, 73, 125)this is my message";
        String result = html.replaceAll("rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\)","");
        assertEquals("\"hello\" this is my message", result);
    }

    @Test
    public void replaceC() {
        String html = "<span style=\"color: rgb(31, 73, 125);\">this is my message</span>";
        String result = html.replaceAll("<span style=\"color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("</span>", "");
        assertEquals("this is my message", result);
    }

    @Test
    public void matcherA() {
        assertTrue(checkRegexExpression("rgb(31,73,125)","rgb\\(\\s*(?:(\\d{1,3})\\s*,?){3}\\)" ));
        assertTrue(checkRegexExpression("rgb(31,73,125)","rgb\\(\\s*(?:([0-9]{1,3})\\s*,?){3}\\)" ));
        assertTrue(checkRegexExpression("rgb 31","^[a-zA-Z0-9 ]+$" ));
        assertTrue(checkRegexExpression("rgb(31,73,125)","rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\)" ));
    }

    public static boolean checkRegexExpression(String expression, String regex) {
        int flags = Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(expression);
        return matcher.matches();
    }
}
