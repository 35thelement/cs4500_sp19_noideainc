package com.example.cs4500_sp19_noideainc.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;


@Entity
@Table(name="providers")
public class Provider extends User {
    private String description;
    @OneToMany(mappedBy = "provider")
    private List<ServiceProviderAssociation> services;

    public Provider() {super();}
    public Provider(Integer i, String username, String firstName, String lastName, String description, List<ServiceProviderAssociation> services) {
        super(i, username, firstName, lastName);
        this.description = description;
        this.services = services;
    }
}