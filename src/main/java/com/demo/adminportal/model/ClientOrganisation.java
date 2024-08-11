package com.demo.adminportal.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({"personnelList"})
public class ClientOrganisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private LocalDate registrationDate;
    private LocalDate expiryDate;
    private boolean enabled;

    @OneToMany(mappedBy = "clientOrganisation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Personnel> personnelList;

    private boolean expiringSoon;
}
