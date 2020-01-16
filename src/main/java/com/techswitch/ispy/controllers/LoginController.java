package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.LoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
@RequestMapping("/login")
public class LoginController {

    @PostMapping("")
    public boolean getSuspectList(LoginDetails loginDetails) {

        // does user have token

        // if yes, check it,

        // if okay arry on
        // if no, show login-pop up

        if(loginDetails.getUsername().equals("admin") && loginDetails.getPassword().equals("password")){
            return true;
        }
        return false;
    }

}