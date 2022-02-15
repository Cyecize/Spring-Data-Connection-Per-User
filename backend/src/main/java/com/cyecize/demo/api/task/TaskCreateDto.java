package com.cyecize.demo.api.task;

import com.cyecize.demo.util.converters.LocalDateConverter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class TaskCreateDto {

    @NotEmpty(message = "Description cannot be empty")
    @Length(max = 255, message = "Max length is {max} characters")
    private String description;

    @LocalDateConverter
    private LocalDateTime dueDate;

    private Boolean inProgress;
}
