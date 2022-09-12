package com.railway.DataObject;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketInformation {
    private String departStation;
    private String arriveStation;
    private String seatType;
    private String departDate;
    private String bookDate;
    private String expiredDate;
    private BigDecimal price;
    private int ticketAmount;
}
