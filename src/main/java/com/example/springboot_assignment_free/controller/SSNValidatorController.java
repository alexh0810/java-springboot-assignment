package com.example.springboot_assignment_free.controller;

import com.example.springboot_assignment_free.model.SSN;
import com.example.springboot_assignment_free.services.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSNValidatorController {

    @PostMapping("/api/v1/validate-ssn")
    public ResponseEntity<String> postBody(@RequestBody SSN request_body) {
        String ssn = request_body.getSsn();
        String country_code = request_body.getCountry_code();

        //Check for null
        if (ssn == null || country_code == null) {
            return new ResponseEntity<>("Social security number or country code must not be empty!", HttpStatus.BAD_REQUEST);
        }

        //Check for invalid country code
        if (!country_code.equals("FI")) {
            return new ResponseEntity<>("Country code is not supported", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        boolean validatedSSN = ValidationService.isValidSSN(ssn);
        if (!validatedSSN) {
            return new ResponseEntity<>("Social security is invalid!", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>("Social security number is valid!", HttpStatus.OK);
    }
}

