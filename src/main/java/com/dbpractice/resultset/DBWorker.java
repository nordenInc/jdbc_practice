package com.dbpractice.resultset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWorker {
    private static  final String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public DBWorker() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Connection failed!");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
