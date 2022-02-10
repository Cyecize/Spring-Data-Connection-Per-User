package com.cyecize.demo.configuration.security.authloader;

import com.cyecize.demo.api.session.Session;
import com.cyecize.demo.api.session.SessionStorageService;
import com.cyecize.demo.api.user.User;
import com.cyecize.demo.constants.General;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocalAuthenticationLoader implements AuthenticationLoader {

    private final SessionStorageService sessionStorageService;

    @Override
    public boolean loadAuthentication(String accessToken) {
        Optional<Session> currentSession = this.sessionStorageService.getCurrentSession();
        if (currentSession.isEmpty()) {
            return false;
        }

        final User user = (User) currentSession.get().getSessionStorage().get(General.CURRENT_USER_SESSION_ATTR_NAME);
        if (user == null) {
            return false;
        }

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }
}
