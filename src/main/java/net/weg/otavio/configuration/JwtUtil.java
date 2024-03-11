package net.weg.otavio.configuration;


import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtUtil {
    // Header
    // Payload
    // Signature
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().
                issuer("WEG")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 300000))
                .signWith(SignatureAlgorithm.NONE, "WEG123")
                .content(userDetails.getUsername())
                .compact();
    }

    private Jws<Claims> validateToken(String token){
        return getParser().parseSignedClaims(token);
    }

    private JwtParser getParser(){
        return Jwts.parser().setSigningKey("WEG").build();
    }

    public String getUsername(String token) {
        return validateToken(token).getPayload().getSubject();
    }
}
