package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.dto.UpdateUserDto;
import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.mapper.PublicUserMapper;
import at.technikum.springrestbackend.mapper.UserMapper;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final PublicUserMapper publicUserMapper;
    private final UserMapper userMapper;

    @GetMapping()
    public List<PublicUserDto> getUsers(){
        return publicUserMapper.toPublicUserDtos(userService.findAll());
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable String id,
                           @AuthenticationPrincipal UserPrincipal userPrincipal){
        UserEntity requestUser = userService.findById(userPrincipal.getUserId());
        return userMapper.toUserDto(userService.findById(id, requestUser));
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserDto updateUserDto,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        UserEntity requestUser = userService.findById(userPrincipal.getUserId());
        return userMapper.toUserDto(userService.update(id, updateUserDto, requestUser));
    }
}
