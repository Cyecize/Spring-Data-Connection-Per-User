package com.cyecize.demo.api.task;

import com.cyecize.demo.util.converters.LocalDateConverter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long id;

    private String description;

    private Boolean inProgress;

    @LocalDateConverter
    private LocalDateTime dueDate;

    @LocalDateConverter
    private LocalDateTime createDate;
}
