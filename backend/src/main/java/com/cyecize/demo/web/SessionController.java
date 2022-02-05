package com.cyecize.demo.web;

import com.cyecize.demo.api.session.Session;
import com.cyecize.demo.api.session.SessionStorageService;
import com.cyecize.demo.constants.Endpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionStorageService sessionStorageService;

    @GetMapping(Endpoints.SESSION)
    public String getSession() {
        final Optional<Session> currentSession = this.sessionStorageService.getCurrentSession();
        if (currentSession.isPresent()) {
            return currentSession.get().getSessionId();
        }

        return this.sessionStorageService.generateSession().getSessionId();
    }
}
