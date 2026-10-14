package com.ozgecmis.model;

public class WorkExperience {
    private String companyName;
    private String position;
    private String period;
    private String description;

    public WorkExperience(String companyName, String position, String period, String description) {
        this.companyName = companyName;
        this.position = position;
        this.period = period;
        this.description = description;
    }

    public String getCompanyName() { return companyName; }
    public String getPosition() { return position; }
    public String getPeriod() { return period; }
    public String getDescription() { return description; }
}