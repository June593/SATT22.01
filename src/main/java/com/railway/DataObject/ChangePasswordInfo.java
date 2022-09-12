package com.railway.DataObject;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordInfo {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
