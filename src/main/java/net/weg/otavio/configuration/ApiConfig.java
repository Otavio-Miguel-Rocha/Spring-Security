package net.weg.otavio.configuration;

import lombok.AllArgsConstructor;
import net.weg.otavio.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class ApiConfig {
    private final AuthenticationService authenticationService;
//    @Bean
//    public UserDetailsManager inMemoryUserDetailsManager(){
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("Otavio")
//                .password("Otavio")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return authenticationService;
    }

}
