package edu.database;

import edu.exception.SqliteException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteUtil {

    private SqliteUtil() {}

    /**
     * Get a Connection object.
     * @param dbPath database path
     * @return a Connection
     * @throws SqliteException any exception
     */
    private static Connection getConnection(String dbPath) throws SqliteException {
        try {
            String url = (dbPath != null) ? "jdbc:sqlite:".concat(dbPath) : null;
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch(ClassNotFoundException | SQLException ex) {
            throw new SqliteException(ex.getMessage());
        }
    }

    /**
     * Execute an insert/update/delete query on a Sqlite database.
     * @param dbPath database path
     * @param sql update query
     * @throws SqliteException any exception
     */
    public static void update(String dbPath, String sql) throws SqliteException {
        try (Connection conn = getConnection(dbPath)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch(SQLException ex) {
            throw new SqliteException(ex.getMessage());
        }
    }

    /**
     * Execute a select query on a Sqlite database.
     * @param dbPath database path
     * @param sql select query
     * @return org.json.JSONArray
     * @throws SqliteException any exception
     */
    public static JSONArray selectToJson(String dbPath, String sql) throws SqliteException {
        try(Connection conn = getConnection(dbPath)) {
            JSONArray jsonResult = new JSONArray();
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        JSONObject jsonLine = new JSONObject();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                            String columnName = rsmd.getColumnName(i);
                            jsonLine.put(columnName, rs.getObject(columnName));
                        }
                        jsonResult.put(jsonLine);
                    }

                    return jsonResult;
                }
            }

        } catch(SQLException | SqliteException ex) {
            throw new SqliteException(ex.getMessage());
        }
    }

    /**
     * Execute a select query on a Sqlite database.
     * @param dbPath database path
     * @param sql select query
     * @return a list of maps
     * @throws SqliteException any exception
     */
    public static List<Map<String, Object>> selectToMap(String dbPath, String sql) throws SqliteException {
        try(Connection conn = getConnection(dbPath)) {
            List<Map<String, Object>> listResult = new ArrayList();
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        Map<String, Object> map = new HashMap<>();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                            String columnName = rsmd.getColumnName(i);
                            map.put(columnName, rs.getObject(columnName));
                        }
                        listResult.add(map);
                    }

                    return listResult;
                }
            }

        } catch(SQLException | SqliteException ex) {
            throw new SqliteException(ex.getMessage());
        }
    }

}