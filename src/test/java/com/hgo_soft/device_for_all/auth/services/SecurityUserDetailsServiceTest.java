package com.hgo_soft.device_for_all.auth.services;

import com.hgo_soft.device_for_all.auth.entities.Permission;
import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        when(userRepository.findRolesAndPermissionsByUsername("disabled-user")).thenReturn(Optional.of(user));

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("disabled-user");
        assertFalse(userDetails.isEnabled());
    }

    @Test
    void loadUserByUsername_whenUserHasRoleAndPermissions_returnsAuthorities() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SecurityUserDetailsService securityUserDetailsService = new SecurityUserDetailsService(userRepository);
        Permission deviceRead = Permission.builder()
                .name("DEVICE_READ")
                .build();
        Permission deviceCreate = Permission.builder()
                .name("DEVICE_CREATE")
                .build();
        Role staff = Role.builder()
                .name("STAFF")
                .permissions(Set.of(deviceRead, deviceCreate))
                .build();
        User user = User.builder()
                .username("staff-user")
                .passwordHash("staff-password")
                .enabled(true)
                .roles(Set.of(staff))
                .build();

        when(userRepository.findRolesAndPermissionsByUsername("staff-user"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("staff-user");
        Set<String> authorityNames = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertEquals(
                Set.of("ROLE_STAFF", "DEVICE_READ", "DEVICE_CREATE"),
                authorityNames
        );
    }
}
