package com.demo.adminportal.repository;

import com.demo.adminportal.model.ClientOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClientOrganisationRepository extends JpaRepository<ClientOrganisation, Long> {
    List<ClientOrganisation> findByEnabled(boolean enabled);
    List<ClientOrganisation> findByExpiryDateBefore(LocalDate date);
}
