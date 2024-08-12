package com.demo.adminportal.service;

import com.demo.adminportal.model.Personnel;
import com.demo.adminportal.repository.PersonnelRepository;
import com.demo.adminportal.utility.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing personnel.
 */
@Service
public class PersonnelService {
    @Autowired
    private PersonnelRepository personnelRepository;

    /**
     * Retrieves all personnel.
     *
     * @return a list of all personnel.
     */
    public List<Personnel> getAllPersonnel() {
        return personnelRepository.findAll();
    }

    /**
     * Retrieves a personnel by its ID.
     *
     * @param id the ID of the personnel to retrieve.
     * @return the personnel with the specified ID.
     */
    public Personnel getPersonnelById(Long id) {
        return personnelRepository.findById(id)
                .orElseThrow(() -> null);
    }

    /**
     * Creates a new personnel.
     *
     * @param personnel the personnel to create.
     * @return the created personnel.
     */
    public Personnel createPersonnel(Personnel personnel) {
        personnel.setPassword(PasswordUtility.encodePassword(personnel.getPassword()));
        return personnelRepository.save(personnel);
    }

    /**
     * Updates an existing personnel.
     *
     * @param id the ID of the personnel to update.
     * @param personnelDetails the new details of the personnel.
     * @return the updated personnel.
     */
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

    /**
     * Deletes a personnel by its ID.
     *
     * @param id the ID of the personnel to delete.
     */
    public void deletePersonnel(Long id) {
        Personnel personnel = getPersonnelById(id);
        personnelRepository.delete(personnel);
    }
}
