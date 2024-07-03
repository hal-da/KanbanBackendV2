package at.technikum.springrestbackend.controller;

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
}
