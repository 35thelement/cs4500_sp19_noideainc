package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Embeddable;
import javax.persistence.Column;

import java.io.Serializable;

@Embeddable
public class ServiceProviderId implements Serializable {

    @Column(name = "provider_id")
    private Integer providerId;

    @Column(name = "serivce_id")
    private Integer serviceId;

    private ServiceProviderId() {
    }

    public ServiceProviderId(Integer providerId, Integer serviceId) {
        this.providerId = providerId;
        this.serviceId = serviceId;
    }
}