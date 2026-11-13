package edu.database;

import edu.exception.SqliteException;
import edu.file.FileUtil;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class SqliteUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(SqliteUtilTest.class);

    @TempDir
    Path tempDir;

    private String tempFolder;
    private String dbPath;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        tempFolder = tempDir.toString() + File.separator + "sqlite-util";
        Files.createDirectories(Paths.get(tempFolder));
    }

    @Test
    public void basicTestWithJson() {
        try {
            // create a database containing customers table
            dbPath = tempFolder + File.separator + "customers.sqlite";
            String sqlCreateCustomers = FileUtil.getFileContent("." + File.separator + "data" + File.separator + "sqlite-customers.sql");
            SqliteUtil.update(dbPath, sqlCreateCustomers);

            // count records
            JSONObject jsCount = ((JSONArray) SqliteUtil.selectToJson(dbPath,"select count(*) as total from customers")).getJSONObject(0);
            int nbRecords = jsCount.getInt("total");
            assertEquals(91, nbRecords);

            // display all of them
            JSONArray jsCustomers = SqliteUtil.selectToJson(dbPath,"select * from customers order by CustomerID");
            //LOG.info("full json :\n{}{}", jsCustomers.toString(), "\n__________________________________________________________\n");
            int i=0;
            LOG.info("customers list :\n--------------");
            while (i<jsCustomers.length()) {
                JSONObject jsCustomer = jsCustomers.getJSONObject(i);
                LOG.info("{} - {}", jsCustomer.getString("CustomerID"), jsCustomer.getString("ContactName"));
                i++;
            }
            assertEquals(nbRecords, i);

            // try a query with no result
            JSONArray jsEmptyArray = SqliteUtil.selectToJson(dbPath,"select * from customers where ContactName = 'Bob'");
            assertEquals(0, jsEmptyArray.length());

        } catch(Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void basicTestWithMap() {
        try {
            // create a database containing customers table
            dbPath = tempFolder + File.separator + "customers.sqlite";
            String sqlCreateCustomers = FileUtil.getFileContent("." + File.separator + "data" + File.separator + "sqlite-customers.sql");
            SqliteUtil.update(dbPath, sqlCreateCustomers);

            // count records
            Map<String, Object> mapCount = SqliteUtil.selectToMap(dbPath,"select count(*) as total from customers").get(0);
            int nbRecords = (int) mapCount.get("total");
            assertEquals(91, nbRecords);

            // display all of them
            List<Map<String, Object>> listCustomers = SqliteUtil.selectToMap(dbPath,"select * from customers order by CustomerID");
            assertEquals(nbRecords, listCustomers.size());

        } catch(Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void executeUpdateWithException() {
        try {
            SqliteUtil.update(tempFolder + File.separator + "foo.sqlite", "select foo");
            fail("it cannot work");
        } catch(SqliteException ex) {
            assertTrue(ex.getMessage().contains("[SQLITE_ERROR] SQL error or missing database"));
        }
    }

    @Test
    public void executeSelectWithJsonException() {
        try {
            SqliteUtil.selectToJson(tempFolder + File.separator + "bar.sqlite", "select bar");
            fail("it cannot work");
        } catch(SqliteException ex) {
            assertTrue(ex.getMessage().contains("[SQLITE_ERROR] SQL error or missing database"));
        }
    }

    @Test
    public void executeSelectWithMapException() {
        try {
            SqliteUtil.selectToMap(tempFolder + File.separator + "bar.sqlite", "select bar");
            fail("it cannot work");
        } catch(SqliteException ex) {
            assertTrue(ex.getMessage().contains("[SQLITE_ERROR] SQL error or missing database"));
        }
    }

    @Test
    public void failConnection() {
        try {
            SqliteUtil.selectToJson(null, "select 1 from dual");
            fail("it cannot work");
        } catch(SqliteException sqliteEx) {
            assertTrue(sqliteEx.getMessage().contains("null"));
        }
    }

}