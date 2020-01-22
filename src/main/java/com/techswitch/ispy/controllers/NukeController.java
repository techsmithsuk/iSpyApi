package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.Token;
import com.techswitch.ispy.models.database.ReportDatabaseModel;
import com.techswitch.ispy.services.NukeService;
import com.techswitch.ispy.services.token_validation.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/are-you-sure")
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
public class NukeController {

    private TokenValidator tokenValidator;
    private NukeService nukeService;

    @Autowired
    public NukeController(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    public ResponseEntity nukeDatabase(@RequestHeader(value = "token", required = false) String token) {

        ResponseEntity failedAttempt = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        ResponseEntity successfulAttempt = ResponseEntity.status(HttpStatus.OK).body("Success Data Purged From Database");

        if(token == null || !tokenValidator.validateToken(token)){
            return failedAttempt;
        }

        nukeService.truncateAllRowsInAllTables();
        return successfulAttempt;
    }
}
