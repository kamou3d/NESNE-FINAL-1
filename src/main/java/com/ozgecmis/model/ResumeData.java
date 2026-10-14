package com.ozgecmis.model;

import java.util.List;

public class ResumeData {
    private PersonalInfo personalInfo;
    private String summary;
    private List<WorkExperience> experiences;
    private List<Education> educations;
    private List<String> skills;

    public ResumeData(PersonalInfo personalInfo, String summary, List<WorkExperience> experiences,
                      List<Education> educations, List<String> skills) {
        this.personalInfo = personalInfo;
        this.summary = summary;
        this.experiences = experiences;
        this.educations = educations;
        this.skills = skills;
    }

    public PersonalInfo getPersonalInfo() { return personalInfo; }
    public String getSummary() { return summary; }
    public List<WorkExperience> getExperiences() { return experiences; }
    public List<Education> getEducations() { return educations; }
    public List<String> getSkills() { return skills; }
}