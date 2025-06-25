package com.miempresa.ecommerce.security;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.miempresa.ecommerce.entity.User;
import com.miempresa.ecommerce.repository.UserRepository;

/**
 * Tests unitarios para CustomUserDetailsService
 * 
 */
@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEnabled(true);
        testUser.setAccountNonExpired(true);
        testUser.setAccountNonLocked(true);
        testUser.setCredentialsNonExpired(true);
        testUser.setRoles(Set.of(User.Role.USER));
    }

    @Test
    void testLoadUserByUsername_WithValidUsername_ShouldReturnUserDetails() {
        // Dado
        when(userRepository.findByUsernameOrEmail("testuser", "testuser"))
                .thenReturn(Optional.of(testUser));

        // Cuando
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Entonces
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(userRepository).findByUsernameOrEmail("testuser", "testuser");
    }

    @Test
    void testLoadUserByUsername_WithValidEmail_ShouldReturnUserDetails() {
        // Dado
        when(userRepository.findByUsernameOrEmail("test@example.com", "test@example.com"))
                .thenReturn(Optional.of(testUser));

        // Cuando
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");

        // Entonces
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        verify(userRepository).findByUsernameOrEmail("test@example.com", "test@example.com");
    }

    @Test
    void testLoadUserByUsername_WithInvalidUsername_ShouldThrowException() {
        // Dado
        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // Cuando & Entonces
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });

        verify(userRepository).findByUsernameOrEmail("nonexistent", "nonexistent");
    }

    @Test
    void testLoadUserById_WithValidId_ShouldReturnUserDetails() {
        // Dado
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Cuando
        UserDetails userDetails = userDetailsService.loadUserById(1L);

        // Entonces
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void testLoadUserById_WithInvalidId_ShouldThrowException() {
        // Dado
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Cuando & Entonces
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserById(999L);
        });

        verify(userRepository).findById(999L);
    }

    @Test
    void testUserPrincipal_WithMultipleRoles_ShouldMapAllRoles() {
        // Dado
        testUser.setRoles(Set.of(User.Role.USER, User.Role.ADMIN));
        when(userRepository.findByUsernameOrEmail("testuser", "testuser"))
                .thenReturn(Optional.of(testUser));

        // Cuando
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Entonces
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testUserPrincipal_WithDisabledUser_ShouldReturnDisabledUserDetails() {
        // Dado
        testUser.setEnabled(false);
        when(userRepository.findByUsernameOrEmail("testuser", "testuser"))
                .thenReturn(Optional.of(testUser));

        // Cuando
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Entonces
        assertFalse(userDetails.isEnabled());
    }

    @Test
    void testUserPrincipal_WithExpiredAccount_ShouldReturnExpiredUserDetails() {
        // Dado
        testUser.setAccountNonExpired(false);
        when(userRepository.findByUsernameOrEmail("testuser", "testuser"))
                .thenReturn(Optional.of(testUser));

        // Cuando
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Entonces
        assertFalse(userDetails.isAccountNonExpired());
    }

    @Test
    void testUserPrincipal_WithLockedAccount_ShouldReturnLockedUserDetails() {
        // Dado
        testUser.setAccountNonLocked(false);
        when(userRepository.findByUsernameOrEmail("testuser", "testuser"))
                .thenReturn(Optional.of(testUser));

        // Cuando
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Entonces
        assertFalse(userDetails.isAccountNonLocked());
    }

    @Test
    void testUserPrincipal_WithExpiredCredentials_ShouldReturnExpiredCredentialsUserDetails() {
        // Dado
        testUser.setCredentialsNonExpired(false);
        when(userRepository.findByUsernameOrEmail("testuser", "testuser"))
                .thenReturn(Optional.of(testUser));

        // Cuando
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Entonces
        assertFalse(userDetails.isCredentialsNonExpired());
    }
}
