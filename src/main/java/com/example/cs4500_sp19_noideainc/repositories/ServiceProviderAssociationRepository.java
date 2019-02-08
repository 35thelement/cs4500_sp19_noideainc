package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.ServiceProviderAssociation;

public interface ServiceProviderAssociationRepository extends CrudRepository<ServiceProviderAssociation, Integer> {
    @Query(value="SELECT spa FROM ServiceProviderAssociation spa")
    public List<ServiceProviderAssociation> findAllSPAs();
    @Query(value="SELECT spa FROM ServiceProviderAssociation spa WHERE spa.provider=:providerId")
    public List<ServiceProviderAssociation> findAllProviderSPAs(@Param("providerId") Integer providerId);
    @Query(value="SELECT spa FROM ServiceProviderAssociation spa WHERE spa.service=:serviceId")
    public List<ServiceProviderAssociation> findAllServiceSPAs(@Param("serviceId") Integer serviceId);
}