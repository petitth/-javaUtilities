package edu.collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.bean.Customer;
import edu.bean.Day;
import edu.bean.Dummy;
import edu.bean.Person;
import edu.file.FileUtil;
import edu.time.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class JacksonTest {

    private static final Logger LOG = LoggerFactory.getLogger(JacksonTest.class);
    private ObjectMapper objectMapper;

    @TempDir
    Path tempDir;

    private String tempFolder;

    /**
     * This instantiation of ObjectMapper applies a fix to avoid jsr310 exception, faced
     * when working with LocalDate.
     * link : https://stackoverflow.com/questions/28802544/java-8-localdate-jackson-format
     */
    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        tempFolder = tempDir.toString() + File.separator + "jackson-test";
        Files.createDirectories(Paths.get(tempFolder));
        // jackson setup
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * Get a list of beans from a json file.
     * These beans use annotations to map the fields.
     * @see Customer the bean used for this test
     */
    @Test
    public void getListOfBeansFromJson() throws IOException {
        Path path = Paths.get("./src/test/resources/customers.json");
        String jsonCustomers = new String(Files.readAllBytes(path));

        List<Customer> customerList = objectMapper.readValue(jsonCustomers, new TypeReference<List<Customer>>(){});
        assertFalse(customerList.isEmpty());
        assertEquals(91, customerList.size());
    }

    /**
     * Get a list of beans from a json array.
     * This version uses beans without annotations.
     * @throws IOException any exception
     */
    @Test
    public void getListOfBeansFromJsonArray() throws IOException {
        String jsonArray = "[{\"firstName\":\"Anna\",\"lastName\":\"Martinez\"},{\"firstName\":\"John\",\"lastName\":\"Smith\"}]";
        List<Person> list = objectMapper.readValue(jsonArray, new TypeReference<List<Person>>(){});
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
        assertEquals("Anna", list.get(0).getFirstName());
        assertEquals("John", list.get(1).getFirstName());
    }

    /**
     * Get a list of beans from a list of maps.
     * This uses beans without annotations.
     */
    @Test
    public void getListOfBeansFromMaps() {
        Map<String, String> person1 = new HashMap<>();
        person1.put("firstName","Anna");
        person1.put("lastName","Martinez");
        Map<String, String> person2 = new HashMap<>();
        person2.put("firstName","John");
        person2.put("lastName","Smith");
        List<Map<String, String>> mapList = new ArrayList<>();
        mapList.add(person1);
        mapList.add(person2);

        List<Person> personList = mapList.stream()
                                    .map(map -> objectMapper.convertValue(map, Person.class))
                                    .collect(Collectors.toList());
        assertNotNull(personList);
        assertFalse(personList.isEmpty());
        assertEquals(2, personList.size());
        assertEquals("Anna Martinez", personList.get(0).toString());
        assertEquals("John Smith", personList.get(1).toString());
    }

    /**
     * Get one bean from a json string
     * This bean doesn't use any specific annotation.
     * @throws IOException any exception
     */
    @Test
    public void getBeanFromJson() throws IOException {
        Path path = Paths.get("./src/test/resources/person.json");
        String json = new String(Files.readAllBytes(path));

        Person person = objectMapper.readValue(json, Person.class);
        assertNotNull(person);
        assertEquals("Anna", person.getFirstName());
        assertEquals("Martinez", person.getLastName());
    }

    @Test
    public void getBeanFromJsonWithTime() throws IOException {
        // set date format on the object mapper
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        objectMapper.setDateFormat(df);
        // then create the bean from json string
        String json = "{\"id\":\"id1234\",\"date\":\"2020-07-01 08:00\"}";
        Dummy dummy = objectMapper.readValue(json, Dummy.class);
        assertNotNull(dummy);
        assertEquals("id1234", dummy.getId());
        assertEquals(TimeUtil.getDate("yyyy-MM-dd HH:mm","2020-07-01 08:00"), dummy.getDate());
    }

    /**
     * Convert a bean to a json string.
     * This bean doesn't have any specific annotation.
     * @throws JsonProcessingException any json exception
     */
    @Test
    public void getJsonStringFromBean() throws JsonProcessingException {
        Person person = new Person("Anna", "Martinez");
        String result = objectMapper.writeValueAsString(person);
        LOG.debug("bean as json string : {}", result);
        assertEquals("{\"firstName\":\"Anna\",\"lastName\":\"Martinez\"}", result);
    }

    /**
     * Write a bean into a json file.
     * @throws JsonProcessingException any json exception
     * @throws IOException any exception related to file handling
     */
    @Test
    public void getJsonFileFromBeans() throws JsonProcessingException, IOException {
        String jsonPath = tempFolder + File.separator + "person.json";
        Person person = new Person("Anna", "Martinez");
        objectMapper.writeValue(new File(jsonPath), person);
        assertTrue(FileUtil.doesPathExist(jsonPath));
        assertEquals("{\"firstName\":\"Anna\",\"lastName\":\"Martinez\"}", FileUtil.getFileContent(jsonPath));
    }

    @Test
    public void getMapFromJson() throws IOException {
        String json = "{\"firstName\":\"Anna\",\"lastName\":\"Martinez\"}";
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
        assertNotNull(map);
        assertNotNull(map.get("firstName"));
        assertEquals("Anna", map.get("firstName"));
        assertNotNull(map.get("lastName"));
        assertEquals("Martinez", map.get("lastName"));
    }

    @Test
    public void getListOfMapsFromXml() throws IOException {
        Path path = Paths.get("./src/test/resources/person.xml");
        String xml = new String(Files.readAllBytes(path));

        XmlMapper xmlMapper = new XmlMapper();
        List<Map> mapList = xmlMapper.readValue(xml, List.class);
        assertNotNull(mapList);
        assertEquals(2, mapList.size());

        LOG.debug("map list :");
        for (Map map : mapList) {
            LOG.debug("map : firstname = {}, lastname = {}", map.get("firstName"), map.get("lastName"));
        }
    }

    @Test
    public void getListOfBeansFromXml() throws IOException {
        Path path = Paths.get("./src/test/resources/person.xml");
        String xml = new String(Files.readAllBytes(path));

        XmlMapper xmlMapper = new XmlMapper();
        List<Map<String, String>> mapList = xmlMapper.readValue(xml, List.class);
        // conversion is required
        List<Person> personList = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            personList.add(new Person(map.get("firstName"), map.get("lastName")));
        }

        assertNotNull(personList);
        assertEquals(2, personList.size());

        LOG.debug("person list :");
        for (Person person : personList) {
            LOG.debug("person : firstname = {}, lastname = {}", person.getFirstName(), person.getLastName());
        }
    }

    @Test
    public void getOneBeanFromXmlNoConversion() throws JsonProcessingException {
        String xml = "<person>" +
                "    <firstName>Anna</firstName>" +
                "    <lastName>Martinez</lastName>" +
                "</person>";

        XmlMapper xmlMapper = new XmlMapper();
        Person person = xmlMapper.readValue(xml, Person.class);
        assertNotNull(person);
        assertEquals("Anna", person.getFirstName());
        assertEquals("Martinez", person.getLastName());
    }

    @Test
    public void createXmlFromBean() throws IOException {
        // create a list of beans
        Person personA = new Person("Anna", "Martinez");
        Person personB = new Person("John", "Smith");
        List<Person> personList = new ArrayList<>();
        personList.add(personA);
        personList.add(personB);

        // prepare xml creation
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // write beans as xml into a string
        // remark : use tags in bean to specify other nodes names in the xml
        String expected = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<ArrayList>\n" +
                "  <item>\n" +
                "    <firstName>Anna</firstName>\n" +
                "    <lastName>Martinez</lastName>\n" +
                "  </item>\n" +
                "  <item>\n" +
                "    <firstName>John</firstName>\n" +
                "    <lastName>Smith</lastName>\n" +
                "  </item>\n" +
                "</ArrayList>";
        String result = xmlMapper.writeValueAsString(personList).trim();
        assertEquals(expected, result);
        LOG.debug("xml in string : \n{}", result);

        // write bean as xml into a file
        String xmlPath = tempFolder + File.separator + "person.xml";
        File file = new File(xmlPath);
        xmlMapper.writeValue(file, personList);
        assertTrue(new File(xmlPath).exists());
    }

    @Test
    public void getBeanFromJsonWithLocalDate() throws IOException {
        String json = "{\"name\":\"saturday\",\"date\":\"2022-04-09\"}";
        Day day = objectMapper.readValue(json, Day.class);
        assertNotNull(day);
        assertEquals("saturday", day.getName());
        assertEquals(LocalDate.of(2022, 4, 9), day.getDate());
    }

}