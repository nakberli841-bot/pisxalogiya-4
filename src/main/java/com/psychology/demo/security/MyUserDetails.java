package com.psychology.demo.security;

import com.psychology.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class MyUserDetails implements UserDetails {

    private User user;
    private List<GrantedAuthority> authorities;


    @Override
    public boolean isAccountNonExpired() {//akountun vaxtinin bitmesi,mes VIP akkountun vaxtinin bitmesi
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//akountun bloka dusmesi
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//sifrenin vaxtinin bitmesi
        return true;
    }

    @Override
    public boolean isEnabled() {//akuntun aktivliyinin yoxlayir
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
