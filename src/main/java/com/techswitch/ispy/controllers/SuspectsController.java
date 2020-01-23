package com.techswitch.ispy.controllers;
import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.api.SuspectListResponseModel;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;
import com.techswitch.ispy.models.database.SuspectsImagesDatabaseModel;
import com.techswitch.ispy.services.ImagesService;
import com.techswitch.ispy.services.SuspectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/suspects")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
public class SuspectsController {

    private SuspectsService suspectsService;
    private ImagesService imagesService;

    @Autowired
    public SuspectsController(SuspectsService suspectsService, ImagesService imagesService) {
        this.suspectsService = suspectsService;
        this.imagesService = imagesService;
    }



    @RequestMapping()
    public SuspectListResponseModel getSuspectList(Filter filter) {
        int numberOfSuspects = suspectsService.countSuspects();
        List<SuspectDatabaseModel> suspectList = suspectsService.getAllSuspects(filter);
        return new SuspectListResponseModel(suspectList, filter, numberOfSuspects);
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

    @RequestMapping("/{id}/images")
    public ResponseEntity getImagesBySuspectId(@PathVariable int id){
        List<SuspectsImagesDatabaseModel> imagesBySuspectId = imagesService.getImageBySuspectId(id);
        return ResponseEntity.ok().body(imagesBySuspectId);
    }
}

