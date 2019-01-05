package com.dbpractice.blob;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
            statement.executeUpdate("create table if not exists artworks (id MEDIUMINT not null primary key auto_increment, name varchar(20), img blob)");

            BufferedImage image = ImageIO.read(new File("key.jpg"));
            Blob blob = connection.createBlob();
            try (OutputStream outputStream = blob.setBinaryStream(1)) {
                ImageIO.write(image, "jpg", outputStream);
            }

            PreparedStatement preparedStatement = connection.prepareStatement("insert into artworks (name, img) values (?,?)");
            preparedStatement.setString(1,"first work");
            preparedStatement.setBlob(2, blob);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery("select * from artworks");
            while (resultSet.next()) {
                Blob blob1 = resultSet.getBlob("img");
                BufferedImage image1 = ImageIO.read(blob.getBinaryStream());
                File outputFile = new File("saved.jpg");
                ImageIO.write(image1, "jpg", outputFile);
            }


        } catch (SQLException | IOException e) {
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

