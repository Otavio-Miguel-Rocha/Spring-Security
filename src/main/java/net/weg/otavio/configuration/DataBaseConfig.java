package net.weg.otavio.configuration;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.otavio.model.Authorization;
import net.weg.otavio.model.User;
import net.weg.otavio.model.UserDetailsAuth;
import net.weg.otavio.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DataBaseConfig {
    private final UserRepository userRepository;

//    @PostConstruct
//    public void init() {
//        User user = new User();
//        user.setName("Teste");
//        user.setUserDetailsAuth(
//                UserDetailsAuth.builder()
//                        .user(user)
//                        .enabled(true)
//                        .accountNonExpired(true)
//                        .accountNonLocked(true)
//                        .credentialsNonExpired(true)
//                        .email("otavio@gmail.com")
//                        .password(new BCryptPasswordEncoder().encode("otavio123456"))
//                        .authorities(List.of(Authorization.GET))
//                        .build());
//        userRepository.save(user);
//    }
}
