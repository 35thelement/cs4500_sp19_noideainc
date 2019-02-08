package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="sevice_provider_associations")
public class ServiceProviderAssociation {

    @EmbeddedId
    private ServiceProviderId id;

    @ManyToOne
    @MapsId("providerId")
    private Provider provider;

    @ManyToOne
    @MapsId("serviceId")
    private Service service;

    private ServiceProviderAssociation() {}

    public ServiceProviderAssociation(Provider provider, Service service) {
        this.provider = provider;
        this.service = service;
        this.id = new ServiceProviderId(provider.getId(), service.getId());
    }
}