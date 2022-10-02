package com.example.springboot_assignment_free.model;

public class SSN {

    private String ssn;
    private String country_code = "FI";

    public SSN(String ssn, String country_code) {
        this.ssn = ssn;
        this.country_code = country_code;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}