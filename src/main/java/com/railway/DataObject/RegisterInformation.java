package com.railway.DataObject;

import com.railway.common.Constants;
import com.railway.common.Helper;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@Builder
public class RegisterInformation {
    private String email;
    private String password;
    private String confirmPassword;
    private String pid;

    public RegisterInformation() {
        this.email = Helper.getUniqueEmail();
        this.password = Constants.VALID_PASSWORD;
        this.confirmPassword = Constants.VALID_PASSWORD;
        this.pid = Constants.VALID_PID;
    }
}
