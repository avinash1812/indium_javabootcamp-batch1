package com.indium.capstone.dao;

import com.indium.capstone.model.Associate;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.DriverManager.getConnection;

public class AssociateDao {
    private Map<Integer, Associate> associateDatabase;
    private int nextAssociateId;

    public AssociateDao() {
        associateDatabase = new HashMap<>();
        nextAssociateId = 1;
    }

    public int addAssociate(Associate associate) {

        associate.setId(nextAssociateId++);
        associateDatabase.put(associate.getId(), associate);
        return associate.getId();
    }

    public boolean updateAssociate(Associate updatedAssociate, int associateId) {
        if (associateDatabase.containsKey(associateId)) {
            updatedAssociate.setId(associateId);
            associateDatabase.put(associateId, updatedAssociate);
            return true;
        }
        return false;
    }

    public boolean deleteAssociate(int associateId) {
        if (associateDatabase.containsKey(associateId)) {
            associateDatabase.remove(associateId);
            return true;
        }
        return false;
    }

    public Associate getAssociateById(int associateId) {
        try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM associates WHERE id = ?")) {

            preparedStatement.setInt(1, associateId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractAssociateFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Associate extractAssociateFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String businessUnit = resultSet.getString("business_unit");
        String email = resultSet.getString("email");
        String location = resultSet.getString("location");


        return new Associate(id, name, age, businessUnit, email, location);
    }

    public List<Associate> getAllAssociates() {
        List<Associate> associates = new ArrayList<>();
        try (Connection connection = getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM associates")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String businessUnit = resultSet.getString("business_unit");
                String email = resultSet.getString("email");
                String location = resultSet.getString("location");


                Associate associate = new Associate(id, name, age, businessUnit, email, location);
                associates.add(associate);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return associates;
    }
}
