package com.cyecize.demo.api.session;

import java.util.Optional;

public interface SessionStorageService {

    Session generateSession();

    Optional<Session> getCurrentSession();

    void setCurrentSession(String id);
}
