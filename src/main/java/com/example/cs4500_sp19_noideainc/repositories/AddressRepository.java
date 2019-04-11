package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    @Query(value="SELECT address FROM Address address")
    public List<Address> findAllAddresses();
    @Query(value="SELECT address FROM Address address WHERE address.id=:id")
    public Address findAddressById(@Param("id") Integer id);
    @Query(value="SELECT address FROM Address address WHERE address.resident.id=:id")
    public Address findAddressByUserId(@Param("id") Integer id);
}