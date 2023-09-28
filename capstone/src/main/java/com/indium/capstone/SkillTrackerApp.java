package com.indium.capstone;

import com.indium.capstone.model.Associate;
import com.indium.capstone.model.Skill;
import com.indium.capstone.model.SkillCategory;
import com.indium.capstone.service.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SkillTrackerApp {
    private static List<Associate> associates = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            // Establish a database connection here
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");

            // Create instances of services using the database connection
            AssociateService associateService = new AssociateServiceDaoImpl(connection);
            AssociateServiceDaoImpl associateService1 = new AssociateServiceDaoImpl(connection);
            SkillService skillService = new SkillServiceImpl(connection);

            // Create a scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Skill Tracker App Menu:");
                System.out.println("1. Add New Associate");
                System.out.println("2. List Associates");
                System.out.println("3. Add Skill to Associate");
                System.out.println("4. Edit Associate");
                System.out.println("5. Delete Associate");
                System.out.println("6. Search Associates");
                System.out.println("7. Delete Skills");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addNewAssociate(scanner,associateService);
                        break;
                    case 2:
                        listAssociates(associateService);
                        break;
                    case 3:
                        addSkillToAssociate(scanner,associateService,skillService);
                        break;
                    case 4:
                        editAssociate(scanner, associateService1);
                        break;
                    case 5:
                        System.out.print("Enter Associate ID to delete: ");
                        int associateIdToDelete = scanner.nextInt();
                        boolean deleted = associateService1.deleteAccount(associateIdToDelete);
                        if (deleted) {
                            System.out.println("Associate deleted successfully.");
                        } else {
                            System.out.println("Failed to delete associate.");
                        }
                        break;
                    case 6:
                        System.out.print("Enter Associate Name to search: ");
                        String ass_name = scanner.nextLine();
                        associateService1.searchAssociatesByName(ass_name);
//                      searchAssociates(associateService);
                        break;
                    case 7:
                        System.out.print("Enter Skill ID to delete: ");
                        int skillIdToDelete = scanner.nextInt();
                        boolean deleted1=associateService1.deleteSkill(skillIdToDelete);
                        if (deleted1) {
                            System.out.println("Skill deleted successfully.");
                        } else {
                            System.out.println("Failed to delete skill.");
                        }
                        break;
                    case 8:
                        System.out.println("Exiting Skill Tracker App.");
                        connection.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static void addNewAssociate(Scanner scanner, AssociateService associateService) {
        System.out.print("Enter Associate Id: ");
        Integer associateId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Associate Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Associate Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Business Unit: ");
        String businessUnit = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        List<Skill> skills = new ArrayList<>();

        System.out.print("Enter the number of skills: ");
        int numSkills = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numSkills; i++) {
            System.out.println("Skill " + (i + 1) + ":");
            System.out.print("Enter Skill Name: ");
            String name1 = scanner.nextLine();
            System.out.print("Enter Skill Description: ");
            String description = scanner.nextLine();
            System.out.print("Enter Skill Category (Primary/Secondary): ");
            String categoryInput = scanner.nextLine();
            SkillCategory category;

            try {
                category = SkillCategory.valueOf(categoryInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Skill Category. Please enter 'Primary' or 'Secondary'.");
                return; // Exit the method or handle the invalid input as needed.
            }
            System.out.print("Enter Skill Experience (in months): ");
            int experience = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            Skill skill = new Skill(name1, description, category, experience);
            skills.add(skill);
        }
        // Create the Associate object
        Associate associate = new Associate(associateId, name, age, businessUnit, email, location, skills);

        // Add the associate to the database
        int success = associateService.addAssociate(associate);
        if (success > 0) {
//            AssociateSkillService associateSkillService = new AssociateSkillService("jdbc:mysql://localhost:3306/skill_tracker", "root", "root");
//            associateSkillService.addSkillsToAssociate(success, skills);
            System.out.println("Associate added successfully.");
        } else {
            System.out.println("Failed to add associate.");
        }
    }

    private static void listAssociates(AssociateService associateService) {
        List<Associate> associates = associateService.getAllAssociates();
        if (associates.isEmpty()) {
            System.out.println("No associates found.");
        } else {
            System.out.println("List of Associates:");
            for (Associate associate : associates) {
                System.out.println(associate);
            }
        }
    }
    private static void searchAssociates(AssociateService associateService) {
        List<Associate> associates = associateService.getAllAssociates();
        System.out.print("Enter search criteria (Name/ID/Location/Skills): ");
        String searchCriteria = scanner.nextLine();

        System.out.println("Matching Associates:");
        for (Associate associate : associates) {
            if (associate.getName().contains(searchCriteria) ||
                    String.valueOf(associate.getId()).equals(searchCriteria) ||
                    associate.getLocation().contains(searchCriteria) ||
                    associate.getSkills().contains(searchCriteria)) {
                System.out.println(associate);
            }
        }
    }

    private static void addSkillToAssociate(Scanner scanner, AssociateService associateService, SkillService skillService) {
        System.out.print("Enter Associate ID: ");
        int associateId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Skill Name: ");
        String skillName = scanner.nextLine();

        // Check if the associate exists
        Associate associate = associateService.getAssociateById(associateId);
        if (associate == null) {
            System.out.println("Associate not found.");
            return;
        }

        // Check if the skill exists or create a new skill
        Skill skill = (Skill) skillService.searchSkillsByName(skillName);
        if (skill == null) {
            System.out.print("Enter Skill Description: ");
            String skillDescription = scanner.nextLine();
//            System.out.print("Enter Skill Category (Primary/Secondary): ");
            SkillCategory skillCategory = null;

            while (skillCategory == null) {
                System.out.print("Enter Skill Category (Primary/Secondary): ");
                String categoryInput = scanner.nextLine().toUpperCase();

                try {
                    skillCategory = SkillCategory.valueOf(categoryInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Skill Category. Please enter 'Primary' or 'Secondary'.");
                }
                System.out.print("Enter Skill Experience (in months): ");
                int skillExperience = scanner.nextInt();

                skill = new Skill(skillName, skillDescription, skillCategory, skillExperience);
                int skillAdded = skillService.addSkill(skill);
                if (!(skillAdded > 0)) {
                    System.out.println("Failed to add skill.");
                    return;
                } else {
                    System.out.println("Skill added to associate successfully.");
                }
            }

            // Associate the skill with the associate
//            boolean success = associateService.addSkillToAssociate(associate, skill);
//            if (success) {
//                System.out.println("Skill added to associate successfully.");
//            } else {
//                System.out.println("Failed to add skill to associate.");
//            }
        }
    }

    private static void editAssociate(Scanner scanner, AssociateServiceDaoImpl associateService) {
        System.out.print("Enter the ID of the associate you want to edit: ");
        int associateId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Retrieve the associate by ID
        Associate associate = associateService.getAssociateById(associateId);

        if (associate == null) {
            System.out.println("Associate not found.");
            return;
        }


        System.out.println("Current Details of Associate:");
        System.out.println(associate);

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        associate.setName(newName);

        System.out.print("Enter new age: ");
        int newAge = scanner.nextInt();
        associate.setAge(newAge);
        scanner.nextLine();

        System.out.print("Enter new Business Unit: ");
        String newBU = scanner.nextLine();
        associate.setBusinessUnit(newBU);


        System.out.print("Enter new Email: ");
        String newEmail = scanner.nextLine();
        associate.setEmail(newEmail);


        System.out.print("Enter new Location: ");
        String newLocation = scanner.nextLine();
        associate.setLocation(newLocation);


        boolean success = associateService.updateAssociate(associate);

        if (success) {
            System.out.println("Associate updated successfully.");
        } else {
            System.out.println("Failed to update associate.");
        }
    }
}
