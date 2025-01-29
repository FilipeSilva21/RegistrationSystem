package services;

import DTOs.CreateUserDTO;
import DTOs.UpdateUserDTO;
import Repositories.UserRepository;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Long createUser(CreateUserDTO createUserDTO){

        var user = new User(
                null,
                createUserDTO.name(),
                createUserDTO.email(),
                createUserDTO.height()
        );

        var userSaved = userRepository.save(user);

        return userSaved.getUserId();
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public Optional<User>findUserById (Long userId){

        return userRepository.findById(userId);
    }

    public void updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception {

        var userExists = userRepository.findById(userId);

        if (userExists.isPresent()) {
            var user = userExists.get();

            if (updateUserDTO.name() != null) {
                user.setName(updateUserDTO.name());
            }
            userRepository.save(user);
            System.out.println("Usuario atualizado com sucesso");
        } else {
            throw new Exception("Usuario nao existe");
        }
    }

    public void deleteUser (Long userId) throws Exception {

        var userExists = userRepository.existsById(userId);

        if(userExists) {
            userRepository.deleteById(userId);
        } else {
            throw new Exception("Usuario não existe");
        }
    }
}
