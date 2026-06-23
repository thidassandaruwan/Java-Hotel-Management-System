package com.hotelapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthModel {
    public String validateLogin(String username, String password){
        String sql = "SELECT * FROM Employee WHERE BINARY username = ? AND BINARY password = ?";

        try (Connection connection = DatabaseHelper.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next())
            {
                return result.getString("role");
            }
            else
            {
                return null;
            }


        } catch (Exception e) {
            System.out.println("Database Connection Error" + e.getMessage());
            return null;
        }

    }
}