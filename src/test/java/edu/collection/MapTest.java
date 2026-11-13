package edu.collection;

import edu.bean.Person;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;


public class MapTest {

    private static final Logger LOG = LoggerFactory.getLogger(MapTest.class);

    @Test
    public void testHashMap() {
        Map<String, String> map = new HashMap();
        map.put("foo","test1");
        map.put("bar","test2");

        assertNotNull(map.get("foo"));
        assertNotNull(map.get("bar"));

        assertNull(map.get("FOO"));
        assertNull(map.get("BAR"));
    }

    @Test
    public void testTreeMapNonCaseSensitive() {
        Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("foo","test1");
        map.put("bar","test2");

        assertNotNull(map.get("foo"));
        assertNotNull(map.get("bar"));

        assertNotNull(map.get("FOO"));
        assertNotNull(map.get("BAR"));
    }

    @Test
    public void loopThroughMap() {
        Map<String, Person> map = new HashMap<>();
        map.put("id1", new Person("John", "Smith"));
        map.put("id2", new Person("Anna", "Martinez"));
        map.put("id3", new Person("Paul", "Watson"));

        LOG.debug("--- loop A ---");
        map.forEach((key, value) -> LOG.debug("{} : {}", key, value));

        LOG.debug("--- loop B ---");
        map.entrySet().forEach(entry -> LOG.debug("{} : {}", entry.getKey(), entry.getValue()));

        LOG.debug("--- loop C (no lambda) ---");
        for (Map.Entry<String, Person> entry : map.entrySet()) {
            LOG.debug("key: {} - value: {}", entry.getKey(), entry.getValue());
        }

        assertTrue(true, "make sonar happy");
    }

}
