package com.example.contactmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBConnection {

    private static int id;
    private static String name;
    private static long phoneNumber;
    private static String email;

    public static Connection getCon() {
        String url = "jdbc:mysql://localhost:3306/contacts";// Replace "contacts" with your Database name.
        String username = "root"; // Your Connection Username.
        String password = "1234";// Your Connection Password.
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static boolean insertIntoDatabase(String name, long phoneNumber, String email) {
        Connection connection = getCon();
        try {
            String insertQuery = "insert into contactData (person_name, phone_number, email) values(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, name);
            statement.setLong(2, phoneNumber);
            statement.setString(3, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean editData(int id, String new_name, long new_phoneNumber, String new_email) {
        Connection connection = getCon();
        String updateQuery = "UPDATE contactData SET person_name = ?, phone_number = ?, email = ? WHERE id = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, new_name);
            preparedStatement.setString(2, String.valueOf(new_phoneNumber));
            preparedStatement.setString(3, new_email);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean deleteData(int id) {
        Connection connection = getCon();
        String deleteQuery = "DELETE FROM contactData WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static ObservableList<Person> getPersonData() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        Connection connection = getCon();
        try {
            // Retrieve the new person from the database
            String selectQuery = "SELECT * FROM contactData";
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = selectPreparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("person_name");
                long phoneNumber = resultSet.getLong("phone_number");
                String email = resultSet.getString("email");

                Person person = new Person(id, name, phoneNumber, email);
                personList.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personList;
    }

    public static String getName() {
        return name;
    }

    public static int getId() {
        return id;
    }

    public static long getPhoneNumber() {
        return phoneNumber;
    }

    public static String getEmail() {
        return email;
    }
}

