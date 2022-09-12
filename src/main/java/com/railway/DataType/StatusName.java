package com.railway.DataType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusName {

    IGNORED("Ignore"),
    NEW("New"),
    EXPIRED("Expired"),
    PAID("Paid");

    public String name;
}
