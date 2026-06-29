package com.hotelapp.model;

public record BookingFormInfo(
        int roomid,
        String roomTier,
        String roomSpace,
        String checkInDate,
        String checkOutDate
){}
