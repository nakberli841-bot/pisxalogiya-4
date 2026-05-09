package com.psychology.demo.security;

import com.psychology.demo.excception.BusinessLogicException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/api/auth/refresh");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {




           String header = request.getHeader("Authorization");
           if (header == null || !header.startsWith("Bearer ")) {
               filterChain.doFilter(request, response);
               return;
           }
           String token = header.substring(7);
           String userName = jwtService.getUserNameFromToken(token);
           if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

               UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
               if (jwtService.validateToken(token, userDetails)) {
                   Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                           null, userDetails.getAuthorities());

                   SecurityContextHolder.getContext().setAuthentication(authentication);
               } else {
                   throw new BusinessLogicException("access token yoxlanisdan kecmedi");
               }


           }
           filterChain.doFilter(request, response);

    }
}
