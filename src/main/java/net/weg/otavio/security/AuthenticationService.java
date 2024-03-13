package net.weg.otavio.security;

import lombok.AllArgsConstructor;
import net.weg.otavio.model.User;
import net.weg.otavio.model.UserDetailsAuth;
import net.weg.otavio.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserDetailsAuth_Email(email);
        if(userOptional.isPresent()){
            return userOptional.get().getUserDetailsAuth();
        }
        throw new UsernameNotFoundException("Invalidated Data");
    }
}
