package com.techswitch.ispy.controllers;

import com.techswitch.ispy.services.NukeService;
import com.techswitch.ispy.services.token_validation.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
public class NukeController {

    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private NukeService nukeService;

    @Autowired
    public NukeController(TokenValidator tokenValidator, NukeService nukeService) {
        this.tokenValidator = tokenValidator;
        this.nukeService = nukeService;
    }

    @RequestMapping(value = "/admin/are-you-sure")
    public ResponseEntity<String> nukeDatabase(@RequestHeader(value = "token", required = false) String token) {

        if(!tokenValidator.validateToken(token)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        nukeService.truncateAllRowsInAllTables();
        return ResponseEntity.status(HttpStatus.OK).body("Success Data Purged From Database");
    }
}
