package com.example.springboot_assignment_free.controller;

import com.example.springboot_assignment_free.model.SSN;
import com.example.springboot_assignment_free.model.SsnValidationResult;
import com.example.springboot_assignment_free.services.SsnValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSNValidatorController {

    @PostMapping("/api/v1/validate-ssn")
    public ResponseEntity<SsnValidationResult> validateSSN(@RequestBody SSN request_body) {
        String ssn = request_body.getSsn();
        String countryCode = request_body.getCountryCode();

        if (ssn == null || countryCode == null) {
            return new ResponseEntity<>(new SsnValidationResult(false, "Social security number or country code must not be empty!"), HttpStatus.BAD_REQUEST);
        }

        //Check for invalid country code
        if (!countryCode.equals("FI")) {
            return new ResponseEntity<>(new SsnValidationResult(false, "Country code is not supported"), HttpStatus.BAD_REQUEST);
        }

        boolean validatedSSN = SsnValidationService.isValidSSN(ssn);
        if (!validatedSSN) {
            return new ResponseEntity<>(new SsnValidationResult(false, "Social security number is invalid!"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new SsnValidationResult(true, ""), HttpStatus.OK);
    }
}

