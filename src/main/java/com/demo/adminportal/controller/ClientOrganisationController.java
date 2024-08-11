package com.demo.adminportal.controller;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.service.ClientOrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/client-organisations")
public class ClientOrganisationController {
    @Autowired
    private ClientOrganisationService clientOrganisationService;

    @GetMapping
    public List<ClientOrganisation> getAllClientOrganisations() {
        return clientOrganisationService.getAllClientOrganisations();
    }

    @GetMapping("/{id}")
    public ClientOrganisation getClientOrganisationById(@PathVariable Long id) {
        return clientOrganisationService.getClientOrganisationById(id);
    }

    @PostMapping
    public ClientOrganisation createClientOrganisation(@RequestBody ClientOrganisation clientOrganisation) {
        return clientOrganisationService.createClientOrganisation(clientOrganisation);
    }

    @PutMapping("/{id}")
    public ClientOrganisation updateClientOrganisation(@PathVariable Long id, @RequestBody ClientOrganisation clientOrganisationDetails) {
        return clientOrganisationService.updateClientOrganisation(id, clientOrganisationDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClientOrganisation(@PathVariable Long id) {
        clientOrganisationService.deleteClientOrganisation(id);
        return ResponseEntity.ok().build();
    }
}
