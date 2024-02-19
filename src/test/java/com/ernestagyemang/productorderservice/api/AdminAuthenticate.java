package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.service.implementations.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AdminAuthenticate {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    void authenticateAsAdmin() {
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                myUserDetailsService.loadUserByUsername("admin"), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
