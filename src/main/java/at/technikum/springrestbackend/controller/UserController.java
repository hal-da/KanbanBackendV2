package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.mapper.PublicUserMapper;
import at.technikum.springrestbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final PublicUserMapper publicUserMapper;

    @GetMapping()
    public List<PublicUserDto> getUsers(){
        return publicUserMapper.toPublicUserDtos(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        userService.delete(id);
    }

    @GetMapping("/{email}")
    public PublicUserDto getUserByEmail(@PathVariable String email){
        System.out.println(email);
        return publicUserMapper.toPublicUserDto(userService.findByEmail(email));
    }
}
