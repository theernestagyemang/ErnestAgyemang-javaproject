package com.ernestagyemang.productorderservice.dto;


import com.ernestagyemang.productorderservice.enums.UserRole;
import com.ernestagyemang.productorderservice.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDetailsDto implements UserDetails {

    private String username;
    private String password;
    private boolean active;
    private User user;
    private List<GrantedAuthority> authorities;

    @Enumerated(EnumType.STRING)
    private UserRole roles;

    public MyUserDetailsDto(User user) {

        this.username = user.getEmail();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.user = user;
        List<GrantedAuthority> sm = new ArrayList<>();

        sm.add(new SimpleGrantedAuthority(user.getRole().name()));
        authorities = sm;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
