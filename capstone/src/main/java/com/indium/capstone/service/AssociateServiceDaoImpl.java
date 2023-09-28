package com.indium.capstone.service;

import com.indium.capstone.model.Associate;
import com.indium.capstone.model.Skill;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class AssociateServiceDaoImpl extends AssociateService{
    private final Connection connection; // Initialize this connection in the constructor

    public AssociateServiceDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int addAssociate(Associate associate) {
        String insertAssociateSQL = "INSERT INTO associates (name, age, business_unit, email, location, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAssociateSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, associate.getName());
            preparedStatement.setInt(2, associate.getAge());
            preparedStatement.setString(3, associate.getBusinessUnit());
            preparedStatement.setString(4, associate.getEmail());
            preparedStatement.setString(5, associate.getLocation());
            preparedStatement.setObject(6, LocalDateTime.now());
            preparedStatement.setObject(7, LocalDateTime.now());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Failed to add associate, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the auto-generated ID
                } else {
                    throw new SQLException("Failed to add associate, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
            return -1; // Return an error code or handle errors appropriately
        }
    }

    @Override
    public boolean addSkillToAssociate(Associate associateId, Skill skill) {
        String insertSkillSQL = "INSERT INTO skills (name, description, category, experience) VALUES (?, ?, ?, ?)";
        String insertAssociateSkillSQL = "INSERT INTO associate_skill (associate_id, skill_id) VALUES (?, ?)";

        try {
            // First, insert the skill
            PreparedStatement skillStatement = connection.prepareStatement(insertSkillSQL, Statement.RETURN_GENERATED_KEYS);
            skillStatement.setString(1, skill.getName());
            skillStatement.setString(2, skill.getDescription());
            skillStatement.setString(3, skill.getCategory().toString());
            skillStatement.setInt(4, skill.getExperienceMonths());

            int skillRowsInserted = skillStatement.executeUpdate();

            if (skillRowsInserted == 0) {
                throw new SQLException("Failed to add skill, no rows affected.");
            }

            int skillId;
            try (ResultSet generatedKeys = skillStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    skillId = generatedKeys.getInt(1); // Get the auto-generated skill ID
                } else {
                    throw new SQLException("Failed to add skill, no ID obtained.");
                }
            }

            // Then, associate the skill with the associate
            PreparedStatement associateSkillStatement = connection.prepareStatement(insertAssociateSkillSQL);
            associateSkillStatement.setInt(1, associateId.getId());
            associateSkillStatement.setInt(2, skillId);

            int associateSkillRowsInserted = associateSkillStatement.executeUpdate();

            return associateSkillRowsInserted > 0; // Return true if the skill was added to the associate
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
            return false; // Return false to indicate failure
        }

    }

    public int addSkill(Skill skill) {
        try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO skills (SkillName, SkillDescription, SkillCategory, ExperienceMonths) VALUES (?, ?, ?, ?)",
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
    public boolean deleteAccount(int accountId) {
        String sql = "DELETE FROM associates WHERE id = ?";

        try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, accountId);

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            // Handle any potential exceptions here
            e.printStackTrace();
            return false; // Return false on error
        }
    }
        @Override
        public boolean deleteSkill ( int accountId){
            String sql = "DELETE FROM skills WHERE id = ?";

            try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, accountId);

                int rowsDeleted = preparedStatement.executeUpdate();

                return rowsDeleted > 0; // Return true if at least one row was deleted
            } catch (SQLException e) {
                // Handle any potential exceptions here
                e.printStackTrace();
                return false; // Return false on error
            }
        }
    @Override
    public boolean updateAssociate(Associate updatedAssociate) {
        String updateAssociateSQL = "UPDATE associates SET name = ?, age = ?, business_unit = ?, email = ?, location = ? WHERE id = ?";

        try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(updateAssociateSQL)) {
            preparedStatement.setString(1, updatedAssociate.getName());
            preparedStatement.setInt(2, updatedAssociate.getAge());
            preparedStatement.setString(3, updatedAssociate.getBusinessUnit());
            preparedStatement.setString(4, updatedAssociate.getEmail());
            preparedStatement.setString(5, updatedAssociate.getLocation());
            preparedStatement.setInt(6, updatedAssociate.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<Associate> searchAssociatesByName(String name) {
        List<Associate> result = new ArrayList<>();
        String sql = "SELECT * FROM associates WHERE name LIKE ?";

        try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Associate associate = new Associate(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("business_unit"),
                        resultSet.getString("email"),
                        resultSet.getString("location"));

                result.add(associate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


}

