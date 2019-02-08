package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    @Query(value="SELECT client FROM Client client")
    public List<Client> findAllClients();
    @Query(value="SELECT client FROM Client client WHERE client.id=:id")
    public Client findClientById(@Param("id") Integer id);
    @Query(value="SELECT client FROM Client client WHERE client.username=:username")
    public Client findByClientname(@Param("username") String username);
}