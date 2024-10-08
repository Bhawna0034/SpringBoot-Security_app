package com.bhawna.SpringBoot.Security_app.dtos;

import lombok.Data;

@Data
public class SignupDto {

    private String email;
    private String name;
    private String password;
}
