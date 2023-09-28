package com.indium.capstone.service;

import com.indium.capstone.model.Skill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AssociateSkillService {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public AssociateSkillService(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    // Method to add skills to an associate
    public boolean addSkillsToAssociate(int associateId, List<Skill> skills) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // SQL query to insert skills for an associate into the junction table
            String insertSql = "INSERT INTO Associate_Skill (AssociateID, SkillID) VALUES (?, ?)";

            // Use a PreparedStatement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                for (Skill skill : skills) {
                    // Set the parameters in the PreparedStatement
                    preparedStatement.setInt(1, associateId);
                    preparedStatement.setInt(2, skill.getId()); // Assuming Skill has an ID property

                    // Execute the query
                    preparedStatement.executeUpdate();
                }
                // All skills added successfully
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions, e.g., database connection issue or SQL error
            return false;
        }
    }
}
