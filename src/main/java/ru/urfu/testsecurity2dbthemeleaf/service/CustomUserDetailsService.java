package ru.urfu.testsecurity2dbthemeleaf.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.urfu.testsecurity2dbthemeleaf.enity.User;
import ru.urfu.testsecurity2dbthemeleaf.repository.UserRepository;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        User user = userRepository.findByEmail(usernameOrEmail);
        if (user != null) {
            org.springframework.security.core.userdetails.User userAuthority = new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(),
                    user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList()));
            return userAuthority;
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}
