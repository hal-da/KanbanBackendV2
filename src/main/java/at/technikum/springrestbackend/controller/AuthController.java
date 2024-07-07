package at.technikum.springrestbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {

    @GetMapping
    public String welcome(){
        return "Login to Kanbantastisch API";
    }

    @PostMapping("/login")
    public String login(@RequestBody String user) {
        System.out.println(user);
        return "Login successful";
    }

    @PostMapping("/register")
    public String register(@RequestBody String user) {
        System.out.println(user);
        return "Registration successful";
    }
}
