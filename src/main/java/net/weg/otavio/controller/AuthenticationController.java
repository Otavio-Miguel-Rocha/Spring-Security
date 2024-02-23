package net.weg.otavio.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.otavio.model.DTO.UserLoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;

    //Manipular a requisição de forma única e personalizável
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLoginDTO user,
                                          HttpServletRequest request, HttpServletResponse response){
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            //Interface genérica para autenticação
            Authentication authencation = authenticationManager.authenticate(authenticationToken);

            //Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authencation);
            securityContextRepository.saveContext(context, request, response);

            return ResponseEntity.ok("Autenticação bem Sucedida");
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
