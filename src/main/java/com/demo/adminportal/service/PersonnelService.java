package com.demo.adminportal.service;

import com.demo.adminportal.model.Personnel;
import com.demo.adminportal.repository.PersonnelRepository;
import com.demo.adminportal.utility.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelService {
    @Autowired
    private PersonnelRepository personnelRepository;

    public List<Personnel> getAllPersonnel() {
        return personnelRepository.findAll();
    }

    public Personnel getPersonnelById(Long id) {
        return personnelRepository.findById(id)
                .orElseThrow(() -> null);
    }

    public Personnel createPersonnel(Personnel personnel) {
        personnel.setPassword(PasswordUtility.encodePassword(personnel.getPassword()));
        return personnelRepository.save(personnel);
    }

    public Personnel updatePersonnel(Long id, Personnel personnelDetails) {
        Personnel personnel = getPersonnelById(id);
        personnel.setFirstName(personnelDetails.getFirstName());
        personnel.setLastName(personnelDetails.getLastName());
        personnel.setUsername(personnelDetails.getUsername());
        personnel.setPassword(PasswordUtility.encodePassword(personnelDetails.getPassword()));
        personnel.setEmailAddress(personnelDetails.getEmailAddress());
        personnel.setTelephoneNumber(personnelDetails.getTelephoneNumber());
        return personnelRepository.save(personnel);
    }

    public void deletePersonnel(Long id) {
        Personnel personnel = getPersonnelById(id);
        personnelRepository.delete(personnel);
    }
}
