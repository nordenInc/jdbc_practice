package com.dbpractice.dates;

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

            PreparedStatement preparedStatement = connection.prepareStatement("insert into artworks (name, dt) values (?,?)");
            preparedStatement.setString(1,"monalisa");
            preparedStatement.setDate(2, new Date(1546672576542L));
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery("select * from artworks");
            while (resultSet.next()) {
                System.out.println(resultSet.getDate("dt"));
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

