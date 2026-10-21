package com.hgo_soft.device_for_all.auth.services;

import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;


public class SecurityUserDetailsServiceTest {

    @Test
    void loadUserByUsername_whenUserIsDisabled_returnsDisabledUserDetails(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SecurityUserDetailsService securityUserDetailsService = new SecurityUserDetailsService(userRepository);
        User user = User.builder()
                .username("disabled-user")
                .passwordHash("disabled-password")
                .enabled(false)
                .roles(Set.of())
                .build();

        when(userRepository.findByUsername("disabled-user")).thenReturn(Optional.of(user));

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("disabled-user");
        assertFalse(userDetails.isEnabled());
    }
}
