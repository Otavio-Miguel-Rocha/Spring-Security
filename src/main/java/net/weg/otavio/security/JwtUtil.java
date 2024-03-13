package net.weg.otavio.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtUtil {

    @Autowired
    private final Environment environment;

    // Header
    // Payload
    // Signature
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().
                issuer("WEG")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 300000))
                .signWith(getKey(), Jwts.SIG.HS256)
                .subject(userDetails.getUsername())
                .compact();
    }

    private Jws<Claims> validateToken(String token){
        return getParser().parseSignedClaims(token);
    }

    private JwtParser getParser(){
        return Jwts.parser().verifyWith(getKey()).build();
    }

    public String getUsername(String token) {
        return validateToken(token).getPayload().getSubject();
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(
                new BCryptPasswordEncoder().encode(environment.getProperty("secret.key")).getBytes(StandardCharsets.UTF_8));
    }
}
