package edu.misc;

import java.time.Instant;

public class SandboxUtil {
    private SandboxUtil() { }

    public static Instant getInstant() {
        return Instant.now();
    }

    public static String getLabel(long num) {
        return "foo " + num;
    }
}
