package com.railway.DataType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TabName {
    REGISTER("Register"),
    LOGIN("Login"),
    TIMETABLE("Timetable"),
    TICKET_PRICE("Ticket price"),
    CHANGE_PASSWORD("Change password"),
    BOOK_TICKET("Book ticket"),
    MY_TICKET("My ticket"),
    LOG_OUT("Log out");

    public String name;
}
