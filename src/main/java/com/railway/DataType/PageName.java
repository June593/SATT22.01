package com.railway.DataType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PageName {
    REGISTER("Register"),
    LOGIN("Login"),
    TIMETABLE("Timetable"),
    TICKET_PRICE("Ticket price"),
    CHANGE_PASSWORD("Change password"),
    BOOK_TICKET("Book ticket"),
    MY_TICKET("Manage Tickets"),
    LOGOUT("Log out");

    public String name;
}
