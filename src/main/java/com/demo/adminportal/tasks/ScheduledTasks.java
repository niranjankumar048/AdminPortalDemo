package com.demo.adminportal.tasks;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.service.ClientOrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {
    @Autowired
    private ClientOrganisationService clientOrganisationService;

    @Scheduled(fixedRate = 60000)
    public void checkExpiryDates() {
        List<ClientOrganisation> expiredOrganisations = clientOrganisationService.getExpiredOrganisations();
        for (ClientOrganisation org : expiredOrganisations) {
            org.setEnabled(false);
            clientOrganisationService.updateClientOrganisation(org.getId(), org);
        }
    }
}
