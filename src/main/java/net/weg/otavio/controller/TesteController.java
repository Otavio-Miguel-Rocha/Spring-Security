package net.weg.otavio.controller;


import lombok.AllArgsConstructor;
import net.weg.otavio.model.User;
import net.weg.otavio.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teste")
@AllArgsConstructor
public class TesteController {

    private final UserRepository userRepository;

    @GetMapping
    public String teste(){
        return "Teste";
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user){
        try{
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
