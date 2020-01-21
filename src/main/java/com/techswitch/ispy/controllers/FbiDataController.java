package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.request.SuspectFbiRequestModel;
import com.techswitch.ispy.services.FbiDataService;
import com.techswitch.ispy.services.SuspectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@RestController
public class FbiDataController {

    private FbiDataService fbiDataService;
    private SuspectsService suspectsService;

    @Autowired
    public FbiDataController(FbiDataService fbiDataService, SuspectsService suspectsService) {
        this.fbiDataService = fbiDataService;
        this.suspectsService = suspectsService;
    }

    @RequestMapping(value = "/admin/fetch-fbi-data")
    public ResponseEntity fetchFbiData() throws IOException, ParseException {
        List<SuspectFbiRequestModel> suspects = fbiDataService.getSuspectsFromFbiApi();
        int insertedSuspects = suspectsService.addSuspectsAndReturnNumberOfInsertedSuspects(suspects);
        if (insertedSuspects == 0) {
            return ResponseEntity.ok().body(Collections.singletonMap("Success", "No data has been added. Database Up to date."));
        }
        return ResponseEntity.ok().body(Collections.singletonMap("Success", insertedSuspects + " row(s) has been added to database."));
    }


}
