package net.weg.otavio.configuration;

import lombok.AllArgsConstructor;
import net.weg.otavio.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
public class BeanConfigs {

    private final AuthenticationService authenticationService;


    @Bean
    public SecurityContextRepository securityContextRepository() {
        //Mantém informações do usuário na requisição HTTP do usuário
        return new HttpSessionSecurityContextRepository();
    }


    //Camada de Authentication Manager, será configurada ao iniciar a aplicação
    //Pode conter configuração de providers default, ou google, github...
    @Bean
    public AuthenticationManager authenticationManager() {
        //Forma de autenticação através do userDetailsService e do passwordEncoder
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
//        dao.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        dao.setUserDetailsService(authenticationService);
        return new ProviderManager(dao);
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return authenticationService;
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }


    @Bean
    public CorsConfigurationSource corsConfig(){
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("http://localhost:4200"));
        cors.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "GET"));
        //Funcionamento dos Cookies
        cors.setAllowCredentials(true);

    }
}
