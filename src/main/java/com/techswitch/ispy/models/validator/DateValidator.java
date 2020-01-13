package com.techswitch.ispy.models.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isEmpty()){
            return true;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate ld = LocalDate.parse(value, formatter);
            String result = ld.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
