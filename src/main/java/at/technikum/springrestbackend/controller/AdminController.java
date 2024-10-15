package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.mapper.UserMapper;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController()
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public List<UserDto> getUsers(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return userMapper.toUserDtos(userService.findAll(userPrincipal));
    }
}
