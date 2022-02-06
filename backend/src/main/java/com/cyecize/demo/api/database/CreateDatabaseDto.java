package com.cyecize.demo.api.database;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class CreateDatabaseDto {

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z$_]+", message = "Invalid database name")
    private String databaseName;
}
