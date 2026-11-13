package edu.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {

    private TestData() { }

    /**
     * List of the seven continents, ranked by current population.
     * source : https://www.worldometers.info/geography/7-continents/
     * @version aug 2020
     * @return a list of maps with continents ranked by population
     */
    public static List<Map<String, Number>> getWorldPopulationByContinentMetricList() {
        List<Map<String, Number>> list = new ArrayList<>();
        list.add(createMap("Asia", 59.54));
        list.add(createMap("Africa", 17.20));
        list.add(createMap("Europe", 9.59));
        list.add(createMap("North America", 7.60));
        list.add(createMap("South america", 5.53));
        list.add(createMap("Australia", 0.55));
        list.add(createMap("Antarctica", 0.0));
        return list;
    }

    public static Map<String, Number> createMap(String key, Number value) {
        Map<String, Number> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static Map<Number, Number> createMap(Number key, Number value) {
        Map<Number, Number> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static List<Map<String, Object>> getEmployees() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(getEmployee("TORTU", "Miguel Angel Paolino", "México D.F.", "Mexico"));
        mapList.add(getEmployee("TRADH", "Anabela Domingues", "Sao Paulo", "Brazil"));
        mapList.add(getEmployee("TRAIH", "Helvetius Nagy", "Kirkland", "USA"));
        mapList.add(getEmployee("VAFFE", "Palle Ibsen", "örhus", "Denmark"));
        mapList.add(getEmployee("VICTE", "Mary Saveley", "Lyon", "France"));
        mapList.add(getEmployee("VINET", "Paul Henriot", "Reims", "France"));
        mapList.add(getEmployee("WANDK", "Rita Müller", "Stuttgart", "Germany"));
        mapList.add(getEmployee("WARTH", "Pirkko Koskitalo", "Oulu", "Finland"));
        mapList.add(getEmployee("WELLI", "Paula Parente", "Resende", "Brazil"));
        mapList.add(getEmployee("WHITC", "Karl Jablonski", "Seattle", "USA"));
        mapList.add(getEmployee("WILMK", "Matti Karttunen", "Helsinki", "Finland"));
        mapList.add(getEmployee("WOLZA", "Zbyszek Piestrzeniewicz", "Warszawa", "Poland"));
        return mapList;
    }

    public static Map<String, Object> getEmployee(String customerID, String contactName, String city, String country) {
        Map<String, Object> map = new HashMap<>();
        map.put("CustomerID", customerID);
        map.put("ContactName", contactName);
        map.put("City", city);
        map.put("Country", country);
        return map;
    }
}