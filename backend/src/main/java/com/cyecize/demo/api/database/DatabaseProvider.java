package com.cyecize.demo.api.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DatabaseProvider {

    MY_SQL("My SQL", 3306),
    MS_SQL_SERVER("Microsoft SQL Server", 1433);

    private final String displayName;

    private final int defaultPort;

    private final String name = name();
}
