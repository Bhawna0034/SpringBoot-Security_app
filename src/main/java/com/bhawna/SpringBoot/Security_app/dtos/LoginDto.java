package com.bhawna.SpringBoot.Security_app.dtos;

import lombok.Data;

@Data
public class LoginDto {

    private String email;
    private String password;
}
