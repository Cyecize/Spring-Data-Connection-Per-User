package com.cyecize.demo.api.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@EqualsAndHashCode
public class Session {

    @Setter
    private String sessionId;

    private LocalDateTime lastAccessTimestamp;

    private final Map<String, Object> sessionStorage = new ConcurrentHashMap<>();

    void updateLastAccessTime() {
        this.lastAccessTimestamp = LocalDateTime.now();
    }

    public void invalidate() {
        this.lastAccessTimestamp = LocalDateTime.MIN;
    }
}
