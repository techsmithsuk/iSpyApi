package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.LoginDetails;
import com.techswitch.ispy.services.token_validation.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
@RequestMapping("/login")
public class LoginController {

    private String adminUsername;
    private String adminPassword;

    @Autowired
    public LoginController(@Qualifier("adminUsername") String adminUsername, @Qualifier("adminPassword") String adminPassword) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @PostMapping(value = "")
    public ResponseEntity validateLoginDetails(LoginDetails loginDetails) {
        if (loginDetails.getUsername().equals(adminUsername) && loginDetails.getPassword().equals(adminPassword)) {

            return ResponseEntity.status(HttpStatus.OK).body(TokenGenerator.createToken());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}