package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.Report;
import com.techswitch.ispy.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("report")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/create", method = POST, consumes = "application/json")
    public ResponseEntity createReport(@Valid @RequestBody Report report, BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        reportService.createReport(report);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
