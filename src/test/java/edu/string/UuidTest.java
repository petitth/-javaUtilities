package edu.string;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UuidTest {

    private static final Logger LOG = LoggerFactory.getLogger(UuidTest.class);

    @Test
    public void basics() {
        UUID randomUUID = UUID.randomUUID();
        LOG.debug("random UUID = '{}'", randomUUID);
        assertNotNull(randomUUID);

        UUID someUUID = UUID.fromString("36f46476-e7a8-4074-8bed-3af67e13ebf8");
        assertNotNull(someUUID);
    }

}
