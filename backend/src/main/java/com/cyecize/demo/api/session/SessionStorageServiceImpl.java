package com.cyecize.demo.api.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class SessionStorageServiceImpl implements SessionStorageService {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private final int sessionMaxLifeTimeMinutes;

    private final ThreadLocal<Session> currentSession = new ThreadLocal<>();

    public SessionStorageServiceImpl(@Value("${session.max.lifetime.minutes}") int sessionMaxLifeTimeMinutes) {
        this.sessionMaxLifeTimeMinutes = sessionMaxLifeTimeMinutes;
    }

    @Scheduled(fixedRateString = "${session.filter.interval.minutes}", timeUnit = TimeUnit.MINUTES)
    void cleanInactiveSessions() {
        final LocalDateTime now = LocalDateTime.now();

        this.sessions.entrySet().stream()
                .filter(entry -> this.isSessionExpired(entry.getValue(), now))
                .forEach(entry -> this.sessions.remove(entry.getKey()));
    }

    @Override
    public Session generateSession() {
        final Session session = new Session();
        session.setSessionId(UUID.randomUUID().toString());
        session.updateLastAccessTime();

        this.sessions.put(session.getSessionId(), session);

        return session;
    }

    @Override
    public Optional<Session> getCurrentSession() {
        return Optional.ofNullable(this.currentSession.get());
    }

    @Override
    public void setCurrentSession(String id) {
        this.currentSession.set(null);
        this.getSession(id).ifPresent(this.currentSession::set);
    }

    private Optional<Session> getSession(String id) {
        if (id == null) {
            return Optional.empty();
        }

        final Session session = this.sessions.get(id);

        if (session == null) {
            return Optional.empty();
        }

        if (this.isSessionExpired(session, LocalDateTime.now())) {
            this.sessions.remove(id);
            return Optional.empty();
        }

        session.updateLastAccessTime();
        return Optional.of(session);
    }

    private boolean isSessionExpired(Session session, LocalDateTime timeNow) {
        final long diff = Math.abs(ChronoUnit.MINUTES.between(timeNow, session.getLastAccessTimestamp()));
        return diff > this.sessionMaxLifeTimeMinutes;
    }
}
