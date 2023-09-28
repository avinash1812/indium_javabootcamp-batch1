package com.indium.capstone.service;

import com.indium.capstone.dao.AssociateDao;
import com.indium.capstone.model.Associate;
import com.indium.capstone.model.Skill;

import java.util.List;

public abstract class AssociateService {
    private AssociateDao associateDao;

    public AssociateService() {
        associateDao = new AssociateDao();
    }
    public Associate getAssociateById(int associateId) {
        return associateDao.getAssociateById(associateId);
    }

    public int addAssociate(Associate associate) {
        return associateDao.addAssociate(associate);
    }

    public boolean updateAssociate(Associate updatedAssociate) {
        int associateId = updatedAssociate.getId(); // Retrieve the associate ID from the updated associate
        return associateDao.updateAssociate(updatedAssociate, associateId);
    }


    public boolean deleteAssociate(int associateId) {
        return associateDao.deleteAssociate(associateId);
    }

    public List<Associate> getAllAssociates() {
        return associateDao.getAllAssociates();
    }


    public abstract boolean addSkillToAssociate(Associate associateId, Skill skill);

    public abstract boolean deleteAccount(int accountNumber);

    public abstract boolean deleteSkill(int accountId);

    public abstract List<Associate> searchAssociatesByName(String name);
}
