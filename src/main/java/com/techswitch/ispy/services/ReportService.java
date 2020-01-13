package com.techswitch.ispy.services;

import com.techswitch.ispy.models.Report;
import com.techswitch.ispy.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReportService extends DatabaseService {

    @Autowired
    protected ReportService(@Qualifier("databaseUrl") String url) {
        super(url);
    }

    public ResponseEntity createReport(Report report) {
        AppUtils.log("createReport", "[REPORT] - Started executing sql query to add a new report to database");
        try {
            jdbi.useHandle(handle ->
                    handle.createUpdate("INSERT INTO reports (suspect_id, date_of_sighting, location, description) " +
                            "VALUES (:suspectId, :date::date, :location, :description);")
                            .bind("suspectId", report.getSuspectId())
                            .bind("date", report.getDate())
                            .bind("location", report.getLocation())
                            .bind("description", report.getDescription())
                            .execute()
            );
        }catch (Exception e){
            AppUtils.logCatchException("createReport", "[REPORT] - Error executing sql query to add a new report to database", e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        AppUtils.log("createReport", "[REPORT] - Successfully executed sql query. Report has been added to database");
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
