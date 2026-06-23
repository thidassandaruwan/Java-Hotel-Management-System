package com.hotelapp.model;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    // create dotEnv object to load database login details from .env
    private static final Dotenv dotenv = Dotenv.configure().directory("C:\\Users\\Thidas\\IdeaProjects\\EAD_coursework\\hotel-management-system\\.env").load();
    // Aiven database "login" details
    private static final String HOST = dotenv.get("DB_HOST");
    private static final String PORT = dotenv.get("DB_PORT");
    private static final String DB_NAME = dotenv.get("DB_NAME");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASS = dotenv.get("DB_PASS");

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?sslMode=REQUIRED";

    public static Connection connect() throws SQLException{
        // stablishes the connection between Aiven database and return a connection object so SQL queries can be run
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
