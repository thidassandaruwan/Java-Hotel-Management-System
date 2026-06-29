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

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        // get all employees
        String sql = "SELECT * FROM Employee";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // add employee to employeeList
                employeeList.add(new Employee(rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }
        } catch (SQLException e) {
            System.err.println("Database error handling employees: " + e.getMessage());
        }
        return employeeList;
    }

    // get filtered employees, use to search and filter employees
    public List<Employee> getFilteredEmployees(String keyword, String role) {
        List<Employee> employeeList = new ArrayList<>();
        // 1 = 1 trick ( 1 = 1 is a awlways true condition, if we want to add more conditions to the sql query, we can use ALWAYS "ADD condition"
        // if we didnt had the WHERE 1 = 1, we can't dynamically just dad AND condition, because a SQL conditions statrs with WHERE consition
        StringBuilder sql = new StringBuilder("SELECT username, password, role FROM Employee WHERE 1=1 ");

        // if the seach term isn't empty
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND username LIKE ? ");
        }
        // if a specific role is chosen
        if (!"Any".equals(role)) {
            sql.append("AND role = ? ");
        }

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            // since the sql query is dynamic, something to keep track of the current parameter index ("?")
            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                pstmt.setString(paramIndex++, "%" + keyword.trim() + "%"); // % allows partial matching
            }
            if (!"Any".equals(role)) {
                pstmt.setString(paramIndex, role);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    employeeList.add(new Employee(rs.getString("username"), rs.getString("password"), rs.getString("role")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error filtering employees: " + e.getMessage());
        }
        return employeeList;
    }

    // Insert a new employee into the database
    public boolean addEmployee(Employee newEmployee) {
        String sql = "INSERT INTO Employee (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newEmployee.username());
            pstmt.setString(2, newEmployee.password());
            pstmt.setString(3, newEmployee.role());

            int rowsAffected = pstmt.executeUpdate();
            // Returns true if the insert was successful
            return (rowsAffected > 0);

        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }

    // update employee
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET password = ?, role = ? WHERE username = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.password());
            pstmt.setString(2, employee.role());
            pstmt.setString(3, employee.username());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            return false;
        }
    }

    // remove employee
    public boolean deleteEmployee(String username) {
        String sql = "DELETE FROM Employee WHERE username = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            return false;
        }
    }

    public List<Room> getAllRooms(){
        List<Room> rooms = new ArrayList<>();
        // get all rooms query
        String sql = "SELECT * FROM Room";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("roomId"),
                        rs.getString("space"),
                        rs.getString("tier"),
                        rs.getString("status"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error handling rooms: " + e.getMessage());
        }
        return rooms;
    }

    // get searched filtered rooms (same thing as getFilteredEmps just more filters
    public List<Room> getFilteredRooms(String roomIdStr, String tier, String space, String status) {
        List<Room> roomList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT roomId, space, tier, status, price FROM Room WHERE 1=1 ");

        if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
            sql.append("AND roomId = ? ");
        }
        if (!"Any".equals(tier)) sql.append("AND tier = ? ");
        if (!"Any".equals(space)) sql.append("AND space = ? ");
        if (!"Any".equals(status)) sql.append("AND status = ? ");

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(roomIdStr.trim()));
            }
            if (!"Any".equals(tier)) pstmt.setString(paramIndex++, tier);
            if (!"Any".equals(space)) pstmt.setString(paramIndex++, space);
            if (!"Any".equals(status)) pstmt.setString(paramIndex, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    roomList.add(new Room(rs.getInt("roomId"), rs.getString("space"),
                            rs.getString("tier"), rs.getString("status"), rs.getDouble("price")));
                }
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error filtering rooms: " + e.getMessage());
        }
        return roomList;
    }

    // Insert a new room into the database
    public boolean addRoom(Room newRoom) {
        String sql = "INSERT INTO Room (space, tier, status, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newRoom.space());
            pstmt.setString(2, newRoom.tier());
            pstmt.setString(3, "Ready");
            pstmt.setDouble(4, newRoom.price());

            int rowsAffected = pstmt.executeUpdate();
            // Returns true if the insert was successful
            return (rowsAffected > 0);

        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
            return false;
        }
    }

    // update room
    public boolean updateRoom(Room room) {
        String sql = "UPDATE Room SET space = ?, tier = ?, status = ?, price = ? WHERE roomId = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.space());
            pstmt.setString(2, room.tier());
            pstmt.setString(3, room.status());
            pstmt.setDouble(4, room.price());
            pstmt.setInt(5, room.roomId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating room: " + e.getMessage());
            return false;
        }
    }

    // remove room
    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM Room WHERE roomId = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }

    // get all cusotemr records
    public List<CustomerRecord> getAllCustomerRecords() {
        List<CustomerRecord> customerRecords = new ArrayList<>();
        // get all customer records
        String sql = "SELECT * FROM CustomerRecord";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // create customer record objects and add them to the records list
            while (rs.next()) {
                customerRecords.add(new CustomerRecord(
                        rs.getInt("recordId"),
                        rs.getString("customerName"),
                        rs.getInt("roomId"),
                        rs.getString("checkIn"),
                        rs.getString("checkOut"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error handling customer records: " + e.getMessage());
        }
        return customerRecords;
    }

    public List<CustomerRecord> searchCustomerRecords(String type, String keyword) {
        List<CustomerRecord> recordList = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            // return all customers if the search keyword empty
            return getAllCustomerRecords();
        }

        String sql = "SELECT recordId, customerName, roomId, checkIn, checkOut, price FROM CustomerRecord WHERE ";

        switch (type) {
            case "Customer": sql += "customerName LIKE ?"; break;
            case "RecordID": sql += "recordId = ?"; break;
            case "Check-In": sql += "checkIn = ?"; break;
            case "Check-Out": sql += "checkOut = ?"; break;
            default: return recordList;
        }

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // inject the query values corrosponding to the column type
            if (type.equals("Customer")) {
                pstmt.setString(1, "%" + keyword.trim() + "%");
            } else if (type.equals("RecordID")) {
                pstmt.setInt(1, Integer.parseInt(keyword.trim()));
            } else {
                pstmt.setString(1, keyword.trim()); // For dates
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    recordList.add(new CustomerRecord(rs.getInt("recordId"), rs.getString("customerName"),
                            rs.getInt("roomId"), rs.getString("checkIn"), rs.getString("checkOut"), rs.getDouble("price")));
                }
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error searching customers: " + e.getMessage());
        }
        return recordList;
    }
}
