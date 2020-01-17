package com.techswitch.ispy.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;
import com.techswitch.ispy.services.SuspectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/suspects")
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
public class SuspectsController {

    private SuspectsService suspectsService;

    @Autowired
    public SuspectsController(SuspectsService suspectsService) {
        this.suspectsService = suspectsService;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping()
    public List<SuspectDatabaseModel> getSuspectList(Filter filter) {
        List<SuspectDatabaseModel> suspectList = suspectsService.getAllSuspects(filter);
        return suspectList;
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public ResponseEntity getSuspectById(@PathVariable int id){
        Optional<SuspectDatabaseModel> suspectFromDatabase = suspectsService.getSuspectById(id);
        if(suspectFromDatabase.isPresent()){
            return ResponseEntity.ok().body(suspectFromDatabase);
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("Error", "Suspect with id: " + id + " not found"));
    }
}

