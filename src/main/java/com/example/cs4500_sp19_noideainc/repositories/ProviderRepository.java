package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {
    @Query(value="SELECT provider FROM Provider provider")
    public List<Provider> findAllProviders();
    @Query(value="SELECT provider FROM Provider provider WHERE provider.id=:id")
    public Provider findProviderById(@Param("id") Integer id);
    @Query(value="SELECT provider FROM Provider provider WHERE provider.username=:username")
    public Provider findByProvidername(@Param("username") String username);
}