package com.cyecize.demo.web;

import com.cyecize.demo.api.user.User;
import com.cyecize.demo.api.user.UserDto;
import com.cyecize.demo.configuration.security.CurrentUser;
import com.cyecize.demo.constants.Endpoints;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isFullyAuthenticated()")
public class UserController {

    private final ModelMapper modelMapper;

    @GetMapping(Endpoints.USER_ME)
    public UserDto getCurrentUser(@CurrentUser User currentUser) {
        return this.modelMapper.map(currentUser, UserDto.class);
    }
}
