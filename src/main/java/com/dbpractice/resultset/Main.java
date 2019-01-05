package com.dbpractice.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DBWorker dbWorker = new DBWorker();
        String query = "select * from user where username = 'Boris'";

        try {
            Statement statement = dbWorker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.err.println("Problems with connection");
        } finally {
            try {
                dbWorker.getConnection().close();
            } catch (SQLException e) {
                System.err.println("Problems with connection closing.");
            }
        }
    }
}
