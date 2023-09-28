package com.indium.capstone.service;

import com.indium.capstone.dao.SkillDao;
import com.indium.capstone.model.*;

import java.sql.*;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class SkillServiceImpl implements SkillService {
    private SkillDao skillDao;
    private AssociateService associateService;

    public SkillServiceImpl(SkillDao skillDao, AssociateService associateService) {
        this.skillDao = skillDao;
        this.associateService = associateService;
    }

    public SkillServiceImpl(Connection connection) {
    }

    @Override
    public int addSkill(Skill skill) {
        try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO skills (name, description, category, experience) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, skill.getName());
            preparedStatement.setString(2, skill.getDescription());
            preparedStatement.setString(3, skill.getCategory().toString()); // Convert SkillCategory to String
            preparedStatement.setInt(4, skill.getExperienceMonths());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Failed to add skill, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int skillId = generatedKeys.getInt(1);
                    return skillId; // Return the generated SkillID
                } else {
                    throw new SQLException("Failed to add skill, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions here
            return -1; // Indicates failure
        }
    }

    @Override
    public boolean deleteAssociateById(int associateId) {
        String deleteAssociateSQL = "DELETE FROM associates WHERE associate_id = ?";

        try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
                PreparedStatement preparedStatement = connection.prepareStatement(deleteAssociateSQL)) {
            preparedStatement.setInt(1, associateId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
            return false; // Return false to indicate failure
        }
    }

    @Override
    public boolean updateSkill(Skill skill) {
        return false;
    }

    @Override
    public boolean deleteSkill(int skillId) {
        return false;
    }

    @Override
    public Skill getSkillById(int skillId) {
        return null;
    }

    @Override
    public List<Skill> getAllSkills() {
        return null;
    }

    @Override
    public List<Skill> searchSkillsByName(String name) {
        return null;
    }

    @Override
    public List<Skill> searchSkillsByCategory(SkillCategory category) {
        return null;
    }

    @Override
    public List<Skill> searchSkillsByExperience(int experience) {
        return null;
    }

    @Override
    public List<Skill> searchSkillsByCategoryAndExperience(SkillCategory category, int experience) {
        return null;
    }

    @Override
    public List<Skill> getTopNSkills(int n) {
        return null;
    }

    @Override
    public double getAverageExperienceBySkill(String skillName) {
        return 0;
    }

    @Override
    public int getTotalAssociates() {
        return 0;
    }

    @Override
    public int getTotalAssociatesWithSkills(int n) {
        return 0;
    }

    @Override
    public List<Integer> getAssociatesWithSkillsGreaterThanN(int n) {
        return null;
    }

    @Override
    public int getTotalAssociatesWithSkills(List<String> skills) {
        return 0;
    }

    @Override
    public void updateSkill(Skill updatedSkill, int associateId) {

    }

    @Override
    public void deleteSkill(int skillId, int associateId) {

    }

    @Override
    public List<Skill> getSkillsByAssociate(int associateId) {
        return null;
    }


//    @Override
//    public boolean addSkill(Skill skill) {
//        // Implement the logic to add a skill to the database
//        return skillDao.addSkill(skill);
//    }
//
//    @Override
//    public boolean updateSkill(Skill skill) {
//        // Implement the logic to update a skill in the database
//        return skillDao.updateSkill(skill);
//    }
//
//    @Override
//    public boolean deleteSkill(int skillId) {
//        // Implement the logic to delete a skill from the database
//        return skillDao.deleteSkill(skillId);
//    }
//
//    @Override
//    public Skill getSkillById(int skillId) {
//        // Implement the logic to retrieve a skill by ID from the database
//        return skillDao.getSkillById(skillId);
//    }
//
//    @Override
//    public List<Skill> getAllSkills() {
//        // Implement the logic to retrieve all skills from the database
//        return skillDao.getAllSkills();
//    }
//
//    @Override
//    public List<Skill> searchSkillsByName(String name) {
//        // Implement the logic to search skills by name in the database
//        return skillDao.searchSkillsByName(name);
//    }
//
//    @Override
//    public List<Skill> searchSkillsByCategory(SkillCategory category) {
//        // Implement the logic to search skills by category in the database
//        return skillDao.searchSkillsByCategory(category);
//    }
//
//    @Override
//    public List<Skill> searchSkillsByExperience(int experience) {
//        // Implement the logic to search skills by experience in the database
//        return skillDao.searchSkillsByExperience(experience);
//    }
//
//    @Override
//    public List<Skill> searchSkillsByCategoryAndExperience(SkillCategory category, int experience) {
//        // Implement the logic to search skills by category and experience in the database
//        return skillDao.searchSkillsByCategoryAndExperience(category, experience);
//    }
//
//    @Override
//    public List<Skill> getTopNSkills(int n) {
//        // Implement the logic to retrieve the top N skills from the database
//        return skillDao.getTopNSkills(n);
//    }
//
//    @Override
//    public double getAverageExperienceBySkill(String skillName) {
//        // Implement the logic to calculate the average experience for a skill
//        List<Associate> associatesWithSkill = associateService.getAssociatesBySkill(skillName);
//        if (associatesWithSkill.isEmpty()) {
//            return 0.0;
//        }
//        double totalExperience = associatesWithSkill.stream()
//                .mapToDouble(Associate::getExperience)
//                .sum();
//        return totalExperience / associatesWithSkill.size();
//    }
//
//    @Override
//    public int getTotalAssociates() {
//        // Implement the logic to get the total number of associates
//        return associateService.getTotalAssociates();
//    }
//
//    @Override
//    public int getTotalAssociatesWithSkills(int n) {
//        // Implement the logic to get the total number of associates with at least N skills
//        return associateService.getTotalAssociatesWithSkills(n);
//    }
//
//    @Override
//    public List<Integer> getAssociatesWithSkillsGreaterThanN(int n) {
//        // Implement the logic to get the IDs of associates with more than N skills
//        return associateService.getAssociatesWithSkillsGreaterThanN(n);
//    }
//
//    @Override
//    public int getTotalAssociatesWithSkills(List<String> skills) {
//        // Implement the logic to get the total number of associates with a specific set of skills
//        return associateService.getTotalAssociatesWithSkills(skills);
//    }
//
//    @Override
//    public void updateSkill(Skill updatedSkill, int associateId) {
//        // Implement the logic to update a skill associated with an associate
//        associateService.updateAssociateSkill(updatedSkill, associateId);
//    }
//
//    @Override
//    public void deleteSkill(int skillId, int associateId) {
//        // Implement the logic to delete a skill associated with an associate
//        associateService.deleteAssociateSkill(skillId, associateId);
//    }
//
//    @Override
//    public List<SkillCountByAssociate> getSkillCountByAssociates() {
//        // Implement the logic to calculate skill counts by associates
//        List<Associate> associates = associateService.getAllAssociates();
//        return associates.stream()
//                .map(associate -> new SkillCountByAssociate(associate.getId(), associate.getSkills().size()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<AssociateCountByBU> getAssociateCountByBU() {
//        // Implement the logic to calculate associate counts by BU
//        Map<String, Long> countsByBU = associateService.getAssociateCountByBU();
//        return countsByBU.entrySet().stream()
//                .map(entry -> new AssociateCountByBU(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<LocationCountBySkill> getLocationCountBySkill() {
//        // Implement the logic to calculate location counts by skill
//        List<Associate> associates = associateService.getAllAssociates();
//        Map<String, Map<String, Long>> locationCountsBySkill = associates.stream()
//                .flatMap(associate -> associate.getSkills().stream()
//                        .map(skill -> new LocationSkillKey(associate.getLocation(), skill.getName())))
//                .collect(Collectors.groupingBy(LocationSkillKey::getLocation,
//                        Collectors.groupingBy(LocationSkillKey::getSkillName, Collectors.counting())));
//
//        return locationCountsBySkill.entrySet().stream()
//                .flatMap(entry -> entry.getValue().entrySet().stream()
//                        .map(skillEntry -> new LocationCountBySkill(entry.getKey(), skillEntry.getKey(), skillEntry.getValue())))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<SkillCountByCategory> getSkillCountByCategory() {
//        // Implement the logic to calculate skill counts by category
//        Map<SkillCategory, Long> countsByCategory = skillDao.getSkillCountByCategory();
//        return countsByCategory.entrySet().stream()
//                .map(entry -> new SkillCountByCategory(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Skill> getSkillsByAssociate(int associateId) {
//        // Implement the logic to get skills associated with an associate
//        Associate associate = associateService.getAssociateById(associateId);
//        if (associate != null) {
//            return associate.getSkills();
//        }
//        return null;
//    }
}
