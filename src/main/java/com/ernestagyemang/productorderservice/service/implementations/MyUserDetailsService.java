package com.ernestagyemang.productorderservice.service.implementations;


import com.ernestagyemang.productorderservice.dto.MyUserDetailsDto;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        user.ifPresent(this::setAttribute);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found " + username));
        return user.map(MyUserDetailsDto::new).get();
    }


    private void setAttribute(User user) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra != null) {
            ra.setAttribute("current_user", user, RequestAttributes.SCOPE_SESSION);
        }
    }


}
