package com.example.springboot_assignment_free.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public interface ValidationService {
    static boolean isValidSSN(String ssn) {
        String[] controlCharactersText = {"A", "B", "C", "D", "E", "F", "H", "J", "K", "L", "M", "N", "P", "R", "S", "T", "U", "V", "W", "X", "Y"};
        HashMap<Integer, String> controlCharacters = new HashMap<Integer, String>();

        //Insert entries into the controlCharacters Hashmap
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

        //REGEX TO CHECK SSN FORMAT
        String regex = "^[0-3][0-9][0-1][0-9][0-9]{2}(\\-|\\+|A)[0-8][0-9]{2}[0-9A-Z]$";
        //CHECK FOR NULL VALUE
        if (ssn == null) {
            System.out.print("Null value detected");
            return false;
        }

        //Step 1: VALIDATE WITH REGEX
        if (!ssn.matches(regex)) {
            System.out.print("Does not match with regex");
            return false;
        }

        //Step 2: Validate the dob
        String[] ssnSplit = ssn.split("A|-|\\+");
        String DOB = ssnSplit[0]; // eg: 081097
        String individualNum = ssnSplit[1].substring(0, 3); // eg: 764
        char controlCharacter = ssn.charAt(10); // eg: W
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("ddmmyy");
            Date formattedDOB = dateFormatter.parse(DOB);
            int year = formattedDOB.getYear();

            // Check if born in the 1800s
            if (year > 1800 && year < 1899) {
                int ssnSign = ssn.indexOf("+");
                if (ssnSign == -1) {
                    System.out.print("Wrong year");
                    return false;
                }
                return true;
            }

            //Check if year is between 1990 and 2000
            if (year > 1990 && year < 2000) {
                int ssnSign = ssn.indexOf("-");
                if (ssnSign == -1) {
                    System.out.print("Wrong year");
                    return false;
                }
            }

            //Check if year is > 2000
            if (year > 2000) {
                int ssnSign = ssn.indexOf("A");
                if (ssnSign == -1) {
                    System.out.print("Wrong year");
                    return false;
                }
            }

        } catch (ParseException e) {
            return false;
        }

        //Step 3: Check the individual number
        if (parseInt(individualNum) < 2 && parseInt(individualNum) > 899) {
            System.out.print("Individual number's not in range");
            return false;
        }

        //Step 4: Check the control character
        String nineDigitNumber = DOB+individualNum; // eg: 081097764
        int remainder = parseInt(nineDigitNumber) % 31; // find the remainder to find the matching control character
        if (!controlCharacters.get(remainder).equals(String.valueOf(controlCharacter))) { // if unmatched then return false
            System.out.print("Wrong control character");
            return false;
        }

        // if all checks are passed, return true
        return true;
    }
}
