package com.cyecize.demo.api.database;

import lombok.Data;

@Data
public class ServerConnectionProperties {
    private String host;

    private Integer port;

    private Boolean useSSL;
}
