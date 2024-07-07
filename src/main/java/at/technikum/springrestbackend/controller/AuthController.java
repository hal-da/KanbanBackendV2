package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.LoginRequestDto;
import at.technikum.springrestbackend.dto.LoginResponseDto;
import at.technikum.springrestbackend.security.JWTIssuer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final JWTIssuer jwtIssuer;

    @GetMapping("/login")
    public String welcome(){
        return "Login to Kanbantastisch API";
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginDto) {
        System.out.println("LoginRequest: " + loginDto.getEmail() + " " + loginDto.getPassword());
        String token = jwtIssuer.issueToken(
                "userId",
                loginDto.getEmail(),
                List.of("ROLE_USER", "ROLE_ADMIN")
        );
        return LoginResponseDto.builder().token(token).build();
    }

    @PostMapping("/register")
    public String register(@RequestBody String user) {
        System.out.println(user);
        return "Registration successful";
    }
}
