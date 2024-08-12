package com.demo.adminportal.controller;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.model.Personnel;
import com.demo.adminportal.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing personnel.
 */
@RestController
@RequestMapping("/api/personnel")
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;

    /**
     * Retrieves all personnel.
     *
     * @return a list of all personnel.
     */
    @GetMapping
    public List<Personnel> getAllPersonnel() {
        return personnelService.getAllPersonnel();
    }

    /**
     * Retrieves a personnel by its ID.
     *
     * @param id the ID of the personnel to retrieve.
     * @return the personnel with the specified ID.
     */
    @GetMapping("/{id}")
    public Personnel getPersonnelById(@PathVariable Long id) {
        return personnelService.getPersonnelById(id);
    }

    /**
     * Creates a new personnel.
     *
     * @param personnel the personnel to create.
     * @return the created personnel.
     */
    @PostMapping
    public Personnel createPersonnel(@RequestBody Personnel personnel) {
        return personnelService.createPersonnel(personnel);
    }

    /**
     * Updates an existing personnel.
     *
     * @param id the ID of the personnel to update.
     * @param personnelDetails the new details of the personnel.
     * @return the updated personnel.
     */
    @PutMapping("/{id}")
    public Personnel updatePersonnel(@PathVariable Long id, @RequestBody Personnel personnelDetails) {
        return personnelService.updatePersonnel(id, personnelDetails);
    }

    /**
     * Deletes a personnel by its ID.
     *
     * @param id the ID of the personnel to delete.
     * @return a response entity indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonnel(@PathVariable Long id) {
        personnelService.deletePersonnel(id);
        return ResponseEntity.ok().build();
    }
}
