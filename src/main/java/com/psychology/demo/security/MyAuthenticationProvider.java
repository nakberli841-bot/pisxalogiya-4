package com.psychology.demo.security;

import com.psychology.demo.entity.User;
import com.psychology.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepo.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("istifadeci movcud deyil"));
        MyUserDetails userDetails = MyUserDetails.builder().user(user).build();
        if(passwordEncoder.matches(password, user.getPassword())) {
            if(!user.isEnabled()){
                throw new DisabledException("hesabinizin aktivliyi bitib");
            }
            if(!user.isAccountNonExpired()){
                throw new AccountExpiredException("hesabinizin vaxti bitib");
            }
            if(!user.isAccountNonLocked()){
                throw new LockedException("hesabinizin  blokdadi");
            }
            if(!user.isCredentialsNonExpired()){
                throw new CredentialsExpiredException("sifrenizin vaxti bitib");
            }
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" +user.getRole().name()));
            return new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);

        }else {
            throw new BadCredentialsException("sifre duzgun deyil");

        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
