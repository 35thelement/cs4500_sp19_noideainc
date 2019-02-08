package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Embeddable;
import javax.persistence.Column;

import java.io.Serializable;

@Embeddable
public class ServiceClientId implements Serializable {

    @Column(name = "provider_id")
    private Integer clientId;

    @Column(name = "serivce_id")
    private Integer serviceId;

    private ServiceClientId() {
    }

    public ServiceClientId(Integer clientId, Integer serviceId) {
        this.clientId = clientId;
        this.serviceId = serviceId;
    }
}