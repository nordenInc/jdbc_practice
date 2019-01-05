package com.dbpractice.scrollable.rowset;

import java.sql.*;

public class Main {

    private static  final String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.execute("drop table if exists artworks");
            statement.executeUpdate("create table if not exists artworks (id MEDIUMINT not null primary key auto_increment, name varchar(20), img blob, dt DATE)");
            statement.executeUpdate("INSERT INTO artworks (name) values ('Just for fun')");
            statement.executeUpdate("INSERT INTO artworks (name) values ('Good luck')");
            statement.executeUpdate("INSERT INTO artworks (name) values ('Bible')");

            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select * from artworks;");
            if (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.previous()) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.relative(2)) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.relative(-2)) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.absolute(3)) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.first()) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.last()) {
                System.out.println(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            System.err.println("Problems with connection " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Problems with connection closing.");
            }
        }
    }
}

