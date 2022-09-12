package com.railway.DataObject;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class User {
    private String email;
    private String password;
}
