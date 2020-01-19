package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.LoginDetails;
import com.techswitch.ispy.models.Token;
import com.techswitch.ispy.services.LoginService;
import com.techswitch.ispy.services.token_validation.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
@RequestMapping("/login")
public class LoginController {

    private LoginService loginService;
    private Token token = new Token();

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "", method = POST)
    public ResponseEntity validateLoginDetails(@ModelAttribute LoginDetails loginDetails) {
        if (loginService.validateLogin(loginDetails)) {
            token.setToken(loginService.getToken());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}