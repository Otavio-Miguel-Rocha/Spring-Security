package net.weg.otavio.configuration;

import lombok.AllArgsConstructor;
import net.weg.otavio.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfig{
    private final SecurityContextRepository securityContextRepository;
    private final FilterAuthentication filterAuthentication;
    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        //CSRF - Cross Site Request Forgeries

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // permit all makes that everyone can make a request
                // authenticated
                // has Authority
                // has Any Authority
                // block all requests and make the only necessaries method
                .requestMatchers(HttpMethod.GET, "/teste").hasAuthority("PUT")

                //Solicita autenticação a todas as requisições, exceto aquelas que foram personalizadas
                //Tratando-se de segurança, é melhor bloquear tudo e apontar aquilo que é permitido
                //Do que permitir tudo e apontar aquilo que é bloqueado
                .anyRequest().authenticated()
        );
        //Definição de security context
//        http.securityContext((context) -> context.securityContextRepository(securityContextRepository));

        //Configura a sessão para existir apenas durante a requisição, utilizando o JWT dos cookies sempre
        //Sem persistência de autenticação
        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //Faz com que o filtro entra na lista de filtro de autenticação
        http.addFilterBefore(filterAuthentication, UsernamePasswordAuthenticationFilter.class);

        http.formLogin(Customizer.withDefaults());
        http.logout(Customizer.withDefaults());
        return http.build();
    }


}
