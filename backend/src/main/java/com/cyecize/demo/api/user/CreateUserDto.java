package com.cyecize.demo.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDto {

    private String username;

    private String password;
}
