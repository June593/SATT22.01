package com.railway.DataType.header_name;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimetableTableHeader {

    NO("No."),
    DEPART_STATION("Depart Station"),
    ARRIVE_STATION("Arrive Station"),
    DEPART_TIME("Depart Time"),
    ARRIVE_TIME("Arrive Time"),
    CHECK_PRICE("Check Price"),
    BOOK_TICKET("Book ticket");

    public String name;
}
