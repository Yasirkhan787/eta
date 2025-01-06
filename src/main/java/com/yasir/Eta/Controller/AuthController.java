package com.yasir.Eta.Controller;

import com.yasir.Eta.Requests.LoginRequest;
import com.yasir.Eta.Requests.RegisterRequest;
import com.yasir.Eta.Service.Implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/auth/")
public class AuthController {

    private final UserServiceImpl userService;

    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // Show Registration form
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // Show Login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@ModelAttribute RegisterRequest request) {
        System.out.println("Received Register Request: " + request);
        userService.signUpUser(request);

        // Create a response with a redirection status code and the login page URL in the Location header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/v1/auth/login");

        return new ResponseEntity<>(null, headers, HttpStatus.FOUND);  // HttpStatus.FOUND (302) for redirection
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<String> signIn(@ModelAttribute LoginRequest request){
        userService.signInUser(request);
        return new ResponseEntity<>("User Logged In Successfully", HttpStatus.OK);
    }
}





