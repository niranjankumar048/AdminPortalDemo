package com.demo.adminportal.controller;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.model.Personnel;
import com.demo.adminportal.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnel")
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;

    @GetMapping
    public List<Personnel> getAllPersonnel() {
        return personnelService.getAllPersonnel();
    }

    @GetMapping("/{id}")
    public Personnel getPersonnelById(@PathVariable Long id) {
        return personnelService.getPersonnelById(id);
    }

    @PostMapping
    public Personnel createPersonnel(@RequestBody Personnel personnel) {
        return personnelService.createPersonnel(personnel);
    }

    @PutMapping("/{id}")
    public Personnel updatePersonnel(@PathVariable Long id, @RequestBody Personnel personnelDetails) {
        return personnelService.updatePersonnel(id, personnelDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonnel(@PathVariable Long id) {
        personnelService.deletePersonnel(id);
        return ResponseEntity.ok().build();
    }
}
