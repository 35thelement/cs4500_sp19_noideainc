package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.ServiceClientAssociation;

public interface ServiceClientAssociationRepository extends CrudRepository<ServiceClientAssociation, Integer> {
    @Query(value="SELECT sca FROM ServiceClientAssociation sca")
    public List<ServiceClientAssociation> findAllSCAs();
    @Query(value="SELECT sca FROM ServiceClientAssociation sca WHERE sca.client=:clientId")
    public List<ServiceClientAssociation> findAllClientSCAs(@Param("clientId") Integer clientId);
    @Query(value="SELECT sca FROM ServiceClientAssociation sca WHERE sca.service=:serviceId")
    public List<ServiceClientAssociation> findAllServiceSCAs(@Param("serviceId") Integer serviceId);
}