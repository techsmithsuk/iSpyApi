package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.Sample;
import com.techswitch.ispy.services.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("samples")
public class SampleController {
    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Sample> getSamples() {
        return sampleService.getSamples();
    }
}
