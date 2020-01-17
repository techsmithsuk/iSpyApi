package com.techswitch.ispy.controllers;

import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.database.ReportDatabaseModel;
import com.techswitch.ispy.models.request.ReportRequestModel;
import com.techswitch.ispy.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(method = GET)
    @ResponseBody
    public List<ReportDatabaseModel> getReportListFiltered(Filter filter) {
        List<ReportDatabaseModel> reportList = reportService.getAllReports(filter);
        return reportList;
    }

    @RequestMapping(value = "/create", method = POST, consumes = "application/json")
    @ResponseBody
    public ReportDatabaseModel createReport(@Valid @RequestBody ReportRequestModel reportRequestModel) {
        return reportService.createReport(reportRequestModel);
    }
}
