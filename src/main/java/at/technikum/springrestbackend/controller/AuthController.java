package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.LoginRequestDto;
import at.technikum.springrestbackend.dto.LoginResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class AuthController {

    @GetMapping("/login")
    public String welcome(){
        return "Login to Kanbantastisch API";
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginDto) {
        System.out.println("LoginRequest: " + loginDto.getEmail() + " " + loginDto.getPassword());
        System.out.println(loginDto);
        return LoginResponseDto.builder().token("token").build();
    }

    @PostMapping("/register")
    public String register(@RequestBody String user) {
        System.out.println(user);
        return "Registration successful";
    }
}
