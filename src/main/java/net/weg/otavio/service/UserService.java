package net.weg.otavio.service;


import lombok.AllArgsConstructor;
import net.weg.otavio.model.DTO.UserEditDTO;
import net.weg.otavio.model.DTO.UserPasswordDTO;
import net.weg.otavio.model.DTO.UserRegisterDTO;
import net.weg.otavio.model.File;
import net.weg.otavio.model.User;
import net.weg.otavio.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
   private final UserRepository userRepository;

   private final ModelMapper mapper;

    public User save(UserRegisterDTO user){
        User finalUser = new User();
        mapper.map(user, finalUser);
        //The account will always be true when it is created
        finalUser.setIsActive(true);
        return save(finalUser);
    }

    public User save(UserEditDTO user){
        User finalUser = findById(user.getId());
        mapper.map(user, finalUser);
        return save(finalUser);
    }

    public User findById(Long userID){
        return userRepository.findById(userID).get();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void delete(Long userID){
        userRepository.delete(findById(userID));
    }

    public void editPassword(UserPasswordDTO userPasswordDTO) {
        User user = findById(userPasswordDTO.getId());
        user.setPassword(userPasswordDTO.getPassword());
        save(user);
    }

    public void editActiveAccount(Long userID) {
        User user = findById(userID);
        user.setIsActive(!user.getIsActive());
        save(user);
    }

    public void editPhoto(Long userID, MultipartFile photo) throws IOException {
        User user = findById(userID);
        user.setPhoto(new File(photo));
        save(user);
    }

    private User save(User user){
        return userRepository.save(user);
    }
}
