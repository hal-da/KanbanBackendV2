package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.exception.EmailExistsException;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void delete(String id){
        userRepository.deleteById(id);
    }

    public UserEntity findById(String id) {
        System.out.println("UserService.findById" + id);
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        System.out.println("found user");
        return user;
    }

    public UserEntity findByEmail(String email){
        UserEntity user =  userRepository.findByEmail(email);
        if(user == null){
            throw new RuntimeException("User with email " + email + " not found");
        }
        return user;
    }

    public UserEntity register(UserEntity user) throws EmailExistsException {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if(userEntity != null){
            throw new EmailExistsException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }


    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
