package com.hotelapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    // Aiven database "login" details
    private static final String HOST = "mysql-personalprojects-thidas-personalprojects-thidas.h.aivencloud.com";
    private static final String PORT = "28437";
    private static final String DB_NAME = "hotelSystem";
    private static final String USER = "thidas";
    private static final String PASS = "";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?sslMode=REQUIRED";

    public static Connection connect() throws SQLException{
        // stablishes the connection between Aiven database and return a connection object so SQL queries can be run
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
