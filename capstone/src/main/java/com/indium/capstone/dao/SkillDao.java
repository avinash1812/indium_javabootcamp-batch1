package com.indium.capstone.dao;

import com.indium.capstone.model.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillDao {
    private List<Skill> skills;
    private int nextSkillId;

    public SkillDao() {
        skills = new ArrayList<>();
        nextSkillId = 1;
    }

    public boolean addSkill(Skill skill) {
        skill.setId(nextSkillId++);
        skills.add(skill);
        return true;
    }

    public boolean updateSkill(Skill updatedSkill, int skillId) {
        Optional<Skill> skillToUpdate = skills.stream()
                .filter(skill -> skill.getId() == skillId)
                .findFirst();

        if (skillToUpdate.isPresent()) {
            Skill skill = skillToUpdate.get();
            skill.setName(updatedSkill.getName());
            skill.setDescription(updatedSkill.getDescription());
            skill.setCategory(updatedSkill.getCategory());
            skill.setExperienceMonths(updatedSkill.getExperienceMonths());
            return true;
        }

        return false;
    }

    public boolean deleteSkill(int skillId) {
        Optional<Skill> skillToRemove = skills.stream()
                .filter(skill -> skill.getId() == skillId)
                .findFirst();

        if (skillToRemove.isPresent()) {
            skills.remove(skillToRemove.get());
            return true;
        }

        return false;
    }

    public List<Skill> getAllSkills() {
        return skills;
    }

}
