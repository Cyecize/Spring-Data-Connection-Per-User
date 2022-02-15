package com.cyecize.demo.util.sorting;

import com.cyecize.demo.util.converters.GenericEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortQuery {

    @NotNull
    private String orderBy;

    @NotNull
    @GenericEnumConverter
    private Direction direction;
}
