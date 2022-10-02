package com.example.springboot_assignment_free.model;

public class SsnValidationResult {
    private boolean ssn_valid;
    private String detail;

    public SsnValidationResult() {
    }

    public SsnValidationResult(boolean ssn_valid, String detail) {
        this.ssn_valid = ssn_valid;
        this.detail = detail;
    }

    public boolean isSsn_valid() {
        return ssn_valid;
    }

    public void setSsn_valid(boolean ssn_valid) {
        this.ssn_valid = ssn_valid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
