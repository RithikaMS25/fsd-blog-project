package com.example.MiniBlog;
import com.example.MiniBlog.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if(repo.findByEmail(user.getEmail()) != null){
            return "User already exists";
        }

        repo.save(user);
        return "Registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){

        User existing = repo.findByEmail(user.getEmail());

        if(existing == null){
            return "User not found";
        }

        if(!existing.getPassword().equals(user.getPassword())){
            return "Wrong password";
        }

        return "Login successful";
    }
}