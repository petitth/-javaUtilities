package edu.collection;

import edu.bean.Address;
import edu.bean.Employee;
import edu.bean.Person;
import edu.bean.PersonWithAdr;
import edu.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class CollectionTest {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionTest.class);

    private List<Person> personList;
    private List<PersonWithAdr> personWithAdrList;
    private Person[] personArray;

    @BeforeEach
    public void setup() {
        personList = Arrays.asList(
                new Person("John", "Smith"),
                new Person("Anna", "Martinez"),
                new Person("Paul", "Watson"),
                new Person("Jane", "Smith")
        );
        personArray = new Person[]{
                new Person("Anna", "Martinez"),
                new Person("Paul", "Watson"),
                new Person("John", "Smith")
        };

        List<Address> addressListSmith = Arrays.asList(
                new Address("57","Obere Str.","12209","Berlin","Germany"),
                new Address("2222","Avda. de la Constituciùn","05021","Mexico","Mexico")
        );
        List<Address> addressListMartinez = Arrays.asList(
                new Address("2312","Mataderos","05023","Mexico","Mexico"),
                new Address("120","Hanover Sq","WA1 1DP","London","UK")
        );
        List<Address> addressListWatson = Arrays.asList(
                new Address("8","Berguvsvègen","S-958 22","Luleî","Sweden")
        );
        personWithAdrList = Arrays.asList(
                new PersonWithAdr("John", "Smith", addressListSmith),
                new PersonWithAdr("Anna", "Martinez", addressListMartinez),
                new PersonWithAdr("Paul", "Watson", addressListWatson)
        );
    }

    @Test
    public void streamTest() throws Exception {
        // from List<Person> to List<String>
        List<String> firstNameList = personList.stream().map(Person::getFirstName).collect(Collectors.toList());

        // from List<Person> to List<String>
        List<String> firstNameUpperList = personList.stream().map(Person::getFirstName).map(x -> x.toUpperCase()).collect(Collectors.toList());

        // from List<String> to List<String
        List<String> firstNameUpperV2List = firstNameList.stream().map(x -> x.toUpperCase()).collect(Collectors.toList());

        // display a list
        firstNameUpperList.forEach(x -> LOG.info(x));

        // from List<Person> to String (join items)
        String joinedLastNames = personList.stream().map(Person::getLastName).collect(Collectors.joining(","));
        assertEquals("Smith,Martinez,Watson,Smith", joinedLastNames);
        String joinedNamesWithQuotes = personList.stream().map(x -> "'"+x.getLastName()+"'").collect(Collectors.joining(","));
        assertEquals("'Smith','Martinez','Watson','Smith'", joinedNamesWithQuotes);

        // retrieve one element from list
        Person john = personList.stream().filter(x -> x.getFirstName().equals("John")).findAny().orElse(null);
        assertNotNull(john);
        Person bob = personList.stream().filter(x -> x.getFirstName().equals("Bob")).findAny().orElse(null);
        assertNull(bob);
    }

    @Test
    public void twoLoopsTestA() {
        personWithAdrList.forEach(person -> person.getAddressList()
                .forEach(address -> LOG.info("person '{}' : address = {}", person, address.toString()))
        );
        assertFalse(personWithAdrList.isEmpty(), "make sonar happy");
    }

    @Test
    public void twoLoopsTestB() {
        personWithAdrList.stream()
                .flatMap(person -> person.getAddressList().stream())
                .map(Address::toString)
                .forEach(LOG::info);
        assertFalse(personWithAdrList.isEmpty(), "make sonar happy");
    }

    @Test
    public void twoLoopsTestC() {
        personWithAdrList
                .stream()
                .flatMap(person -> person.getAddressList().stream())
                .map(Address::getCity)
                .forEach(LOG::info);
        assertFalse(personWithAdrList.isEmpty(), "make sonar happy");
    }

    @Test
    public void streamEqualsTest() {
        assertTrue(personList.stream().anyMatch(person -> person.getLastName().equals("Smith") || person.getLastName().equals("Martinez")));
        assertFalse(personList.stream().anyMatch(person -> person.getLastName().equals("foo")));
    }

    @Test
    public void streamCountTest() {
        assertEquals(4, personList.stream().count());
        assertEquals(3, personList.stream().filter(person -> person.getLastName().equals("Smith") || person.getLastName().equals("Martinez")).count());
        assertEquals(2, personList.stream().filter(person -> person.getLastName().equals("Smith")).count());
    }

    @Test
    public void filterList() {
        List<Person> filtered = personList.stream().filter(person -> person.getLastName().equals("Smith")).collect(Collectors.toList());
        assertEquals(2, filtered.size());
        assertEquals("Smith", filtered.get(0).getLastName());
        assertEquals("Smith", filtered.get(1).getLastName());
    }

    @Test
    public void convertToArrayTest() throws Exception {
        // from list to array
        String[] stringArray = personList.stream().map(Person::toString).toArray(String[]::new);
        assertEquals("John Smith", stringArray[0]);
        assertEquals(personList.size(), stringArray.length);

        // from list to array with a filter
        String[] stringSubArray = personList.stream().filter(x -> x.getLastName().length() > 5).map(Person::getLastName).toArray(String[]::new);
        assertEquals(2, stringSubArray.length);

        // create an array of beans without stream() [recommended way]
        Person[] personArray = personList.toArray(new Person[0]);
        assertEquals(personArray.length, personList.size());
        assertEquals("Smith", personArray[0].getLastName());
        assertEquals("Martinez", personArray[1].getLastName());
        assertEquals("Watson", personArray[2].getLastName());

        // create an array of beans without stream() v2
        Person[] personArray2 = new Person[personList.size()];
        personList.toArray(personArray2);
        assertEquals(personArray2.length, personList.size());
        assertEquals("Smith", personArray2[0].getLastName());
        assertEquals("Martinez", personArray2[1].getLastName());
        assertEquals("Watson", personArray2[2].getLastName());
    }

    @Test
    public void sortBeans() {
        // sort beans on first name attribute
        Collections.sort(personList, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getFirstName().compareTo(p2.getFirstName());
            }
        });
        assertEquals("Anna", personList.get(0).getFirstName());
        assertEquals("Jane", personList.get(1).getFirstName());
        assertEquals("John", personList.get(2).getFirstName());
        assertEquals("Paul", personList.get(3).getFirstName());
    }

    @Test
    public void sortBeansWithLambdaExpressions() {
        // sort beans on first name attribute
        personList.sort(Comparator.comparing(Person::getFirstName));
        assertEquals("Anna", personList.get(0).getFirstName());
        assertEquals("Jane", personList.get(1).getFirstName());
        assertEquals("John", personList.get(2).getFirstName());
        assertEquals("Paul", personList.get(3).getFirstName());
    }

    @Test
    public void sortOnlyInALoop() {
        List<Person> sortedList = new ArrayList<>();
        personList.stream()
                .sorted(Comparator.comparing(Person::getLastName)
                        .thenComparing(Person::getFirstName))
                .forEach(sortedList::add);

        assertEquals("Anna Martinez", sortedList.get(0).toString());
        assertEquals("Jane Smith", sortedList.get(1).toString());
        assertEquals("John Smith", sortedList.get(2).toString());
        assertEquals("Paul Watson", sortedList.get(3).toString());

        // original list remained the same
        assertEquals("John Smith", personList.get(0).toString());
        assertEquals("Anna Martinez", personList.get(1).toString());
        assertEquals("Paul Watson", personList.get(2).toString());
        assertEquals("Jane Smith", personList.get(3).toString());
    }

    @Test
    public void containsCaseInsensitive() {
        List<String> list = Arrays.asList("FOO", "BAR");
        String searchedText = "fOo";
        assertTrue(list.stream().anyMatch(searchedText::equalsIgnoreCase));
    }

    @Test
    public void arrayToList() {
        List<Person> list = Arrays.asList(personArray);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(3, list.size());
        assertEquals("Anna", list.get(0).getFirstName());
        assertEquals("Paul", list.get(1).getFirstName());
        assertEquals("John", list.get(2).getFirstName());
    }

    @Test
    public void listReplaceA() {
        List<String> stringList = new ArrayList<>();
        stringList.add("winter is coming ");
        stringList.add("winter is here ");
        stringList.replaceAll(String::trim);
        stringList.replaceAll(String::toUpperCase);
        assertNotNull(stringList);
        assertEquals("WINTER IS COMING", stringList.get(0));
        assertEquals("WINTER IS HERE", stringList.get(1));
    }

    @Test
    public void listReplaceB() {
        List<String> stringList = new ArrayList<>();
        stringList.add("winter is coming");
        stringList.add("winter is here");
        stringList.replaceAll(str -> str.toUpperCase());
        assertNotNull(stringList);
        assertEquals("WINTER IS COMING", stringList.get(0));
        assertEquals("WINTER IS HERE", stringList.get(1));
    }

    @Test
    public void getSumWithStream() {
        List<Employee> employees = TestUtil.getEmployeeList();
        double sum = employees.stream().mapToDouble(x -> x.getSalary()).sum();
        assertEquals(40500.0, sum);
    }

    @Test
    public void removeDuplicatesFromList() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("John", "Smith"));
        list.add(new Person("Anna", "Martinez"));
        list.add(new Person("Paul", "Watson"));
        list.add(new Person("Jane", "Smith"));
        list.add(new Person("John", "Smith"));
        list.add(new Person("Anna", "Martinez"));
        assertEquals(6, list.size());

        List<Person> filteredList = list.stream()
                .distinct()
                .collect(Collectors.toList());
        assertEquals(4, filteredList.size());
    }

    @Test
    public void removeDuplicatesFromListAlt() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("John", "Smith"));
        list.add(new Person("Anna", "Martinez"));
        list.add(new Person("Paul", "Watson"));
        list.add(new Person("Jane", "Smith"));
        list.add(new Person("John", "Smith"));
        list.add(new Person("Anna", "Martinez"));
        assertEquals(6, list.size());

        List<Person> filteredList = new ArrayList<>(new LinkedHashSet<>(list));
        assertEquals(4, filteredList.size());
    }


    @Test
    public void concatLists() {
        List<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("green");
        colors.add("blue");

        List<String> newList = Stream.concat(Stream.of("foo"), colors.stream())
                                     .collect(Collectors.toList());

        assertNotNull(newList);
        assertEquals(4, newList.size());
        assertEquals("foo", newList.get(0));
        assertEquals("red", newList.get(1));
        assertEquals("green", newList.get(2));
        assertEquals("blue", newList.get(3));
    }

    @Test
    public void concatListsB() {
        List<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("green");
        colors.add("blue");

        List<String> newList = new ArrayList<>();
        newList.add("foo");
        colors.forEach(color -> newList.add(color));
        //colors.stream().forEachOrdered(newList::add);

        assertNotNull(newList);
        assertEquals(4, newList.size());
        assertEquals("foo", newList.get(0));
        assertEquals("red", newList.get(1));
        assertEquals("green", newList.get(2));
        assertEquals("blue", newList.get(3));
    }

    @Test
    public void concatListsC() {
        List<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("green");
        colors.add("blue");

        List<String> newList = new ArrayList<>();
        newList.add("foo");
        newList.addAll(colors);

        assertNotNull(newList);
        assertEquals(4, newList.size());
        assertEquals("foo", newList.get(0));
        assertEquals("red", newList.get(1));
        assertEquals("green", newList.get(2));
        assertEquals("blue", newList.get(3));
    }
}