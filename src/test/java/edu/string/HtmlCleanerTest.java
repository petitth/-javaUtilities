package edu.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class HtmlCleanerTest {

    private HtmlCleaner htmlCleaner;

    @BeforeEach
    public void setup() {
        htmlCleaner = new HtmlCleaner();
    }

    @Test
    public void removeBasicTags() throws Exception {
        String html = "<b>hello</b> <i>world</i>";
        Method method = ReflectionUtils.findMethod(htmlCleaner.getClass(), "removeBasicTags", String.class);
        method.setAccessible(true);
        String result = method.invoke(htmlCleaner, html).toString();
        assertEquals("hello world", result);
    }

    @Test
    public void fullProcess() {
        String html = "<span style=\"color: green\"><b>this is my message</b></span>";
        String text = htmlCleaner.process(html);
        assertEquals("this is my message", text);
    }
}