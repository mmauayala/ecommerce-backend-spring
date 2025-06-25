package com.miempresa.ecommerce.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miempresa.ecommerce.entity.User;
import com.miempresa.ecommerce.repository.UserRepository;

/**
 * Implementación personalizada de UserDetailsService para Spring Security
 * 
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username));

        return UserPrincipal.create(user);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return UserPrincipal.create(user);
    }

    /**
     * Clase interna para representar el principal del usuario
     */
    public static class UserPrincipal implements UserDetails {
        private Long id;
        private String username;
        private String email;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private boolean enabled;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;

        public UserPrincipal(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities,
                           boolean enabled, boolean accountNonExpired,
                           boolean accountNonLocked, boolean credentialsNonExpired) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.password = password;
            this.authorities = authorities;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.credentialsNonExpired = credentialsNonExpired;
        }

        public static UserPrincipal create(User user) {
            Collection<GrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

            return new UserPrincipal(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities,
                    user.getEnabled(),
                    user.getAccountNonExpired(),
                    user.getAccountNonLocked(),
                    user.getCredentialsNonExpired()
            );
        }

        private static Collection<GrantedAuthority> mapRolesToAuthorities(Set<User.Role> roles) {
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .collect(Collectors.toList());
        }

        // Getters
        public Long getId() { return id; }
        public String getEmail() { return email; }

        @Override
        public String getUsername() { return username; }

        @Override
        public String getPassword() { return password; }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

        @Override
        public boolean isAccountNonExpired() { return accountNonExpired; }

        @Override
        public boolean isAccountNonLocked() { return accountNonLocked; }

        @Override
        public boolean isCredentialsNonExpired() { return credentialsNonExpired; }

        @Override
        public boolean isEnabled() { return enabled; }
    }
}
