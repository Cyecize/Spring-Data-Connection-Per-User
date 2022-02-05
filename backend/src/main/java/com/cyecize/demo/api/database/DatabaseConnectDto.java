package com.cyecize.demo.api.database;

import com.cyecize.demo.util.converters.GenericEnumConverter;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DatabaseConnectDto {

    @NotNull
    @GenericEnumConverter
    private DatabaseProvider databaseProvider;

    @NotEmpty
    private String host;

    @NotNull
    private Integer port;

    @NotEmpty
    private String username;

    private String password;

    @NotNull
    private Boolean useSSL;
}
