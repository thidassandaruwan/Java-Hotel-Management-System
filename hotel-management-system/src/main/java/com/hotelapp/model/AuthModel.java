package com.hotelapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthModel {
    public String validateLogin(String username, String password){
        String sql = "SELECT * FROM Employee WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseHelper.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();

            return result.getString("role");

        } catch (Exception e) {
            System.out.println("Database Connection Error" + e.getMessage());
            return null;
        }

    }
}