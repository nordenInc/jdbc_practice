package com.dbpractice.staticqueries;

import java.sql.*;

public class Main {

    private static  final String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
//            statement.execute("INSERT INTO users (name, email) values ('Mobby', 'kefwe@mail.com')");
//            int res = statement.executeUpdate("UPDATE users SET name = 'Kay', age = 23 where id = 7");
//            ResultSet res = statement.executeQuery("SELECT * from users");
            statement.addBatch("INSERT INTO users (name, email) values ('Tobby', 'kefwe@mail.com')");
            statement.addBatch("INSERT INTO users (name, email) values ('Mobby', 'kefwe@mail.com')");
            statement.addBatch("INSERT INTO users (name, email) values ('Fobby', 'kefwe@mail.com')");
            statement.executeBatch();
            statement.clearBatch();

            statement.isClosed();
        } catch (SQLException e) {
            System.err.println("Unable to get connection." + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Problems with connection closing.");
            }
        }
    }
}
