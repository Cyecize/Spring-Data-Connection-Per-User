package com.cyecize.demo.api.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createAdmin(CreateUserDto createUserDto);

    User findById(Long id);

    void login(String username, String password);

    void logout();
}
