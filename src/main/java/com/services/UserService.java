package com.services;

import com.DTOs.CreateUserDTO;
import com.DTOs.UpdateUserDTO;
import com.Repositories.UserRepository;
import com.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
                createUserDTO.age(),
                createUserDTO.height()
        );

        if (userRepository.findByEmail(createUserDTO.email()).isPresent()){
            throw new IllegalArgumentException("Usuario com esse email ja existe");
        } else {
            var userSaved = userRepository.save(user);

            return userSaved.getUserId();
        }
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public Optional<User>getUserById (Long userId){

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

    public File generateFileAllUsers() {
        File file = new File("users.txt");

        List<User> users = userRepository.findAll();

        StringBuilder userData = new StringBuilder();
        for (User user : users) {
            userData.append(" ").append(user.getUserId())
                    .append(" - Name: ").append(user.getName())
                    /*.append(", Email: ").append(user.getEmail())
                    .append(", Idade: ").append(user.getAge())
                    .append(", Altura: ").append(user.getHeight())*/
                    .append("\n");
        }

        try (FileWriter fileWriter = new FileWriter(file)) { // Overwrite the file each time
            fileWriter.write(userData.toString());
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar o arquivo", e);
        }
        return file;
    }

    public File generateFileUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário com o Id " + userId + "não encontrado"));

        String fileName = userId + " - " + user.getName().toUpperCase() + ".txt";
        Path filePath = Path.of(fileName);
        try {
            String userData = "UserId: " + user.getUserId()
                    + "\nName: " + user.getName()
                    + "\nEmail: " + user.getEmail()
                    + "\nAltura: " + user.getHeight()
                    + "\nIdade: " + user.getAge();
            Files.write(filePath, userData.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar arquivo para o usuário do id: " + userId, e);
        }

        return filePath.toFile();
    }
}
