package com.hgo_soft.device_for_all.auth.services;

import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findRolesAndPermissionsByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException( "User not found: " + username));

        var authorities = user.getRoles().stream()
                .flatMap(role -> Stream.concat(
                        Stream.of(
                                new SimpleGrantedAuthority("ROLE_" + role.getName())
                        ),
                        role.getPermissions().stream()
                                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                )).collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .disabled(!user.isEnabled())
                .build();
    }
}
