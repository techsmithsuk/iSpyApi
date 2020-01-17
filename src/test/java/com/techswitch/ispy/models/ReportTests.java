package com.techswitch.ispy.models;
import com.techswitch.ispy.models.request.Report;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import static org.junit.Assert.*;


public class ReportTests {

    private Report report;
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void fieldTest_givenValidReport_thenNoViolations() {
        report = new Report(1L, "13-04-2018", "London", "Fake description");
        Set<ConstraintViolation<Report>> violations = validator.validate(report);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void fieldTest_givenNullSuspectId_thenViolationCreated() {
        report = new Report();
        report.setSuspectId(null);
        report.setDate("13-04-2018");
        report.setLocation("Location");
        report.setDescription("Fake Description");
        Set<ConstraintViolation<Report>> violations = validator.validate(report);

        assertEquals(1, violations.size());
        assertTrue(violations.toString().contains("Suspect ID cannot be null"));
    }

    @Test
    public void fieldTest_givenEmptyDescription_thenViolationCreated() {
        report = new Report();
        report.setSuspectId(1L);
        report.setDate("13-04-2018");
        report.setLocation("Location");
        report.setDescription("");
        Set<ConstraintViolation<Report>> violations = validator.validate(report);

        assertEquals(1, violations.size());
        assertTrue(violations.toString().contains("Description cannot be empty"));
    }

    @Test
    public void fieldTest_givenNullDescription_thenViolationCreated() {
        report = new Report();
        report.setSuspectId(1L);
        report.setDate("13-04-2018");
        report.setLocation("Location");
        report.setDescription(null);
        Set<ConstraintViolation<Report>> violations = validator.validate(report);

        assertEquals(1, violations.size());
        assertTrue(violations.toString().contains("Description cannot be empty"));
    }
}
