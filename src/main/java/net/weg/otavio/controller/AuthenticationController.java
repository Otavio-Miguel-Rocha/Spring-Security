package net.weg.otavio.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.otavio.configuration.CookieUtil;
import net.weg.otavio.model.DTO.UserLoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CookieUtil cookieUtil = new CookieUtil();

    //Manipular a requisição de forma única e personalizável
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLoginDTO user,
                                          HttpServletRequest request, HttpServletResponse response){
        try{
            //Principal - username
            //Credential - password
            Authentication authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            //Interface genérica para autenticação
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            //Security Context
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(authencation);
//            securityContextRepository.saveContext(context, request, response);

            //Gera cookie com o token JWT e o adiciona na resposta da request
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Cookie cookie = cookieUtil.generateCookieJWT(userDetails);
            response.addCookie(cookie);

            return new ResponseEntity<>("Autenticação bem Sucedida!", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Falha na autenticação!", HttpStatus.UNAUTHORIZED);
        }
    }
}
