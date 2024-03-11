package net.weg.otavio.configuration;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.otavio.service.AuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class FilterAuthentication extends OncePerRequestFilter {

    private final CookieUtil cookieUtil = new CookieUtil();
    private final JwtUtil jwtUtil = new JwtUtil();
    private final AuthenticationService authenticationService;
    private final SecurityContextRepository securityContextRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Get the JWT Cookie from request and the value
        Cookie cookie = cookieUtil.getCookie(request, "JWT");
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

        //Literally do the filter
        filterChain.doFilter(request, response);
    }

    private boolean isPublicRouter(HttpServletRequest request){
        return request.getRequestURI().equals("/login")
                && request.getMethod().equals("POST");
    }
}
