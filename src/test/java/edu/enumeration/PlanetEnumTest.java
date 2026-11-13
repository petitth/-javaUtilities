package edu.enumeration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlanetEnumTest {

    private static final Logger LOG = LoggerFactory.getLogger(PlanetEnumTest.class);

    @Test
    public void getWeight() {
        double earthWeight = 70;
        double mass = earthWeight / PlanetEnum.EARTH.getSurfaceGravity();
        for (PlanetEnum planet : PlanetEnum.values()) {
            assertTrue(planet.surfaceWeight(mass) > 0);
            LOG.debug("weight on {} is {} kg", planet, planet.surfaceWeight(mass));
        }
    }

    @Test
    public void checkEarth() {
        assertEquals(5.975e+24, PlanetEnum.EARTH.getMass());
        assertEquals(6.378e6, PlanetEnum.EARTH.getRadius());
    }
}
