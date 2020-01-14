package com.techswitch.ispy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.Suspect;

import com.techswitch.ispy.services.SuspectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/allsuspects/page{pageNumber}&page_size{pageSize}")
    public List<Suspect> getSuspectList(@PathVariable("pageNumber") Integer pageNumber,@PathVariable("pageSize") Integer pageSize) {
        Filter filter = new Filter();
        filter.setPage(pageNumber);
        filter.setPageSize(pageSize);
        List<Suspect> suspectList = suspectsService.getAllSuspects(filter);
        return suspectList;
    }

    @RequestMapping("/suspect")
    public String suspect() {
        return "{\"name\":\"BaddieMcBad\"}";
    }
}
