package edu.collection;

import edu.bean.Person;
import edu.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CollectionUtilTest {

    @Test
    public void getPage() {
        List<Person> sourceList = TestUtil.getPersonList();
        List<Person> page1 = CollectionUtil.getPage(sourceList, 1, 4);
        assertEquals(4, page1.size());
        assertEquals("Maria Anders", page1.get(0).toString());
        assertEquals("Victoria Ashworth", page1.get(1).toString());
        assertEquals("Christina Berglung", page1.get(2).toString());
        assertEquals("Frederique Citeaux", page1.get(3).toString());

        List<Person> page2 = CollectionUtil.getPage(sourceList, 2, 4);
        assertEquals(4, page2.size());
        assertEquals("Thomas Hardy", page2.get(0).toString());
        assertEquals("Hanna Moos", page2.get(1).toString());
        assertEquals("Laurence Lebihan", page2.get(2).toString());
        assertEquals("Elisabeth Lincoln", page2.get(3).toString());

        List<Person> page3 = CollectionUtil.getPage(sourceList, 3, 4);
        assertEquals(4, page3.size());
        assertEquals("Antonio Moreno", page3.get(0).toString());
        assertEquals("Patricio Simpson", page3.get(1).toString());
        assertEquals("Martaen Sommer", page3.get(2).toString());
        assertEquals("Ana Trujillo", page3.get(3).toString());
    }

    @Test
    public void getPageSizeInvalid() {
        try {
            CollectionUtil.getPage(TestUtil.getPersonList(),1, -1);
            fail("it cannot work");
        } catch(IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("invalid page size: -1"));
        }
    }

    @Test
    public void getPageInvalid() {
        try {
            CollectionUtil.getPage(TestUtil.getPersonList(),0, 10);
            fail("it cannot work");
        } catch(IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("invalid page: 0"));
        }
    }

    @Test
    public void getPageWrongSourceList() {
        List<Person> personList = CollectionUtil.getPage(null,1, 5);
        assertNotNull(personList);
        assertEquals(0, personList.size());

        personList = CollectionUtil.getPage(TestUtil.getPersonList(),28, 10);
        assertNotNull(personList);
        assertEquals(0, personList.size());
    }
}