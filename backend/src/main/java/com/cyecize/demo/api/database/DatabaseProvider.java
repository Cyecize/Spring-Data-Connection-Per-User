package com.cyecize.demo.api.database;

import com.cyecize.demo.api.database.connectionutil.ConnectionUtil;
import com.cyecize.demo.api.database.connectionutil.MsSqlServerConnectionUtil;
import com.cyecize.demo.api.database.connectionutil.MySqlConnectionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DatabaseProvider {

    MY_SQL(
            "My SQL",
            3306,
            "com.mysql.cj.jdbc.Driver",
            new MySqlConnectionUtil(),
            "mysql"
    ),

    MS_SQL_SERVER(
            "Microsoft SQL Server",
            1433,
            "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            new MsSqlServerConnectionUtil(),
            "mssqlserver"
    );

    private final String displayName;

    private final int defaultPort;

    private final String sqlDriver;

    private final ConnectionUtil connectionUtil;

    private final String migrationsFolderName;

    private final String name = name();
}
