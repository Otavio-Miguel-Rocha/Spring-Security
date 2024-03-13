package net.weg.otavio.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
@Component
public class FilterAuthentication extends OncePerRequestFilter {

    @Autowired
    private final Environment environment;
    private final AuthenticationService authenticationService;
    private final SecurityContextRepository securityContextRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtUtil jwtUtil = new JwtUtil(environment);
        CookieUtil cookieUtil = new CookieUtil(environment);
        if(!isPublicRouter(request)){
            //Get the JWT Cookie from request and the value
            Cookie cookie = cookieUtil.getCookie(request, "JWT");
            System.out.println("Chegou Do Filter" + cookie);
            if(cookie == null){
                return;
            }
            String token = cookie.getValue();

            //Validates the token and creation of authenticate user
            String email = jwtUtil.getUsername(token);
            UserDetails userDetails = authenticationService.loadUserByUsername(email);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities());

            //Save this temporarily in the Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);
        }
        //Literally do the filter
        filterChain.doFilter(request, response);
    }

    private boolean isPublicRouter(HttpServletRequest request){
        return request.getRequestURI().equals("/login")
                && (request.getMethod().equals("POST") || request.getMethod().equals("GET"));
    }
}
