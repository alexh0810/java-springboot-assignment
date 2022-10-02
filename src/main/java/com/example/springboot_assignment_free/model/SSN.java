package com.example.springboot_assignment_free.model;

public class SSN {

    private String ssn;
    private String countryCode = "FI";

    public SSN(String ssn, String countryCode) {
        this.ssn = ssn;
        this.countryCode = countryCode;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}