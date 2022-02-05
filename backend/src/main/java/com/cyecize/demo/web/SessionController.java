package com.cyecize.demo.web;

import com.cyecize.demo.api.session.Session;
import com.cyecize.demo.api.session.SessionStorageService;
import com.cyecize.demo.constants.Endpoints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionStorageService sessionStorageService;

    @GetMapping(Endpoints.SESSION)
    public SessionDto getSession() {
        final Optional<Session> currentSession = this.sessionStorageService.getCurrentSession();
        if (currentSession.isPresent()) {
            return new SessionDto(currentSession.get().getSessionId());
        }

        return new SessionDto(this.sessionStorageService.generateSession().getSessionId());
    }

    @Data
    @AllArgsConstructor
    static class SessionDto {
        private String sessionId;
    }
}
