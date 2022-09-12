package com.railway.DataObject;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filter {

    private String departStation;
    private String arriveStation;
    private String departDate;
    private String status;
}
