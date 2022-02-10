package com.cyecize.demo.api.database;

import com.cyecize.demo.util.validators.FieldMatch;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@FieldMatch(first = "passwordConfirm", second = "adminPassword", message = "Passwords do not match!")
public class CreateDatabaseDto {

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z$_]+", message = "Invalid database name")
    private String databaseName;

    @NotEmpty
    @Length(max = 20, message = "Username too long")
    private String adminUsername;

    @NotEmpty
    @Length(min = 6, max = 50, message = "Password should range between {min} and {max} characters")
    private String adminPassword;

    private String passwordConfirm;
}
