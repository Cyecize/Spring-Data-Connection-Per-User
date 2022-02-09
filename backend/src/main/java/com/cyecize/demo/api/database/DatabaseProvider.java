package com.cyecize.demo.api.database;

import com.cyecize.demo.api.database.connectionutil.ConnectionUtil;
import com.cyecize.demo.api.database.connectionutil.MsSqlServerConnectionUtil;
import com.cyecize.demo.api.database.connectionutil.MySqlConnectionUtil;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.mysql.cj.jdbc.Driver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.SQLServer2016Dialect;

@Getter
@RequiredArgsConstructor
public enum DatabaseProvider {

    MY_SQL(
            "My SQL",
            3306,
            Driver.class.getName(),
            new MySqlConnectionUtil(),
            "mysql",
            MySQL8Dialect.class.getName()
    ),

    MS_SQL_SERVER(
            "Microsoft SQL Server",
            1433,
            SQLServerDriver.class.getName(),
            new MsSqlServerConnectionUtil(),
            "mssqlserver",
            SQLServer2016Dialect.class.getName()
    );

    private final String displayName;

    private final int defaultPort;

    private final String sqlDriver;

    private final ConnectionUtil connectionUtil;

    private final String migrationsFolderName;

    private final String dialectClass;

    private final String name = name();
}
