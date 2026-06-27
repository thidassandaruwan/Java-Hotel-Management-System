package com.hotelapp.model;

// custom data type for admin dashboard stats
public record AdminDashboardStats(
        // standard single and double rooms booked
        int standardSingle,
        int standardDouble,
        // premium single and doubel rooms booked
        int premiumSingle,
        int premiumDouble,
        // total revenue generated from bookings
        double totalRevenue
) {}
