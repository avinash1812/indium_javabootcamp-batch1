package com.indium.capstone.model;

import com.indium.capstone.model.Skill;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Associate {
    private int id;
    private String name;
    private int age;
    private String businessUnit;
    private String email;
    private String location;
    private List<Skill> skills;

    public Associate(int id, String name, int age, String businessUnit, String email, String location) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.businessUnit = businessUnit;
        this.email = email;
        this.location = location;
    }

    public Associate(int id, String associateName, int age, String businessUnit, String email, String location, String skills) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
    public void updateSkill(Skill updatedSkill) {
        for (int i = 0; i < skills.size(); i++) {
            if (skills.get(i).getId() == updatedSkill.getId()) {
                skills.set(i, updatedSkill);
                break; // Exit the loop once the skill is updated
            }
        }
    }

    public void removeSkill(int skillId) {
        skills.removeIf(skill -> skill.getId() == skillId);
    }
    public Associate(int id, String name, int age, String businessUnit, String email, String location, List<Skill> skills) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.businessUnit = businessUnit;
        this.email = email;
        this.location = location;
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    // Getters and setters for other properties

    @Override
    public String toString() {
        return "Associate ID: " + id +
                "\nName: " + name +
                "\nAge: " + age +
                "\nBusiness Unit: " + businessUnit +
                "\nEmail: " + email +
                "\nLocation: " + location +
                "\nSkills: " + skills;
    }
}
