package com.hgo_soft.device_for_all.auth.services;

import com.hgo_soft.device_for_all.auth.entities.Permission;
import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SecurityUserDetailsServiceTest {

    @Test
    void loadUserByUsername_whenUserIsEnabled_returnsEnabledUserDetails() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SecurityUserDetailsService securityUserDetailsService = new SecurityUserDetailsService(userRepository);
        User user = User.builder()
                .username("enabled-user")
                .passwordHash("encoded-password")
                .enabled(true)
                .roles(Set.of())
                .build();

        when(userRepository.findRolesAndPermissionsByUsername("enabled-user"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("enabled-user");

        assertTrue(userDetails.isEnabled());
    }

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
    void loadUserByUsername_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SecurityUserDetailsService securityUserDetailsService = new SecurityUserDetailsService(userRepository);

        when(userRepository.findRolesAndPermissionsByUsername("missing-user"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> securityUserDetailsService.loadUserByUsername("missing-user")
        );

        assertEquals("User not found: missing-user", exception.getMessage());
        verify(userRepository).findRolesAndPermissionsByUsername("missing-user");
    }

    @Test
    void loadUserByUsername_whenUserHasMultipleRoles_returnsEveryRoleAuthority() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SecurityUserDetailsService securityUserDetailsService = new SecurityUserDetailsService(userRepository);
        Role staff = Role.builder().name("STAFF").permissions(Set.of()).build();
        Role administrator = Role.builder().name("ADMIN").permissions(Set.of()).build();
        User user = User.builder()
                .username("multi-role-user")
                .passwordHash("encoded-password")
                .enabled(true)
                .roles(Set.of(staff, administrator))
                .build();

        when(userRepository.findRolesAndPermissionsByUsername("multi-role-user"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("multi-role-user");

        assertEquals(
                Set.of("ROLE_STAFF", "ROLE_ADMIN"),
                authorityNames(userDetails)
        );
    }

    @Test
    void loadUserByUsername_whenMultipleRolesHavePermissions_returnsPermissionsFromEveryRole() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SecurityUserDetailsService securityUserDetailsService = new SecurityUserDetailsService(userRepository);
        Permission deviceRead = Permission.builder().name("DEVICE_READ").build();
        Permission userManage = Permission.builder().name("USER_MANAGE").build();
        Role staff = Role.builder()
                .name("STAFF")
                .permissions(Set.of(deviceRead))
                .build();
        Role administrator = Role.builder()
                .name("ADMIN")
                .permissions(Set.of(userManage))
                .build();
        User user = User.builder()
                .username("multi-permission-user")
                .passwordHash("encoded-password")
                .enabled(true)
                .roles(Set.of(staff, administrator))
                .build();

        when(userRepository.findRolesAndPermissionsByUsername("multi-permission-user"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("multi-permission-user");

        assertEquals(
                Set.of("ROLE_STAFF", "ROLE_ADMIN", "DEVICE_READ", "USER_MANAGE"),
                authorityNames(userDetails)
        );
    }

    @Test
    void loadUserByUsername_whenRolesSharePermission_removesDuplicateAuthority() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SecurityUserDetailsService securityUserDetailsService = new SecurityUserDetailsService(userRepository);
        Permission deviceRead = Permission.builder().name("DEVICE_READ").build();
        Role staff = Role.builder()
                .name("STAFF")
                .permissions(Set.of(deviceRead))
                .build();
        Role auditor = Role.builder()
                .name("AUDITOR")
                .permissions(Set.of(deviceRead))
                .build();
        User user = User.builder()
                .username("shared-permission-user")
                .passwordHash("encoded-password")
                .enabled(true)
                .roles(Set.of(staff, auditor))
                .build();

        when(userRepository.findRolesAndPermissionsByUsername("shared-permission-user"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("shared-permission-user");

        assertEquals(
                Set.of("ROLE_STAFF", "ROLE_AUDITOR", "DEVICE_READ"),
                authorityNames(userDetails)
        );
        assertEquals(3, userDetails.getAuthorities().size());
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

        assertEquals(
                Set.of("ROLE_STAFF", "DEVICE_READ", "DEVICE_CREATE"),
                authorityNames(userDetails)
        );
    }

    private Set<String> authorityNames(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
