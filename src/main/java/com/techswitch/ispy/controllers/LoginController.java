package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.LoginDetails;
import com.techswitch.ispy.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
@RequestMapping("/login")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping()
    public ResponseEntity validateLoginDetails(@ModelAttribute LoginDetails loginDetails) {

        return loginService.validateLogin(loginDetails) ?
                ResponseEntity.status(HttpStatus.OK).body(loginService.getToken()) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}