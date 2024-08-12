package com.demo.adminportal.controller;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.service.ClientOrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing client organisations.
 */
@RestController
@RequestMapping("/api/client-organisations")
public class ClientOrganisationController {
    @Autowired
    private ClientOrganisationService clientOrganisationService;

    /**
     * Retrieves all client organisations.
     *
     * @return a list of all client organisations.
     */
    @GetMapping
    public List<ClientOrganisation> getAllClientOrganisations() {
        return clientOrganisationService.getAllClientOrganisations();
    }

    /**
     * Retrieves a client organisation by its ID.
     *
     * @param id the ID of the client organisation to retrieve.
     * @return the client organisation with the specified ID.
     */
    @GetMapping("/{id}")
    public ClientOrganisation getClientOrganisationById(@PathVariable Long id) {
        return clientOrganisationService.getClientOrganisationById(id);
    }

    /**
     * Creates a new client organisation.
     *
     * @param clientOrganisation the client organisation to create.
     * @return the created client organisation.
     */
    @PostMapping
    public ClientOrganisation createClientOrganisation(@RequestBody ClientOrganisation clientOrganisation) {
        return clientOrganisationService.createClientOrganisation(clientOrganisation);
    }

    /**
     * Updates an existing client organisation.
     *
     * @param id the ID of the client organisation to update.
     * @param clientOrganisationDetails the new details of the client organisation.
     * @return the updated client organisation.
     */
    @PutMapping("/{id}")
    public ClientOrganisation updateClientOrganisation(@PathVariable Long id, @RequestBody ClientOrganisation clientOrganisationDetails) {
        return clientOrganisationService.updateClientOrganisation(id, clientOrganisationDetails);
    }

    /**
     * Deletes a client organisation by its ID.
     *
     * @param id the ID of the client organisation to delete.
     * @return a response entity indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClientOrganisation(@PathVariable Long id) {
        clientOrganisationService.deleteClientOrganisation(id);
        return ResponseEntity.ok().build();
    }
}
