package com.cyecize.demo.api.database;

import com.cyecize.demo.api.session.Session;
import com.cyecize.demo.api.session.SessionStorageService;
import com.cyecize.demo.error.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    private static final String DATABASE_SESSION_ATTR_NAME = "database";

    private final SessionStorageService sessionStorageService;

    @Override
    public boolean hasEstablishedConnection() {
        final Database database = this.getDatabase();

        return database.getJdbcDataSource() != null || database.getOrmDataSource() != null;
    }

    private Database getDatabase() {
        final Optional<Session> currentSession = this.sessionStorageService.getCurrentSession();
        if (currentSession.isEmpty()) {
            throw new ApiException("No session!");
        }

        currentSession.get().getSessionStorage().putIfAbsent(DATABASE_SESSION_ATTR_NAME, new Database());

        return (Database) currentSession.get().getSessionStorage().get(DATABASE_SESSION_ATTR_NAME);
    }
}
