package com.hotelapp.model;

public record Room(
        // custom data type for room info
        int roomId,
        String space,
        String tier,
        String status,
        double price
){}
