package com.indium.capstone.service;

import com.indium.capstone.model.*;

import java.util.List;

public interface SkillService {
    int addSkill(Skill skill);

    boolean deleteAssociateById(int associateId);

    boolean updateSkill(Skill skill);



    boolean deleteSkill(int skillId);

    Skill getSkillById(int skillId);

    List<Skill> getAllSkills();

    List<Skill> searchSkillsByName(String name);

    List<Skill> searchSkillsByCategory(SkillCategory category);

    List<Skill> searchSkillsByExperience(int experience);

    List<Skill> searchSkillsByCategoryAndExperience(SkillCategory category, int experience);

    List<Skill> getTopNSkills(int n);

    double getAverageExperienceBySkill(String skillName);

    int getTotalAssociates();

    int getTotalAssociatesWithSkills(int n);

    List<Integer> getAssociatesWithSkillsGreaterThanN(int n);

    int getTotalAssociatesWithSkills(List<String> skills);

    void updateSkill(Skill updatedSkill, int associateId);

    void deleteSkill(int skillId, int associateId);


    List<Skill> getSkillsByAssociate(int associateId);
}
