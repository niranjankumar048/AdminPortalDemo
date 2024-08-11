package com.demo.adminportal.tasks;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.service.ClientOrganisationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledTasksTest {

    @Mock
    private ClientOrganisationService clientOrganisationService;

    @InjectMocks
    private ScheduledTasks scheduledTasks;

    private ClientOrganisation org1;
    private ClientOrganisation org2;

    @BeforeEach
    public void setUp() {
        org1 = new ClientOrganisation();
        org1.setId(1L);
        org1.setName("Organisation 1");
        org1.setRegistrationDate(LocalDate.now().minusYears(1));
        org1.setExpiryDate(LocalDate.now().minusDays(1));
        org1.setEnabled(true);

        org2 = new ClientOrganisation();
        org2.setId(2L);
        org2.setName("Organisation 2");
        org2.setRegistrationDate(LocalDate.now().minusYears(1));
        org2.setExpiryDate(LocalDate.now().plusDays(1));
        org2.setEnabled(true);
    }

    @Test
    public void testCheckExpiryDates() {
        List<ClientOrganisation> expiredOrganisations = Arrays.asList(org1);
        when(clientOrganisationService.getExpiredOrganisations()).thenReturn(expiredOrganisations);
        scheduledTasks.checkExpiryDates();

        // Verify that the expired organisations are disabled
        verify(clientOrganisationService, times(1)).updateClientOrganisation(eq(1L), any(ClientOrganisation.class));

        // Verify that the getExpiredOrganisations method is called
        verify(clientOrganisationService, times(1)).getExpiredOrganisations();

        // Verify that the organisation is disabled
        assertFalse(org1.isEnabled());
    }
}

