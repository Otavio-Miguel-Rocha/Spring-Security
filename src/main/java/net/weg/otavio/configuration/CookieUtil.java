package net.weg.otavio.configuration;

import jakarta.servlet.http.Cookie;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.userdetails.UserDetails;

public class CookieUtil {

    public Cookie generateCookie(UserDetails userDetails){
        String token = new JwtUtil().generateToken(userDetails);
        Cookie cookie =  new Cookie("JWT", token);
        //Onde o cookie pode ser acessado, nesse caso todos os endpoints
        cookie.setPath("/");
        cookie.setMaxAge(300000);
        return cookie;
    }
}
