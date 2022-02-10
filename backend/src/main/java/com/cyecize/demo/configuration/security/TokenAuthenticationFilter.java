package com.cyecize.demo.configuration.security;

import com.cyecize.demo.configuration.security.authloader.AuthenticationLoader;
import com.cyecize.demo.constants.General;
import lombok.RequiredArgsConstructor;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter implements Filter {

    private final AuthenticationLoader authenticationLoader;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        final String accessToken = httpRequest.getHeader(General.AUTHORIZATION_TOKEN);

        if (accessToken != null) {
            boolean localAuthSuccess = this.authenticationLoader.loadAuthentication(accessToken);
        }

        chain.doFilter(request, response);
    }
}
