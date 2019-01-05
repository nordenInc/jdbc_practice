package com.dbpractice.preparedstatement;

import java.sql.*;

public class Main {

    private static  final String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String INSERT_DISH = "INSERT INTO dish values (?,?,?,?,?,?,?)";
    private static final String GET_ALL = "SELECT * from dish";
    private static final String DELETE_DISH = "DELETE from dish where id = ?";

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            preparedStatement = connection.prepareStatement(INSERT_DISH);
            preparedStatement = connection.prepareStatement(DELETE_DISH);
            preparedStatement.setInt(1,2);
            preparedStatement.executeUpdate();

//            preparedStatement.setInt(1,2);
//            preparedStatement.setString(2,"Insert Title");
//            preparedStatement.setString(3,"Insert Description");
//            preparedStatement.setFloat(4,9.54f);
//            preparedStatement.setBoolean(5, true);
//            preparedStatement.setDate(6, new Date(Calendar.getInstance().getTimeInMillis()));
//            preparedStatement.setBlob(7,new FileInputStream("ilon.jpg"));
//            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                float rating = resultSet.getFloat("rating");
                boolean published = resultSet.getBoolean("published");
                Date created = resultSet.getDate("created");
                byte[] icon = resultSet.getBytes("icon");

                System.out.println("id: " + id + ", title: " + title + ", description: "
                + description + ", rating: " + rating + ", published: " + published
                + ", created: " + created + ", icon length: " + icon.length);
            }
        } catch (SQLException /*| FileNotFoundException*/ e) {
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
