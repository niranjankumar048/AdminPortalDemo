package com.demo.adminportal.tasks;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.service.ClientOrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component for scheduled tasks related to client organisations which will be used to update the
 * status of expired organisation
 */
@Component
public class ScheduledTasks {
    @Autowired
    private ClientOrganisationService clientOrganisationService;

    /**
     * Checks the expiry dates of client organisations at a fixed rate and disables expired organisations.
     * This method is scheduled to run everyday at midnight
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkExpiryDates() {
        List<ClientOrganisation> expiredOrganisations = clientOrganisationService.getExpiredOrganisations();
        for (ClientOrganisation org : expiredOrganisations) {
            org.setEnabled(false);
            clientOrganisationService.updateClientOrganisation(org.getId(), org);
        }
    }
}
