package edu.collection;

import edu.bean.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayTest {

    private static final Logger LOG = LoggerFactory.getLogger(ArrayTest.class);

    private Person[] personArray;

    @BeforeEach
    public void setup() {
        personArray = new Person[]{
                new Person("Anna", "Martinez"),
                new Person("Paul", "Watson"),
        };
    }

    @Test
    public void displayArray() {
        assertEquals(2, personArray.length);
        LOG.debug("array : {}", Arrays.toString(personArray));
    }

    @Test
    public void addAnElement() {
        assertEquals(2, personArray.length);
        personArray = Arrays.copyOf(personArray, personArray.length + 1);
        assertNull(personArray[personArray.length - 1]);
        personArray[personArray.length - 1] = new Person("John", "Smith");
        assertNotNull(personArray[2]);
        assertEquals("John", personArray[2].getFirstName());
        assertEquals("Smith", personArray[2].getLastName());
        assertEquals(3, personArray.length);
    }

    @Test
    public void sortArray() {
        // add 2 elements, to have more objects to sort
        personArray = Arrays.copyOf(personArray, personArray.length + 2);
        personArray[2] = new Person("John", "Smith");
        personArray[3] = new Person("Bob", "Razovsky");

        // sort on first name
        Arrays.sort(personArray, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getFirstName().compareTo(p2.getFirstName());
            }
        });

        assertEquals("Anna", personArray[0].getFirstName());
        assertEquals("Bob", personArray[1].getFirstName());
        assertEquals("John", personArray[2].getFirstName());
        assertEquals("Paul", personArray[3].getFirstName());
    }

    /**
     * When trying "Arrays.asList", this instruction will, by default, be mapped to an array,
     * and so will not be modifiable.
     * Using "new ArrayList" makes it a new list, and so can be changed.
     */
    @Test
    public void removeAnElement() {
        // add one element
        personArray = Arrays.copyOf(personArray, personArray.length + 1);
        personArray[2] = new Person("John", "Smith");

        // will remove second element, using a temp list
        int iToRemove = 1;
        List<Person> personList = new ArrayList(Arrays.asList(personArray));
        personList.remove(iToRemove);
        personArray = personList.toArray(new Person[0]);

        // validate
        assertEquals(2, personArray.length);
        assertEquals("Anna", personArray[0].getFirstName());
        assertEquals("John", personArray[1].getFirstName());
    }

    @Test
    public void removeAnElementAlternate() {
        // add one element
        personArray = Arrays.copyOf(personArray, personArray.length + 1);
        personArray[2] = new Person("John", "Smith");

        // will remove second element, using a temp list
        int iToRemove = 1;
        List<Person> personList = new ArrayList(Arrays.asList(personArray));
        personList.remove(iToRemove);

        // here comes the change
        personArray = new Person[personList.size()];
        personList.toArray(personArray);

        // validate
        assertEquals(2, personArray.length);
        assertEquals("Anna", personArray[0].getFirstName());
        assertEquals("John", personArray[1].getFirstName());
    }

}
