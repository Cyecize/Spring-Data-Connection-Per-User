package com.cyecize.demo.api.user;

import com.cyecize.demo.api.session.Session;
import com.cyecize.demo.api.session.SessionStorageService;
import com.cyecize.demo.constants.General;
import com.cyecize.demo.error.ApiException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    private final SessionStorageService sessionStorageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Cannot find user with this email!");
        }

        return user;
    }

    @Override
    public User createAdmin(CreateUserDto createUserDto) {
        final User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setRoles(this.roleRepository.findAll().stream()
                .map(r -> (GrantedAuthority) r).collect(Collectors.toList())
        );
        user.setPassword(createUserDto.getPassword());

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        this.userRepository.save(user);

        return user;
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findUserById(id);
    }

    @Override
    public void login(String username, String password) {
        final User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new ApiException("Username not found!");
        }

        if (!this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new ApiException("Wrong password!");
        }

        this.sessionStorageService.getCurrentSession().get()
                .getSessionStorage().put(General.CURRENT_USER_SESSION_ATTR_NAME, user);
    }

    @Override
    public void logout() {
        final Session session = this.sessionStorageService.getCurrentSession().get();

        session.getSessionStorage().remove(General.CURRENT_USER_SESSION_ATTR_NAME);
    }
}
