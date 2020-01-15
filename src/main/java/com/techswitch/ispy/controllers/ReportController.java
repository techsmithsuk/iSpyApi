package com.techswitch.ispy.controllers;

import com.techswitch.ispy.models.Report;
import com.techswitch.ispy.models.ReportViewModel;
import com.techswitch.ispy.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("reports")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/create", method = POST, consumes = "application/json")
    @ResponseBody
    public ReportViewModel createReport(@Valid @RequestBody Report report) {
        return reportService.createReport(report);
    }

//    @RequestMapping(method = GET, produces = "application/json")
//    public List<Report> getAllReports(Filt
}
