package com.techswitch.ispy.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;
import com.techswitch.ispy.services.SuspectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
public class SuspectsController {

    private SuspectsService suspectsService;

    @Autowired
    public SuspectsController(SuspectsService suspectsService) {
        this.suspectsService = suspectsService;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/suspects")
    public List<SuspectDatabaseModel> getSuspectList(Filter filter) {
        List<SuspectDatabaseModel> suspectList = suspectsService.getAllSuspects(filter);
        return suspectList;
    }

    @RequestMapping("/suspect_test")
    public String suspect() {
        return "{\"name\":\"BaddieMcBad\"}";
    }
}
