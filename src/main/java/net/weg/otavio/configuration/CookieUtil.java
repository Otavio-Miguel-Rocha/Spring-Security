package net.weg.otavio.configuration;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

public class CookieUtil {

    public Cookie generateCookieJWT(UserDetails userDetails){
        String token = new JwtUtil().generateToken(userDetails);
        Cookie cookie =  new Cookie("JWT", token);
        //Onde o cookie pode ser acessado, nesse caso todos os endpoints
        cookie.setPath("/");
        cookie.setMaxAge(300);
        return cookie;
    }

    public Cookie getCookie(HttpServletRequest request, String name){
        return WebUtils.getCookie(request, name);
    }
}
