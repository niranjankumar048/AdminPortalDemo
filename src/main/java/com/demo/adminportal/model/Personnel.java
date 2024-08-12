package com.demo.adminportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String telephoneNumber;

    @ManyToOne
    @JoinColumn(name = "client_organisation_id")
    private ClientOrganisation clientOrganisation;
}
