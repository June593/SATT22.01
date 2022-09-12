package com.railway.DataType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SeatType {
    HARD_SEAT("Hard seat"),
    SOFT_SEAT("Soft seat"),
    SOFT_SEAT_WITH_AIR_CONDITIONER("Soft seat with air conditioner"),
    HARD_BED("Hard bed"),
    SOFT_BED("Soft bed"),
    SOFT_BED_WITH_AIR_CONDITIONER("Soft bed with air conditioner");

    public String name;
}
