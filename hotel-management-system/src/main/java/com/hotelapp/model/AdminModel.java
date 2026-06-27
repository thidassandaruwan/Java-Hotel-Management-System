package com.hotelapp.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {
    public AdminDashboardStats getDashboardStats(boolean currentDayOnly) {
        // check if need to query customer records for today or all month
        String sql = "SELECT " +
                "SUM(c.price) AS total, " +
                "SUM(CASE WHEN r.tier = 'Standard' AND r.space = 'Single' THEN 1 ELSE 0 END) AS standardSingle, " +
                "SUM(CASE WHEN r.tier = 'Standard' AND r.space = 'Double' THEN 1 ELSE 0 END) AS standardDouble, " +
                "SUM(CASE WHEN r.tier = 'Premium' AND r.space = 'Single' THEN 1 ELSE 0 END) AS premiumSingle, " +
                "SUM(CASE WHEN r.tier = 'Premium' AND r.space = 'Double' THEN 1 ELSE 0 END) AS premiumDouble " +
                "FROM CustomerRecord c " +
                "JOIN Room r ON c.roomId = r.roomId ";

        if (currentDayOnly)
        {
            sql = sql + "WHERE checkIn = CURRENT_DATE()" ;
        }
        else
        {
            sql = sql + "WHERE MONTH(checkIn) = MONTH(CURRENT_DATE()) AND YEAR(checkIn) = YEAR(CURRENT_DATE())";
        }

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            // if there any results, return the "total" from the query
            if (rs.next())
            {
                return new AdminDashboardStats(
                        rs.getInt("standardSingle"),
                        rs.getInt("standardDouble"),
                        rs.getInt("premiumSingle"),
                        rs.getInt("premiumDouble"),
                        rs.getDouble("total")
                );
            }
        }
        catch (SQLException e) {
            System.err.println("Error querying dashboard stats" + e.getMessage());
        }
        return new AdminDashboardStats(0, 0, 0, 0, 0);
    }

    public List<String[]> getAllEmployees() {
        List<String[]> employeeList = new ArrayList<>();
        String sql = "SELECT username, role FROM Employee";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                employeeList.add(new String[]{ rs.getString("username"), rs.getString("role") });
            }
        } catch (SQLException e) {
            System.err.println("Database error handling employees: " + e.getMessage());
        }
        return employeeList;
    }
}
