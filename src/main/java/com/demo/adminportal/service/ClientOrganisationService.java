package com.demo.adminportal.service;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.repository.ClientOrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing client organisations.
 */
@Service
public class ClientOrganisationService {
    @Autowired
    private ClientOrganisationRepository clientOrganisationRepository;

    /**
     * Retrieves all client organisations and updates their expiring status if necessary.
     *
     * @return a list of all client organisations.
     */
    public List<ClientOrganisation> getAllClientOrganisations() {
        return clientOrganisationRepository.findAll().stream()
                .map(organisation -> {
                    if (!organisation.isExpiringSoon() && organisation.getExpiryDate().isBefore(LocalDate.now().plusDays(7))) {
                        organisation.setExpiringSoon(true);
                        updateClientOrganisation(organisation.getId(), organisation);
                    }
                    return organisation;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a client organisation by its ID.
     *
     * @param id the ID of the client organisation to retrieve.
     * @return the client organisation with the specified ID.
     */
    public ClientOrganisation getClientOrganisationById(Long id) {
        return clientOrganisationRepository.findById(id)
                .orElseThrow(() -> null);
    }

    /**
     * Creates a new client organisation.
     *
     * @param clientOrganisation the client organisation to create.
     * @return the created client organisation.
     */
    public ClientOrganisation createClientOrganisation(ClientOrganisation clientOrganisation) {
        /*Commented the below logic as not sure whether there would be any scenario where user would create
        any organisation with past expiry date */
        /*if(clientOrganisation.getExpiryDate().isBefore(LocalDate.now()))
            clientOrganisation.setEnabled(false);*/
        return clientOrganisationRepository.save(clientOrganisation);
    }

    /**
     * Retrieves all expired client organisations.
     *
     * @return a list of expired client organisations.
     */
   public List<ClientOrganisation> getExpiredOrganisations() {
        List<ClientOrganisation> enabledOrganisations = clientOrganisationRepository.findByEnabled(true);
        return enabledOrganisations.stream()
                .filter(org -> org.getExpiryDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing client organisation.
     *
     * @param id the ID of the client organisation to update.
     * @param clientOrganisationDetails the new details of the client organisation.
     * @return the updated client organisation.
     */
    public ClientOrganisation updateClientOrganisation(Long id, ClientOrganisation clientOrganisationDetails) {
        ClientOrganisation clientOrganisation = getClientOrganisationById(id);
        clientOrganisation.setName(clientOrganisationDetails.getName());
        clientOrganisation.setRegistrationDate(clientOrganisationDetails.getRegistrationDate());
        clientOrganisation.setExpiryDate(clientOrganisationDetails.getExpiryDate());
        clientOrganisation.setEnabled(clientOrganisationDetails.isEnabled());
        return clientOrganisationRepository.save(clientOrganisation);
    }

    /**
     * Deletes a client organisation by its ID.
     *
     * @param id the ID of the client organisation to delete.
     */
    public void deleteClientOrganisation(Long id) {
        ClientOrganisation clientOrganisation = getClientOrganisationById(id);
        clientOrganisationRepository.delete(clientOrganisation);
    }
}
