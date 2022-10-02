package com.example.springboot_assignment_free.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class SsnValidationService {
    public static final DateTimeFormatter TWO_YEAR_FORMATTER = new DateTimeFormatterBuilder()
            .appendValueReduced(ChronoField.YEAR, 2, 2, 1950)
            .toFormatter();
    private static final    Map<String, List<Integer>> DELIMITER_TO_YEAR_RANGE =  Map.of(
            "+", List.of(1800, 1899),
            "-", List.of(1900, 2000),
            "A", List.of(2000, 0))
            ;
    private static final String SSN_REGEX = "^[0-3][0-9][0-1][0-9][0-9]{2}(\\-|\\+|A)[0-8][0-9]{2}[0-9A-Z]$";

    public static boolean isValidSSN(String ssn) {
        String[] controlCharactersText = {"A", "B", "C", "D", "E", "F", "H", "J", "K", "L", "M", "N", "P", "R", "S", "T", "U", "V", "W", "X", "Y"};
        Map<Integer, String> controlCharacters = new HashMap<Integer, String>();

        for (int i = 0; i < 31; i++) {
            // From 0 to 9 are numbers
            if (i < 10) {
                controlCharacters.put(i, String.valueOf(i));
            }
            // From 9 to 30 are characters
            if (i >= 10) {
                controlCharacters.put(i, controlCharactersText[i - 10]);
            }
        }


        if (ssn == null) {
            return false;
        }

        // Step 1: VALIDATE WITH REGEX
        if (!ssn.matches(SSN_REGEX)) {
            return false;
        }

        // Step 2: Validate the dob
        String[] ssnSplit = ssn.split("(?<=A)|(?<=-)|(?<=\\+)");
        String DOB = ssnSplit[0].substring(0, ssnSplit[0].length() - 1); // eg: 131052
        String individualNum = ssnSplit[1].substring(0, 3); // eg: 308
        char controlCharacter = ssn.charAt(10); // eg: T
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("ddmmyy");
            Date formattedDOB = dateFormatter.parse(DOB);
            int year = Year.parse(String.valueOf(formattedDOB.getYear()), TWO_YEAR_FORMATTER).getValue();
            // Validate delimiter
            String delimiter = ssnSplit[0].substring(ssnSplit[0].length() - 1);
            int from = DELIMITER_TO_YEAR_RANGE.get(delimiter).get(0);
            int to = DELIMITER_TO_YEAR_RANGE.get(delimiter).get(1);

            boolean inRange = year > from && (to != 0 && year < to);
            if (!inRange) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

        //Step 3: Check the individual number
        if (parseInt(individualNum) < 2 && parseInt(individualNum) > 899) {
            return false;
        }

        // Step 4: Check the control character
        String nineDigitNumber = DOB + individualNum; // eg: 131052308
        int remainder = parseInt(nineDigitNumber) % 31; // find the remainder to find the matching control character
        if (!controlCharacters.get(remainder).equals(String.valueOf(controlCharacter))) { // if unmatched then return false
            return false;
        }

        // if all checks are passed, return true
        return true;
    }
}
