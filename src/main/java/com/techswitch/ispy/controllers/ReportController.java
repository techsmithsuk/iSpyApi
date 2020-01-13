package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.Report;
import com.techswitch.ispy.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController()
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/create", method = POST, consumes = "application/json")
    public ResponseEntity createReport(@RequestBody Report report){
        return reportService.createReport(report);
    }
}
