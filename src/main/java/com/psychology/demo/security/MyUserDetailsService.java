package com.psychology.demo.security;

import com.psychology.demo.entity.User;
import com.psychology.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     User user=   userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email+" not found"));
        MyUserDetails myUserDetails = MyUserDetails.builder().user(user).build();
        return myUserDetails;
    }
}
