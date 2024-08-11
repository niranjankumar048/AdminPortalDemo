package com.demo.adminportal.repository;

import com.demo.adminportal.model.ClientOrganisation;
import com.demo.adminportal.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    List<Personnel> findByClientOrganisation(ClientOrganisation clientOrganisation);
}
