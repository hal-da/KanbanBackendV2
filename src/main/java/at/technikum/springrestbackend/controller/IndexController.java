package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String welcome(){
        return "Welcome to Kanbantastisch API";
    }

    @GetMapping("/tokenValidator")
    public String tokenValidator(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return "Token is valid: " + userPrincipal.getEmail()
                + " with authorities: " + userPrincipal.getAuthorities()
                + " userId: " + userPrincipal.getUserId()
                + " email: " + userPrincipal.getEmail()
                + " role: " + userPrincipal.getRole();
    }
}
