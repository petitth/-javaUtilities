package edu.apache.commonsCollections;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * commons-collection is a dependency of POI
 */
public class CollectionUtilsTest {

    List<String> listA;
    List<String> listB;
    List<String> listC;

    @BeforeEach
    public void setup() {
        listA = new ArrayList<>();
        listA.add("red");
        listA.add("green");
        listA.add("blue");
        listA.add("yellow");
        listB = new ArrayList<>();
        listB.add("green");
        listB.add("blue");
        listB.add("pink");
        listC = new ArrayList<>();
        listC.add("black");
        listC.add("orange");
    }

    @Test
    public void intersection() {
        List<String> duplicates = (List<String>) CollectionUtils.intersection(listA, listB);
        Collections.sort(duplicates);
        assertEquals(2, duplicates.size());
        assertEquals("blue", duplicates.get(0));
        assertEquals("green", duplicates.get(1));
    }

    @Test
    public void union() {
        List<String> merged = (List<String>) CollectionUtils.union(listB, listC);
        Collections.sort(merged);
        assertEquals(5, merged.size());
        assertEquals("black", merged.get(0));
        assertEquals("blue", merged.get(1));
        assertEquals("green", merged.get(2));
        assertEquals("orange", merged.get(3));
        assertEquals("pink", merged.get(4));
    }
}