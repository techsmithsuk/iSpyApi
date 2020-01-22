package com.techswitch.ispy.models;
import com.techswitch.ispy.models.request.ReportRequestModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import static org.junit.Assert.*;


public class ReportRequestModelTests {

    private ReportRequestModel reportRequestModel;
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void fieldTest_givenValidReport_thenNoViolations() {
        reportRequestModel = new ReportRequestModel(1L, "2018-04-13", "London", "Fake description");
        Set<ConstraintViolation<ReportRequestModel>> violations = validator.validate(reportRequestModel);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void fieldTest_givenNullSuspectId_thenViolationCreated() {
        reportRequestModel = new ReportRequestModel();
        reportRequestModel.setSuspectId(null);
        reportRequestModel.setDate("2018-04-13");
        reportRequestModel.setLocation("Location");
        reportRequestModel.setDescription("Fake Description");
        Set<ConstraintViolation<ReportRequestModel>> violations = validator.validate(reportRequestModel);

        assertEquals(1, violations.size());
        assertTrue(violations.toString().contains("Suspect ID cannot be null"));
    }

    @Test
    public void fieldTest_givenEmptyDescription_thenViolationCreated() {
        reportRequestModel = new ReportRequestModel();
        reportRequestModel.setSuspectId(1L);
        reportRequestModel.setDate("2018-04-13");
        reportRequestModel.setLocation("Location");
        reportRequestModel.setDescription("");
        Set<ConstraintViolation<ReportRequestModel>> violations = validator.validate(reportRequestModel);

        assertEquals(1, violations.size());
        assertTrue(violations.toString().contains("Description cannot be empty"));
    }

    @Test
    public void fieldTest_givenNullDescription_thenViolationCreated() {
        reportRequestModel = new ReportRequestModel();
        reportRequestModel.setSuspectId(1L);
        reportRequestModel.setDate("2018-04-13");
        reportRequestModel.setLocation("Location");
        reportRequestModel.setDescription(null);
        Set<ConstraintViolation<ReportRequestModel>> violations = validator.validate(reportRequestModel);

        assertEquals(1, violations.size());
        assertTrue(violations.toString().contains("Description cannot be empty"));
    }
}
