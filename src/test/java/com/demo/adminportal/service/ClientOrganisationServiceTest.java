package com.demo.adminportal.service;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.repository.ClientOrganisationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientOrganisationServiceTest {

    @Mock
    private ClientOrganisationRepository clientOrganisationRepository;

    @InjectMocks
    private ClientOrganisationService clientOrganisationService;

    private ClientOrganisation org1;
    private ClientOrganisation org2;
    private ClientOrganisation org3;
    private ClientOrganisation org4;

    @BeforeEach
    public void setUp() {
        org1 = new ClientOrganisation();
        org1.setId(1L);
        org1.setName("Org 1");
        org1.setRegistrationDate(LocalDate.now().minusYears(1));
        org1.setExpiryDate(LocalDate.now().plusDays(5));
        org1.setExpiringSoon(true);
        org1.setEnabled(true);

        org2 = new ClientOrganisation();
        org2.setId(2L);
        org2.setName("Org 2");
        org2.setRegistrationDate(LocalDate.now().minusYears(2));
        org2.setExpiryDate(LocalDate.now().minusDays(1));
        org2.setEnabled(true);

        org3 = new ClientOrganisation();
        org3.setId(3L);
        org3.setName("Org 3");
        org3.setRegistrationDate(LocalDate.now().minusYears(3));
        org3.setExpiryDate(LocalDate.now().plusDays(10));
        org3.setEnabled(true);

        org4 = new ClientOrganisation();
        org4.setId(4L);
        org4.setName("Org 4");
        org4.setRegistrationDate(LocalDate.now().minusYears(2));
        org4.setExpiryDate(LocalDate.now().plusDays(7));
        org4.setEnabled(true);

    }

    @Test
    public void testGetAllClientOrganisations() {
        when(clientOrganisationRepository.findAll()).thenReturn(Arrays.asList(org1, org2));
        when(clientOrganisationRepository.findById(2L)).thenReturn(Optional.of(org2));
        List<ClientOrganisation> organisations = clientOrganisationService.getAllClientOrganisations();

        assertNotNull(organisations);
        assertEquals(2, organisations.size());
        assertTrue(org1.isExpiringSoon());
        verify(clientOrganisationRepository, times(1)).save(any(ClientOrganisation.class));
    }

    @Test
    public void testGetAllClientOrganisationsWithoutUpdate() {
        when(clientOrganisationRepository.findAll()).thenReturn(Arrays.asList(org3, org4));
        List<ClientOrganisation> organisations = clientOrganisationService.getAllClientOrganisations();

        assertEquals(2, organisations.size());
        verify(clientOrganisationRepository, never()).save(any(ClientOrganisation.class));
    }

    @Test
    public void testGetClientOrganisationById() {
        when(clientOrganisationRepository.findById(1L)).thenReturn(Optional.of(org1));

        ClientOrganisation organisation = clientOrganisationService.getClientOrganisationById(1L);

        assertNotNull(organisation);
        assertEquals("Org 1", organisation.getName());
    }

    @Test
    public void testCreateClientOrganisation() {
        when(clientOrganisationRepository.save(any(ClientOrganisation.class))).thenReturn(org1);

        ClientOrganisation savedOrg = clientOrganisationService.createClientOrganisation(org1);

        assertNotNull(savedOrg);
        assertEquals("Org 1", savedOrg.getName());
        verify(clientOrganisationRepository, times(1)).save(org1);
    }

    @Test
    public void testUpdateClientOrganisation() {
        when(clientOrganisationRepository.findById(1L)).thenReturn(Optional.of(org1));
        when(clientOrganisationRepository.save(any(ClientOrganisation.class))).thenReturn(org1);

        org1.setName("Updated Org 1");
        ClientOrganisation updatedOrg = clientOrganisationService.updateClientOrganisation(1L, org1);

        assertNotNull(updatedOrg);
        assertEquals("Updated Org 1", updatedOrg.getName());
        verify(clientOrganisationRepository, times(1)).save(org1);
    }

    @Test
    public void testDeleteClientOrganisation() {
        when(clientOrganisationRepository.findById(1L)).thenReturn(Optional.of(org1));

        clientOrganisationService.deleteClientOrganisation(1L);

        verify(clientOrganisationRepository, times(1)).delete(org1);
    }

    @Test
    public void testGetExpiredOrganisations() {
        when(clientOrganisationRepository.findByEnabled(true)).thenReturn(Arrays.asList(org1, org2));

        List<ClientOrganisation> expiredOrgs = clientOrganisationService.getExpiredOrganisations();

        assertNotNull(expiredOrgs);
        assertEquals(1, expiredOrgs.size());
        assertEquals("Org 2", expiredOrgs.get(0).getName());
    }
}
