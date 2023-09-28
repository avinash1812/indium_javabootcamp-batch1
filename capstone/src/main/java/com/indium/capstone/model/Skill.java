package com.indium.capstone.model;

public class Skill {
    private int id; // Auto-generated ID
    private String name;
    private String description;
    private SkillCategory category;
    private int experienceMonths;

    public Skill(int id, String name, String description, SkillCategory category, int experienceMonths) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.experienceMonths = experienceMonths;
    }

    public Skill(String name1, String description, SkillCategory category, int experience) {
        this.name = name1;
        this.description = description;
        this.category = category;
        this.experienceMonths = experience;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

    public int getExperienceMonths() {
        return experienceMonths;
    }

    public void setExperienceMonths(int experienceMonths) {
        this.experienceMonths = experienceMonths;
    }
// Constructors, getters, and setters
}

