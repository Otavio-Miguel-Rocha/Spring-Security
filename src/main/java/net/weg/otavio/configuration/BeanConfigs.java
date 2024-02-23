package net.weg.otavio.configuration;

import lombok.AllArgsConstructor;
import net.weg.otavio.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

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
    public AuthenticationManager authenticationManager() throws Exception {
        //Forma de autenticação através do userDetailsService e do passwordEncoder
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        dao.setUserDetailsService(authenticationService);
        return new ProviderManager(dao);
    }

}
