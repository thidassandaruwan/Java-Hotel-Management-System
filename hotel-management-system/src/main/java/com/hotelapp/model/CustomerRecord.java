package com.hotelapp.model;

public record CustomerRecord(
        // custom customerRecord record
        int recordId,
        String custName,
        int roomId,
        String checkIn,
        String checkOut,
        double price
) {}
