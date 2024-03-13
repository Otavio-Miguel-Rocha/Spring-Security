package net.weg.otavio.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtUtil {

    @Autowired
    private final Environment environment;

    // Header
    // Payload
    // Signature
    public String generateToken(UserDetails userDetails){
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(environment.getProperty("secret.key")));
        return JWT.create().
                withIssuer("WEG")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 300000))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }


    public String getUsername(String token) {
        return JWT.decode(token).getSubject();
    }

}
