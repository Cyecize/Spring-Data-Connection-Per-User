package com.cyecize.demo.api.database;

import lombok.Data;

@Data
public class DatabaseProviderDto {

    private String displayName;

    private String name;

    private int defaultPort;
}
