package at.technikum.springrestbackend.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("login")
public class LoginController {

    @GetMapping
    public String welcome(){
        return "Login to Kanbantastisch API";
    }

    @PostMapping
    public String login(@RequestBody String user){
        System.out.println(user);
        return "Login successful";
    }
}
