package com.cyecize.demo.web;

import com.cyecize.demo.api.user.UserService;
import com.cyecize.demo.constants.Endpoints;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;

    @PostMapping(Endpoints.LOGIN)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAnonymous()")
    public void login(@Valid @RequestBody LoginDto loginDto) {
        this.userService.login(loginDto.getUsername(), loginDto.getPassword());
    }

    @PostMapping(Endpoints.LOGOUT)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isFullyAuthenticated()")
    public void logout() {
        this.userService.logout();
    }

    @Data
    static class LoginDto {

        @NotNull
        private String username;

        @NotNull
        private String password;
    }
}
