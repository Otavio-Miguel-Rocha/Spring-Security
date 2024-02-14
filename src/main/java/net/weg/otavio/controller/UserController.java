package net.weg.otavio.controller;

import lombok.AllArgsConstructor;
import net.weg.otavio.model.DTO.UserEditDTO;
import net.weg.otavio.model.DTO.UserPasswordDTO;
import net.weg.otavio.model.DTO.UserRegisterDTO;
import net.weg.otavio.model.User;
import net.weg.otavio.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserRegisterDTO user){
        try{
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PutMapping
    public ResponseEntity<?> edit(@RequestBody UserEditDTO user){
        try{
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{userID}")
    public ResponseEntity<?> findByID(@PathVariable Long userID){
        try{
            return new ResponseEntity<>(userService.findById(userID), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<?> delete(@PathVariable Long userID){
        try{
            userService.delete(userID);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //Patches
    @PatchMapping("/password")
    public ResponseEntity<?> editPassword(@RequestBody UserPasswordDTO userPasswordDTO){
        try{
            userService.editPassword(userPasswordDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PatchMapping("/{userID}/account-activate")
    public ResponseEntity<?> editActiveAccount(@PathVariable Long userID){
        try{
            userService.editActiveAccount(userID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{userID}/photo")
    public ResponseEntity<?> editPhoto(@PathVariable Long userID, @RequestParam MultipartFile photo){
        try{
            userService.editPhoto(userID, photo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
