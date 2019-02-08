package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="sevice_client_associations")
public class ServiceClientAssociation {

    @EmbeddedId
    private ServiceClientId id;

    @ManyToOne
    @MapsId("clientId")
    private Client client;

    @ManyToOne
    @MapsId("serviceId")
    private Service service;

    private ServiceClientAssociation() {}

    public ServiceClientAssociation(Client client, Service service) {
        this.client = client;
        this.service = service;
        this.id = new ServiceClientId(client.getId(), service.getId());
    }

}