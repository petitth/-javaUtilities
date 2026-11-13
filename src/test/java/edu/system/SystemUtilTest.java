package edu.system;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SystemUtilTest {

    private final Logger LOG = LoggerFactory.getLogger(SystemUtilTest.class);

    @Test
    public void launchCommandFromString() throws IOException, InterruptedException {
        String result = SystemUtil.launchCommand("java --version");
        LOG.debug("java version : \n{}", result);
        assertNotNull(result);
    }

    @Test
    public void launchCommandFromArray() throws IOException, InterruptedException {
        String result = SystemUtil.launchCommand(new String[]{"java","--version"});
        LOG.debug("java version : \n{}", result);
        assertNotNull(result);
    }

}
