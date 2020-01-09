package com.techswitch.ispy.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.models.Suspect;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/")
    public String home() {
        return "";
    }

    @RequestMapping("/suspect")
    public String suspect() throws JsonProcessingException {
        Suspect suspect = new Suspect();
        return objectMapper.writeValueAsString(suspect);
    }
}
