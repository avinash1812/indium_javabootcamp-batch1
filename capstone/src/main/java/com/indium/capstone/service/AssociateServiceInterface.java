package com.indium.capstone.service;

import com.indium.capstone.model.Associate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AssociateServiceInterface {
    boolean createAssociate(Associate associate);
//    public boolean updateAccount(int id, Account account);

    boolean updateAssociate(Associate associate);

    public boolean deleteAssociate(int associate);
    public Associate getAssociate(int assId);
    //    Map<String, Account> getAllAccounts();
    List<Associate> getAllAssociates();



}
