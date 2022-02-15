package com.cyecize.demo.api.task;

import com.cyecize.demo.util.sorting.SortQuery;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class TaskQuery {

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    @Valid
    @NotNull
    @JsonUnwrapped
    private SortQuery sortQuery;

    private Boolean hasDueDate;

    private Boolean inProgress;

    private String description;
}
