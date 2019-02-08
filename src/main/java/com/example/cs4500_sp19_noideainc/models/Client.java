package com.example.cs4500_sp19_noideainc.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.Table;
import javax.persistence.OneToMany;


@Entity
@Table(name="clients")
public class Client extends User {
    @OneToMany(mappedBy = "client")
    private List<ServiceClientAssociation> previousServices;

    public Client() {super();}
    public Client(Integer i, String username, String firstName, String lastName, List<ServiceClientAssociation> previousServices) {
        super(i, username, firstName, lastName);
        this.previousServices = previousServices;
    }

    public List<ServiceClientAssociation> getPreviousServices() {
        return this.previousServices;
    }
    public void setPreviousServices(List<ServiceClientAssociation> previousServices) {
        this.previousServices = previousServices;
    }
}