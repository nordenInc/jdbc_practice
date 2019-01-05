package com.dbpractice.procedures;

import com.sun.org.apache.regexp.internal.RESyntaxException;

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
            statement.executeUpdate("INSERT INTO artworks (name) values ('Just for fund')");
            statement.executeUpdate("INSERT INTO artworks (name) values ('Good luck')");

            CallableStatement callableStatement = connection.prepareCall("{CALL ArtworksCount(?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();
            System.out.println(callableStatement.getInt(1));
            System.out.println("---------");

            CallableStatement callableStatement1 = connection.prepareCall("{CALL getArtworks(?)}");
            callableStatement1.setInt(1, 2);
            if (callableStatement1.execute()) {
                ResultSet resultSet = callableStatement1.getResultSet();
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id"));
                    System.out.println(resultSet.getString("name"));
                }
            }
            System.out.println("---------");

            CallableStatement callableStatement2 = connection.prepareCall("{call getCount()}");
            boolean hasResult = callableStatement2.execute();
            while (hasResult) {
                ResultSet resultSet = callableStatement2.getResultSet();
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                }
                hasResult = callableStatement2.getMoreResults();
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

