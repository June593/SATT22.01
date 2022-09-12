package com.railway.DataType.header_name;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ManageTicketTableHeader {
    NO("No."),
    DEPART_STATION("Depart Station"),
    ARRIVE_STATION("Arrive Station"),
    SEAT_TYPE("Seat Type"),
    DEPART_DATE("Depart Date"),
    BOOK_DATE("Book Date"),
    EXPIRED_DATE("Expired Date"),
    STATUS("Status"),
    AMOUNT("Amount"),
    TOTAL_PRICE("Total Price"),
    OPERATION("Operation");

    public String name;
}
