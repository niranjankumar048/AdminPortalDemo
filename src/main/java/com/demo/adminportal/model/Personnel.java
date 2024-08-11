package com.demo.adminportal.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;

    @Column(nullable = false)
    private String password;

    private String emailAddress;
    private String telephoneNumber;

    @ManyToOne
    @JoinColumn(name = "client_organisation_id")
    private ClientOrganisation clientOrganisation;
}
