package com.cyecize.demo.api.user;

import com.cyecize.demo.util.converters.LocalDateConverter;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {

    private Long id;

    private String username;

    @LocalDateConverter
    private LocalDateTime dateRegistered;

    private List<Role> roles;
}
