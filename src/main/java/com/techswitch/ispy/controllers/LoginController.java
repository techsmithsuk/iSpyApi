package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.LoginDetails;
import com.techswitch.ispy.services.token_validation.TokenGenerator;
import com.techswitch.ispy.services.token_validation.TokenValidator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
@RequestMapping("/login")
public class LoginController {

    @PostMapping("")
    public String validateLoginDetails(LoginDetails loginDetails) {
        if (loginDetails.getUsername().equals(System.getenv("ADMIN_USERNAME")) && loginDetails.getPassword().equals(System.getenv("ADMIN_PASSWORD"))) {
            return TokenGenerator.createToken();
        }
        return "invalid";
    }
}