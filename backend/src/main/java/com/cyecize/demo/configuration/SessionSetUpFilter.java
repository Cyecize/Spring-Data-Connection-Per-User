package com.cyecize.demo.configuration;

import com.cyecize.demo.api.session.SessionStorageService;
import com.cyecize.demo.constants.General;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(-100)
@RequiredArgsConstructor
public class SessionSetUpFilter implements Filter {

    private final SessionStorageService sessionStorageService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final String header = req.getHeader(General.SESSION_TOKEN);

        this.sessionStorageService.setCurrentSession(header);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
