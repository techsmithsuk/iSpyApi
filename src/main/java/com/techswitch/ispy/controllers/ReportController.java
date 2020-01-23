package com.techswitch.ispy.controllers;

import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.api.ReportApiModel;
import com.techswitch.ispy.models.database.ReportDatabaseModel;
import com.techswitch.ispy.models.request.ReportRequestModel;
import com.techswitch.ispy.services.ReportService;
import com.techswitch.ispy.services.token_validation.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000", "https://techswitch-i-spy-staging.herokuapp.com", "https://techswitch-i-spy.herokuapp.com"})
@RequestMapping("reports")
public class ReportController {

    private ReportService reportService;
    private TokenValidator tokenValidator;

    @Autowired
    public ReportController(ReportService reportService,TokenValidator tokenValidator) {
        this.reportService = reportService;
        this.tokenValidator = tokenValidator;
    }

    @RequestMapping(method = GET)
    @ResponseBody
    public ResponseEntity getReportListFiltered(@RequestHeader(value = "token",required = false) String token, Filter filter) {
        if(token == null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(tokenValidator.validateToken(token)) {
            List<ReportDatabaseModel> reportList = reportService.getAllReports(filter);
            List<ReportApiModel> reportApiModelList = reportList.stream().map(ReportApiModel::new).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(reportApiModelList);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(value = "/create", method = POST, consumes = "application/json")
    @ResponseBody
    public ReportApiModel createReport(@Valid @RequestBody ReportRequestModel reportRequestModel) {
        ReportDatabaseModel reportDatabaseModel = reportService.createReport(reportRequestModel);
        return new ReportApiModel(reportDatabaseModel);
    }
}
